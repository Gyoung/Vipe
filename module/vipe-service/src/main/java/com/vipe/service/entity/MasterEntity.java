package com.vipe.service.entity;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

/**
 * 主表
 * Created by zengjiyang on 2016/3/31.
 */
public class MasterEntity extends BaseEntity {
    /**
     * 状态
     */
    protected int state;

    /**
     * 修改人
     */
    private String modifyBy;

    /**
     * 实体（数据记录）修改时间
     */
    private Date modifyAt;


    @XmlTransient
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    /**
     * 获取实体（数据记录）修改时间
     *
     * @return
     */
    public Date getModifyAt() {
        return modifyAt;
    }

    /**
     * 设置实体（数据记录）修改时间
     *
     * @param modifyAt
     */
    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    /**
     * 隐私字段
     */
    @Transient
    private String zoePrivacyFields;

    @XmlTransient
    public String getZoePrivacyFields() {
        return zoePrivacyFields;
    }

    public void setZoePrivacyFields(String zoePrivacyFields) {
        this.zoePrivacyFields = zoePrivacyFields;
    }
}
