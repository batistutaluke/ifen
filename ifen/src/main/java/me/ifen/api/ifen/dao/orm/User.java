package me.ifen.api.ifen.dao.orm;

import me.ifen.core.hibernate.BaseEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by zhangjingbo on 15/1/26.
 */
@Entity
@Table(name = "ifen_user")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends BaseEntity {
    private Long id;

    /**
     * 用户来源：
     * 0 - qq
     */
    private Integer source;
    /**
     * 第三方应用的openid
     */
    private String openid;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 性别
     */
    private String gender;
    /**
     * 所在省份
     */
    private String province;
    /**
     * 所在城市
     */
    private String city;
    /**
     * 生日年份
     */
    private Integer year;
    /**
     * 头像url
     */
    private String figureurl;
    /**
     * qq头像url
     */
    private String figureurlQq;
    /**
     * 是否黄钻
     * 0 - 不是
     * 1 - 是
     */
    private Integer vip;
    /**
     * 黄钻级别
     */
    private Integer level;
    /**
     * 是否年费黄钻
     * 0 - 不是
     * 1 - 是
     */
    private Integer isYellowYearVip;



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "source")
    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    @Column(name = "openid")
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "year")
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Column(name = "figureurl")
    public String getFigureurl() {
        return figureurl;
    }

    public void setFigureurl(String figureurl) {
        this.figureurl = figureurl;
    }

    @Column(name = "figureurl_qq")
    public String getFigureurlQq() {
        return figureurlQq;
    }

    public void setFigureurlQq(String figureurlQq) {
        this.figureurlQq = figureurlQq;
    }

    @Column(name = "vip")
    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Column(name = "is_yellow_year_vip")
    public Integer getIsYellowYearVip() {
        return isYellowYearVip;
    }

    public void setIsYellowYearVip(Integer isYellowYearVip) {
        this.isYellowYearVip = isYellowYearVip;
    }
}