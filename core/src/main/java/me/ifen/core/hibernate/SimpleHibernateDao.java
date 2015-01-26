package me.ifen.core.hibernate;

import me.ifen.core.Constants;
import me.ifen.core.util.ReflectionUtils;
import org.hibernate.*;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 封装Hibernate原生API的DAO泛型基类.
 * <p/>
 * 可在Service层直接使用, 也可以扩展泛型DAO子类使用, 见两个构造函数的注释.
 * 参考Spring2.5自带的Petlinc例子, 取消了HibernateTemplate, 直接使用Hibernate原生API.
 *
 * @param <T>  DAO操作的对象类型
 * @param <PK> 主键类型
 * @author calvin
 */
@SuppressWarnings("unchecked")
public class SimpleHibernateDao<T, PK extends Serializable> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SessionFactory sessionFactory;

    protected Class<T> entityClass;

    /**
     * 用于Dao层子类使用的构造函数.
     * 通过子类的泛型定义取得对象类型Class.
     * eg.
     * public class SysUserDao extends SimpleHibernateDao<SysUser, Long>
     */
    public SimpleHibernateDao() {
        this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
    }

    /**
     * 用于用于省略Dao层, 在Service层直接使用通用SimpleHibernateDao的构造函数.
     * 在构造函数中定义对象类型Class.
     * eg.
     * SimpleHibernateDao<SysUser, Long> userDao = new SimpleHibernateDao<SysUser, Long>(sessionFactory, SysUser.class);
     *
     * @param sessionFactory SessionFactory
     * @param entityClass    Class<T>
     */
    public SimpleHibernateDao(final SessionFactory sessionFactory, final Class<T> entityClass) {
        this.sessionFactory = sessionFactory;
        this.entityClass = entityClass;
    }

    /**
     * 取得sessionFactory.
     *
     * @return SessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * 取得当前Session.
     *
     * @return Session
     */
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * clear Session对象
     */
    public void clear(T entity) {
        getSession().evict(entity);
    }

    /**
     * 保存新增或修改的对象.
     *
     * @param entity 实体对象
     */
    public void save(final T entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().saveOrUpdate(entity);
        logger.debug("save entity: {}", entity);
    }

    /**
     * 保存新增或修改的对象.
     *
     * @param entity 实体对象
     */
    public void saveOnly(final T entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().save(entity);

        logger.debug("save entity: {}", entity);
    }

    /**
     * 保存新增或修改的对象.
     *
     * @param entity 实体对象
     */
    public void merge(final T entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().merge(entity);

        logger.debug("save entity: {}", entity);
    }

    /**
     * 删除对象.
     *
     * @param entity 对象必须是session中的对象或含id属性的transient对象.
     */
    public void delete(final T entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().delete(entity);

        logger.debug("delete entity: {}", entity);
    }

    /**
     * 按id删除对象.
     *
     * @param id PK主键
     */
    public void delete(final PK id) {
        Assert.notNull(id, "id不能为空");
        delete(get(id));
        logger.debug("delete entity {},id is {}", entityClass.getSimpleName(), id);
    }

    /**
     * 按id获取对象.
     *
     * @param id 主键，包括联合主键
     * @return T
     */
    public T get(final PK id) {
        Assert.notNull(id, "id不能为空");
        T item = null;
        if (item == null) {
            item = (T) getSession().get(entityClass, id);
        }
        return item;
    }

    public T getByEntityClassAndId(Class<T> c, Long id) {
        T item = null;

        if (item == null) {
            item = (T) getSession().get(c, id);
        }

        return item;
    }

    /**
     * 按id列表获取对象列表.
     *
     * @param ids 主键集合
     * @return List<T>
     */
    public List<T> get(final Collection<PK> ids) {
        return find(Restrictions.in(getIdName(), ids));
    }

    /**
     * 获取全部对象.
     *
     * @return List<T>
     */
    public List<T> getAll() {
        return find();
    }

    /**
     * 获取全部对象, 支持按属性行序.
     *
     * @param orderByProperty 排序的字段
     * @param isAsc           升序或降序
     * @return List<T>
     */
    public List<T> getAll(String orderByProperty, boolean isAsc) {
        Criteria c = createCriteria();
        if (isAsc) {
            c.addOrder(Order.asc(orderByProperty));
        } else {
            c.addOrder(Order.desc(orderByProperty));
        }
        return c.list();
    }

    /**
     * 按属性查找对象列表, 匹配方式为相等.
     *
     * @param propertyName 属性名
     * @param value        属性值
     * @return List<T>
     */
    public List<T> findBy(final String propertyName, final Object value) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = Restrictions.eq(propertyName, value);
        return find(criterion);
    }

    /**
     * 按属性查找唯一对象, 匹配方式为相等.
     *
     * @param propertyName 属性
     * @param value        对于的值
     * @return T
     */
    public T findUniqueBy(final String propertyName, final Object value) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = Restrictions.eq(propertyName, value);
        return (T) createCriteria(criterion).uniqueResult();
    }

    /**
     * 按HQL查询对象列表.
     *
     * @param hql    执行的hql字符串
     * @param values 数量可变的参数,按顺序绑定.
     * @param <X>    泛型
     * @return <X> List<X>
     */
    public <X> List<X> find(final String hql, final Object... values) {
        return createQuery(hql, values).list();
    }

    /**
     * 按HQL查询对象列表.
     *
     * @param hql    执行的hql字符串
     * @param values 命名参数,按名称绑定.
     * @param <X>    泛型
     * @return <X> List<X>
     */
    public <X> List<X> find(final String hql, final Map<String, ?> values) {
        return createQuery(hql, values).list();
    }

    /**
     * 按HQL查询唯一对象.
     *
     * @param hql    需要执行的hql字符串
     * @param values 数量可变的参数,按顺序绑定.
     * @param <X>    泛型
     * @return <X> X
     */
    public <X> X findUnique(final String hql, final Object... values) {
        return (X) createQuery(hql, values).uniqueResult();
    }

    /**
     * 按HQL查询唯一对象.
     *
     * @param hql    需要执行的hql字符串
     * @param values 数量可变的参数,按顺序绑定.
     * @param <X>    泛型
     * @return <X> X
     */
    public <X> X findUnique(final String hql, final List<Object> values) {
        return (X) createQuery(hql, values).uniqueResult();
    }

    /**
     * 按HQL查询唯一对象.
     *
     * @param hql    需要执行的hql字符串
     * @param values 命名参数,按名称绑定.
     * @param <X>    泛型
     * @return <X> X
     */
    public <X> X findUnique(final String hql, final Map<String, ?> values) {
        return (X) createQuery(hql, values).uniqueResult();
    }

    /**
     * 执行HQL进行批量修改/删除操作.
     *
     * @param hql    需要执行的hql字符串
     * @param values 数量可变的参数,按顺序绑定.
     * @return 更新记录数.
     */
    public int batchExecute(final String hql, final Object... values) {
        return createQuery(hql, values).executeUpdate();
    }

    /**
     * 执行HQL进行批量修改/删除操作.
     *
     * @param hql    需要执行的hql字符串
     * @param values 命名参数,按名称绑定.
     * @return 更新记录数.
     */
    public int batchExecute(final String hql, final Map<String, ?> values) {
        return createQuery(hql, values).executeUpdate();
    }

    /**
     * 根据查询HQL与参数列表创建Query对象.
     * 与find()函数可进行更加灵活的操作.
     *
     * @param queryString 执行的字符串
     * @param values      数量可变的参数,按顺序绑定.
     * @return Query
     */
    public Query createQuery(final String queryString, final Object... values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    /**
     * 根据查询HQL与参数列表创建Query对象.
     * 与find()函数可进行更加灵活的操作.
     *
     * @param queryString 执行的字符串
     * @param values      数量可变的参数,按顺序绑定.
     * @return Query
     */
    public Query createQuery(final String queryString, final List<Object> values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.size(); i++) {
                query.setParameter(i, values.get(i));
            }
        }
        return query;
    }

    /**
     * 根据查询HQL与参数列表创建Query对象.
     * 与find()函数可进行更加灵活的操作.
     *
     * @param queryString 执行的字符串
     * @param values      命名参数,按名称绑定.
     * @return Query
     */
    public Query createQuery(final String queryString, final Map<String, ?> values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            query.setProperties(values);
        }
        return query;
    }

    /**
     * 按Criteria查询对象列表.
     *
     * @param criterions 数量可变的Criterion.
     * @return List<T>
     */
    public List<T> find(final Criterion... criterions) {
        return createCriteria(criterions).list();
    }

    public List<T> find(final Criteria criteria) {
        return criteria.list();
    }

    /**
     * 按Criteria查询唯一对象.
     *
     * @param criterions 数量可变的Criterion.
     * @return T
     */
    public T findUnique(final Criterion... criterions) {
        return (T) createCriteria(criterions).uniqueResult();
    }

    /**
     * 按Criteria查询唯一对象.
     *
     * @param criteria Criterion
     * @return T
     */
    public T findUnique(final Criteria criteria) {
        return (T) criteria.uniqueResult();
    }

    /**
     * 根据Criterion条件创建Criteria.
     * 与find()函数可进行更加灵活的操作.
     *
     * @param criterions 数量可变的Criterion.
     * @return Criterion
     */
    public Criteria createCriteria(final Criterion... criterions) {
        return createCriteria(this.getEntityAlias(entityClass), criterions);
    }

    public Criteria createCriteria(Collection<Criterion> criterionCollection){
        Criterion[] converted = new Criterion[criterionCollection.size()];
        converted = criterionCollection.toArray(converted);
        return createCriteria(converted);
    }

    /**
     * @param alias
     * @param criterions 数量可变的Criterion
     * @return Criteria
     */
    public Criteria createCriteria(String alias, final Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(entityClass, alias);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    /**
     * 初始化对象.
     * 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化.
     * 如果传入entity, 则只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性.
     * 如需初始化关联属性,需执行:
     * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
     * Hibernate.initialize(user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
     */
    public void initProxyObject(Object proxy) {
        Hibernate.initialize(proxy);
    }

    /**
     * Flush当前Session.
     */
    public void flush() {
        getSession().flush();
    }

    /**
     * 为Query添加distinct transformer.
     * 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
     *
     * @param query 查询的Query对象
     * @return Query
     */
    public Query distinct(Query query) {
        query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return query;
    }

    /**
     * 为Criteria添加distinct transformer.
     * 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
     *
     * @param criteria Criteria对象
     * @return Criteria
     */
    public Criteria distinct(Criteria criteria) {
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria;
    }

    /**
     * 取得对象的主键名.
     *
     * @return 主键名
     */
    public String getIdName() {
        ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
        return meta.getIdentifierPropertyName();
    }

    /**
     * 判断对象的属性值在数据库内是否唯一.
     * <p/>
     * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
     *
     * @param propertyName 属性名
     * @param newValue     新值
     * @param oldValue     原来的值
     * @return true:属性值唯一, false:属性值不唯一
     */
    public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
        if (newValue == null || newValue.equals(oldValue)) {
            return true;
        }
        Object object = findUniqueBy(propertyName, newValue);
        return (object == null);
    }

    /**
     * 把Criterion的集合转换成数组
     *
     * @param collection Criterion集合
     * @return Criterion数组
     */
    public Criterion[] createCriterians(Collection<Criterion> collection) {
        Criterion[] converted = new Criterion[collection.size()];
        converted = collection.toArray(converted);
        return converted;
    }

    public String getEntityAlias(Class classz) {
        String alias = null;
        try {
            alias = (String) ReflectionUtils.getFieldValue(classz, Constants.QUERY_ALIAS_FIELD_NAME);
        } catch (Exception e) {
        }

        if (alias == null) {
            alias = classz.getSimpleName().toLowerCase();
        }
        return alias;
    }

}