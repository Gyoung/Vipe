package com.vipe.service.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zengjiyang on 2016/1/26.
 */
public class BaseEntity implements Serializable {

    /**
     * 主键id
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "UUID")
    private String id;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 实体（数据记录）创建时间
     */
    private Date createAt;

    /**
     * 获取实体主键id
     *
     * @return id
     */
    public String getId() {

        return id;
    }

    /**
     * 设置实体主键id
     *
     * @param id
     */
    @XmlTransient
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取实体（数据记录）添加时间
     *
     * @return addtime
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * 设置实体（数据记录）添加时间
     *
     * @param createAt
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

}
