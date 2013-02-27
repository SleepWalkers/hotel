package com.yaoxingyu.hotel.model;

import java.util.Date;

public class User {

    private Integer id;

    /*
     * 昵称
     */
    private String  nick;

    /*
     * 密码
     */
    private String  password;

    /*
     * 头像
     */
    private String  avatar;

    /*
     * 邮箱
     */
    private String  email;

    /*
     * 电话
     */
    private String  tel;

    /*
     * 性别
     */
    private String  sex;
    /*
     * QQ
     */
    private String  qq;
    /*
     * 微博
     */
    private String  weibo;
    /*
     * 旺旺
     */
    private String  wangwang;
    /*
     * 邮政编码
     */
    private String  areaCode;
    /*
     * 地址
     */
    private String  address;

    /*
     * 生日
     */
    private Date    birthday;

    /*
     * 注册时间
     */
    private Date    registerDate;

    /*
     * 来源
     */
    private String  comeFrom;

    /*
     * 粉丝数
     */
    private Integer fansNum;

    /*
     * 关注数
     */
    private Integer followNum;

    /*
     * 行业
     */
    private String  industry;

    /*
     * 职位
     */
    private String  position;

    /*
     * 层级
     */
    private String  level;

    /*
     * 心思
     */
    private String  mood;
    /*
     * 权重
     */
    private Double  factor;

    private Integer enable;

    private String  authorities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getFansNum() {
        return fansNum;
    }

    public void setFansNum(Integer fansNum) {
        this.fansNum = fansNum;
    }

    public Integer getFollowNum() {
        return followNum;
    }

    public void setFollowNum(Integer followNum) {
        this.followNum = followNum;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getComeFrom() {
        return comeFrom;
    }

    public void setComeFrom(String comeFrom) {
        this.comeFrom = comeFrom;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getWangwang() {
        return wangwang;
    }

    public void setWangwang(String wangwang) {
        this.wangwang = wangwang;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }
    
    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

	public Double getFactor() {
		return factor;
	}

	public void setFactor(Double factor) {
		this.factor = factor;
	}

    // ----------------------------------------
    //	public static List<DO_User> query(String jpql) {
    //		return getDao(DO_User.class).query(jpql, DO_User.class);
    //	}
    //
    //	public static List<DO_User> queryPage(String jpql, Integer start, Integer count) {
    //		return getDao(DO_User.class).queryPage(jpql, start, count,
    //				DO_User.class);
    //	}
    //
    //	public static DO_User querySingle(String jpql) {
    //		return getDao(DO_User.class).querySingle(jpql, DO_User.class);
    //	}
    //
    //	public Integer update(I_Transaction _tst) {
    //		return getDao().execute(_tst);
    //	}

}
