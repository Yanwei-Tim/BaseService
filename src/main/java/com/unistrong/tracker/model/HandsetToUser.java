package com.unistrong.tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 手持机与用户映射对象
 * @author wyr
 *
 */
@Entity
@Table(name="rd_handset_to_user")
public class HandsetToUser implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -7891894054338190744L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name="user_id")
    private long userId;
    
    @Column(name="handset_id")
    private String handsetId;

    
    public long getId () {
    
        return id;
    }

    
    public void setId (long id) {
    
        this.id = id;
    }

    
    public long getUserId () {
    
        return userId;
    }

    
    public void setUserId (long userId) {
    
        this.userId = userId;
    }

    
    public String getHandsetId () {
    
        return handsetId;
    }

    
    public void setHandsetId (String handsetId) {
    
        this.handsetId = handsetId;
    }
    
    
}
