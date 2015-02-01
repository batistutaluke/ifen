package me.ifen.core.hibernate;

import me.ifen.core.Constants;
import me.ifen.core.util.ReflectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 封装SpringSide扩展功能的Hibernat DAO泛型基类.
 * <p/>
 * 扩展功能包括分页查询,按属性过滤条件列表查询.
 * 可在Service层直接使用,也可以扩展泛型DAO子类使用,见两个构造函数的注释.
 *
 * @param <T>  DAO操作的对象类型
 * @param <PK> 主键类型
 */
public class HibernateDao<T, PK extends Serializable> extends SimpleHibernateDao<T, PK> {
    /**
     * 用于Dao层子类的构造函数.
     * 通过子类的泛型定义取得对象类型Class.
     * eg.
     * public class SysUserDao extends HibernateDao<SysUser, Long>{
     * }
     */
    public HibernateDao() {
        super();
    }

    /**
     * 用于省略Dao层, Service层直接使用通用HibernateDao的构造函数.
     * 在构造函数中定义对象类型Class.
     * eg.
     * HibernateDao<SysUser, Long> userDao = new HibernateDao<SysUser, Long>(sessionFactory, SysUser.class);
     *
     * @param sessionFactory SessionFactory
     * @param entityClass    Class<T>
     */
    public HibernateDao(final SessionFactory sessionFactory, final Class<T> entityClass) {
        super(sessionFactory, entityClass);
    }

    /**
     * 分页获取全部对象.
     *
     * @param page Page<T>
     * @return Page<T>
     */
    public Page<T> getAll(final Page<T> page) {
        return findPage(page);
    }

    /**
     * 按HQL分页查询.
     *
     * @param page   分页参数. 注意不支持其中的orderBy参数.
     * @param hql    hql语句.
     * @param values 数量可变的查询参数,按顺序绑定.
     * @return 分页查询结果, 附带结果列表及所有查询输入参数.
     */
    @SuppressWarnings("unchecked")
    public Page<T> findPage(final Page<T> page, final String hql, final Object... values) {
        Assert.notNull(page, "page不能为空");

        Query q = createQuery(hql, values);

        if (page.isAutoCount()) {
            long totalCount = countHqlResult(hql, values);
            page.setTotalCount(totalCount);
        }

        setPageParameterToQuery(q, page);

        List result = q.list();
        page.setResult(result);
        return page;
    }

    /**
     * 按HQL分页查询.
     *
     * @param page   分页参数. 注意不支持其中的orderBy参数.
     * @param hql    hql语句.
     * @param values 命名参数,按名称绑定.
     * @return 分页查询结果, 附带结果列表及所有查询输入参数.
     */
    @SuppressWarnings("unchecked")
    public Page<T> findPage(final Page<T> page, final String hql, final Map<String, ?> values) {
        Assert.notNull(page, "page不能为空");

        Query q = createQuery(hql, values);

        if (page.isAutoCount()) {
            long totalCount = countHqlResult(hql, values);
            page.setTotalCount(totalCount);
        }

        setPageParameterToQuery(q, page);

        List result = q.list();
        page.setResult(result);
        return page;
    }

    /**
     * 按Criteria分页查询.
     *
     * @param page       分页参数.
     * @param criterions 数量可变的Criterion.
     * @return 分页查询结果.附带结果列表及所有查询输入参数.
     */
    @SuppressWarnings("unchecked")
    public Page<T> findPage(final Page<T> page, final Criterion... criterions) {
        Assert.notNull(page, "page不能为空");

        Criteria c = createCriteria(criterions);

        if (page.isAutoCount()) {
            long totalCount = countCriteriaResult(c);
            page.setTotalCount(totalCount);
        }

        setPageParameterToCriteria(c, page);
        try {
            List result = c.list();
            page.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return page;
    }

    public Page<T> findSimplePage(final Page<T> page, final List<PropertyFilter> filters) {
        Assert.notNull(page, "page不能为空");


        StringBuilder sb = new StringBuilder("from ");
        sb.append(entityClass.getName());
        sb.append(" as ");
        sb.append(this.getEntityAlias(entityClass));

        return this.findPage(page, sb.toString(), new ArrayList<Object>(0), filters);
    }

    /**
     * 分页的方式查询记录
     *
     * @param page     Page<T>
     * @param criteria Criteria对象
     * @return Page<T>
     */
    public Page<T> findPage(final Page<T> page, final Criteria criteria) {
        Assert.notNull(page, "page不能为空");


        if (page.isAutoCount()) {
            long totalCount = countCriteriaResult(criteria);
            page.setTotalCount(totalCount);
        }

        setPageParameterToCriteria(criteria, page);

        List result = criteria.list();
        page.setResult(result);
        return page;
    }

    /**
     * 设置分页参数到Query对象,辅助函数.
     *
     * @param q    Query对象
     * @param page Page<T>
     * @return Query
     */
    protected Query setPageParameterToQuery(final Query q, final Page<T> page) {

        Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");

        //hibernate的firstResult的序号从0开始
        q.setFirstResult(page.getFirst() - 1);
        q.setMaxResults(page.getPageSize());
        return q;
    }

    /**
     * 设置分页参数到Criteria对象,辅助函数.
     *
     * @param c    Criteria对象
     * @param page Page<T>
     * @return Criteria
     */
    protected Criteria setPageParameterToCriteria(final Criteria c, final Page<?> page) {

        Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");

        //hibernate的firstResult的序号从0开始
        c.setFirstResult(page.getFirst() - 1);
        c.setMaxResults(page.getPageSize());

        if (page.isOrderBySetted()) {
            String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
            String[] orderArray = StringUtils.split(page.getOrder(), ',');

            Assert.isTrue(orderByArray.length == orderArray.length, "分页多重排序参数中,排序字段与排序方向的个数不相等");

            for (int i = 0; i < orderByArray.length; i++) {
                if (Page.ASC.equals(orderArray[i])) {
                    c.addOrder(Order.asc(orderByArray[i]));
                } else {
                    c.addOrder(Order.desc(orderByArray[i]));
                }
            }
        }
        return c;
    }

    /**
     * 执行count查询获得本次Hql查询所能获得的对象总数.
     * <p/>
     * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
     *
     * @param hql    要执行的hql语句
     * @param values 数量可变的values
     * @return 对象总数.
     */
    public long countHqlResult(final String hql, final Object... values) {
        String countHql = prepareCountHql(hql);

        try {
            Long count = findUnique(countHql, values);
            return count;
        } catch (Exception e) {
            throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
        }
    }

    /**
     * 执行count查询获得本次Hql查询所能获得的对象总数.
     * <p/>
     * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
     *
     * @param hql    要执行的hql语句
     * @param values 查询条件List
     * @return 对象总数
     */
    protected long countHqlResult(final String hql, final List<Object> values) {
        String countHql = prepareCountHql(hql);

        try {
            Long count = findUnique(countHql, values);
            return count;
        } catch (Exception e) {
            throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
        }
    }

    /**
     * 执行count查询获得本次Hql查询所能获得的对象总数.
     * <p/>
     * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
     *
     * @param hql    要执行的hql语句
     * @param values 查询条件Map
     * @return 对象总数
     */
    protected long countHqlResult(final String hql, final Map<String, ?> values) {
        String countHql = prepareCountHql(hql);

        try {
            Long count = findUnique(countHql, values);
            return count;
        } catch (Exception e) {
            throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
        }
    }

    /**
     * 通过HQL语句生成统计记录总数的HQL语句
     *
     * @param orgHql 源HQL语句
     * @return 统计数量的HQL语句
     */
    protected String prepareCountHql(String orgHql) {
        String fromHql = orgHql;
        //select子句与order by子句会影响count查询,进行简单的排除.
        fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
        fromHql = StringUtils.substringBefore(fromHql, "order by");

        String countHql = "select count(*) " + fromHql;
        return countHql;
    }

    /**
     * 执行count查询获得本次Criteria查询所能获得的对象总数.
     *
     * @param c Criteria对象
     * @return 对象总数.
     */
    @SuppressWarnings("unchecked")
    public long countCriteriaResult(final Criteria c) {
        CriteriaImpl impl = (CriteriaImpl) c;

        // 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
        Projection projection = impl.getProjection();
        ResultTransformer transformer = impl.getResultTransformer();

        List<CriteriaImpl.OrderEntry> orderEntries = null;
        try {
            orderEntries = (List) ReflectionUtils.getFieldValue(impl, "orderEntries");
            ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());
        } catch (Exception e) {
            logger.error("不可能抛出的异常:{}", e.getMessage());
        }

        // 执行Count查询
        Long totalCountObject = (Long) c.setProjection(Projections.rowCount()).uniqueResult();
        long totalCount = (totalCountObject != null) ? totalCountObject : 0;

        // 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
        c.setProjection(projection);

        if (projection == null) {
            c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        if (transformer != null) {
            c.setResultTransformer(transformer);
        }
        try {
            ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
        } catch (Exception e) {
            logger.error("不可能抛出的异常:{}", e.getMessage());
        }

        return totalCount;
    }

    /**
     * 按属性查找对象列表,支持多种匹配方式.
     *
     * @param propertyName 属性
     * @param value        属性值
     * @param matchType    匹配方式,目前支持的取值见PropertyFilter的MatcheType enum.
     * @return List<T>
     */
    public List<T> findBy(final String propertyName, final Object value, final PropertyFilter.MatchType matchType) {
        Criterion criterion = buildCriterion(propertyName, value, matchType);
        return find(criterion);
    }

    /**
     * 按属性过滤条件列表查找对象列表.
     *
     * @param filters 过滤条件
     * @return List<T>
     */
    public List<T> find(List<PropertyFilter> filters, Order... orderBy) {
        Criterion[] criterions = buildCriterionByPropertyFilter(filters);
        Criteria criteria = createCriteria(criterions);
        if (orderBy != null && orderBy.length > 0) {
            for (Order o : orderBy)
                criteria.addOrder(o);
        }

        return criteria.list();
    }

    /**
     * 按属性过滤条件列表分页查找对象.
     *
     * @param page    Page<T>
     * @param filters 过滤条件
     * @return Page<T>
     */
    public Page<T> findPage(final Page<T> page, final List<PropertyFilter> filters) {
        Criterion[] criterions = buildCriterionByPropertyFilter(filters);
        return findPage(page, criterions);
    }

    /**
     * 按属性过滤条件列表分页查找对象.
     *
     * @param page    Page<T>
     * @param hql     执行的hql
     * @param values  查询条件
     * @param filters 过滤条件
     * @return Page<T>
     */
    public Page<T> findPage(final Page<T> page, final String hql, final List<Object> values, final List<PropertyFilter> filters) {

        Assert.notNull(page, "page不能为空");


        Query q = this.buildQueryByPropertyFilter(page, hql, values, filters);

        if (page.isAutoCount()) {
            long totalCount = countHqlResult(q.getQueryString(), values);
            page.setTotalCount(totalCount);
        }

        setPageParameterToQuery(q, page);

        List result = q.list();
        page.setResult(result);
        return page;
    }

    public Page<T> findPage(final Page<T> page, final List<PropertyFilter> filters, final String hql, final Object... values) {
        List paramValues = new ArrayList();
        paramValues.addAll(Arrays.asList(values));
        return this.findPage(page, hql, paramValues, filters);
    }

    /**
     * 按属性条件参数创建Criterion,辅助函数.
     *
     * @param propertyName  属性
     * @param propertyValue 值
     * @param matchType     MatchType
     * @return Criterion
     */
    protected Criterion buildCriterion(final String propertyName, final Object propertyValue, final PropertyFilter.MatchType matchType) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = null;
        //根据MatchType构造criterion
        switch (matchType) {
            case EQ:
                criterion = Restrictions.eq(propertyName, propertyValue);
                break;
            case NEQ:
                criterion = Restrictions.ne(propertyName, propertyValue);
                break;
            case LIKE:
                criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.ANYWHERE);
                break;
            /**
             * 前端模糊查询 hxl
             * %xxx
             */
            case ENDLIKE:
                criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.END);
                break;
            /**
             * 后端模糊查询
             * xxx%
             */
            case STARTLIKE:
                criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.START);
                break;
            /**
             * 精确查询 相当于EQ
             * xxx
             */
            case EXACTLIKE:
                criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.EXACT);
                break;
            case LE:
                criterion = Restrictions.le(propertyName, propertyValue);
                break;
            case LT:
                criterion = Restrictions.lt(propertyName, propertyValue);
                break;
            case GE:
                criterion = Restrictions.ge(propertyName, propertyValue);
                break;
            case GT:
                criterion = Restrictions.gt(propertyName, propertyValue);
        }
        return criterion;
    }

    /**
     * 按属性条件列表创建Criterion数组,辅助函数.
     *
     * @param filters 过滤条件
     * @return Criterion[]
     */
    protected Criterion[] buildCriterionByPropertyFilter(final List<PropertyFilter> filters) {
        List<Criterion> criterionList = new ArrayList<Criterion>();
        for (PropertyFilter filter : filters) {
            if (!filter.hasMultiProperties()) { //只有一个属性需要比较的情况.
                Criterion criterion = buildCriterion(filter.getPropertyName(), filter.getMatchValue(), filter
                        .getMatchType());
                criterionList.add(criterion);
            } else {//包含多个属性需要比较的情况,进行or处理.
                Disjunction disjunction = Restrictions.disjunction();
                for (String param : filter.getPropertyNames()) {
                    Criterion criterion = buildCriterion(param, filter.getMatchValue(), filter.getMatchType());
                    disjunction.add(criterion);
                }
                criterionList.add(disjunction);
            }
        }

        return criterionList.toArray(new Criterion[criterionList.size()]);
    }

    /**
     * 将filters和criterions组合，并根据结果创建一个Criteria
     *
     * @param filters
     * @param criterions
     * @return
     */
    protected Criteria createCriteria(List<PropertyFilter> filters, Criterion... criterions) {
        Criteria criteria = createCriteria(buildCriterionByPropertyFilter(filters));
        if (criterions != null)
            for (Criterion c : criterions) {
                criteria.add(c);
            }
        return criteria;
    }

    /**
     * 按属性条件列表创建Query,辅助函数.
     * 仅支持较简单的hql: 不含group by、order by等
     *
     * @param page    Page<T>
     * @param hql     查询的Hql语句
     * @param values  查询条件
     * @param filters 过滤条件
     * @return Query
     */
    protected Query buildQueryByPropertyFilter(final Page<T> page, String hql, List<Object> values, final List<PropertyFilter> filters) {
        String qs = buildQueryStringByPropertyFilter(page, hql, values, filters);
        Query q = createQuery(qs, values);
        return q;
    }

    /**
     * 按属性条件列表创建查询语句
     *
     * @param page
     * @param hql
     * @param values
     * @param filters
     * @return
     */
    public String buildQueryStringByPropertyFilter(final Page<T> page, String hql, List<Object> values, final List<PropertyFilter> filters) {
        Assert.hasText(hql, "propertyName不能为空");
        StringBuilder sb = new StringBuilder(hql);

        if (StringUtils.indexOfIgnoreCase(hql, "where") > 0) {
            sb.append(" and 1=1 ");
        } else {
            sb.append(" where 1=1 ");
        }

        for (PropertyFilter filter : filters) {
            if (!filter.hasMultiProperties()) { //只有一个属性需要比较的情况.
                sb.append(" and ");
                sb.append(filter.getPropertyName());
                sb.append(" ");
                sb.append(getSQLOperatorByFilterMatchType(filter.getMatchType()));
                sb.append(" ? ");
                if (filter.getMatchType() == PropertyFilter.MatchType.LIKE) {
                    values.add("%" + filter.getMatchValue() + "%");
                } else {
                    values.add(filter.getMatchValue());
                }
            } else {//包含多个属性需要比较的情况,进行or处理.
                sb.append(" and ( ");

                int i = 0;
                for (String param : filter.getPropertyNames()) {
                    if (i > 0) sb.append(" or ");
                    sb.append(param);
                    sb.append(" ");
                    sb.append(getSQLOperatorByFilterMatchType(filter.getMatchType()));
                    sb.append(" ? ");
                    if (filter.getMatchType() == PropertyFilter.MatchType.LIKE) {
                        values.add("%" + filter.getMatchValue() + "%");
                    } else {
                        values.add(filter.getMatchValue());
                    }
                    i++;
                }

                sb.append(" ) ");
            }
        }

        //构造排序参数
        if (page.isOrderBySetted()) {
            String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
            String[] orderArray = StringUtils.split(page.getOrder(), ',');

            Assert.isTrue(orderByArray.length == orderArray.length, "分页多重排序参数中,排序字段与排序方向的个数不相等");

            for (int i = 0; i < orderByArray.length; i++) {
                if (i == 0) {
                    sb.append(" order by ");
                } else {
                    sb.append(",");
                }

                sb.append(orderByArray[i]);
                sb.append(" ");
                if (Page.ASC.equals(orderArray[i])) {
                    sb.append(Page.ASC);
                } else {
                    sb.append(Page.DESC);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 判断MatchType的类型，返回sql操作字符串
     *
     * @param matchType MatchType
     * @return 操作的字符串
     */
    protected String getSQLOperatorByFilterMatchType(PropertyFilter.MatchType matchType) {
        String operator = "";
        //根据MatchType构造criterion
        switch (matchType) {
            case EQ:
                operator = "=";
                break;
            case NEQ:
                operator = "!=";
                break;
            case LIKE:
                operator = "like";
                break;

            case LE:
                operator = "<=";
                break;
            case LT:
                operator = "<";
                break;
            case GE:
                operator = ">=";
                break;
            case GT:
                operator = ">";
        }
        return operator;
    }

    public long countProjectionCriteriaResult(final Criteria c) {
        CriteriaImpl impl = (CriteriaImpl) c;

        return c.list().size();
    }

    /**
     * 根据查询条件值构建Query
     *
     * @param query
     * @param values
     */
    public void buildQueryByValues(Query query, final List<Object> values) {
        if (values != null) {
            for (int i = 0; i < values.size(); i++) {
                query.setParameter(i, values.get(i));
            }
        }
    }

    /**
     * 逻辑删除（将isDelete字段置为1）
     */
    public void logicDelete(PK id) {
        T entity = get(id);
        if (null != entity) {
            ReflectionUtils.invokeSetterMethod(entity, "isDelete", Constants.IS_DELETE_DELETE);
        }
    }

    /**
     * 已删除数据恢复（将isDelete字段置为0）
     */
    public void recoverLogicDelete(PK id) {
        T entity = get(id);
        if (null != entity) {
            ReflectionUtils.invokeSetterMethod(entity, "isDelete", Constants.IS_DELETE_NOT_DELETE);
        }
    }

}
