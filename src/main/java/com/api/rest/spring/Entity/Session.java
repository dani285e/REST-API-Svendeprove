package com.api.rest.spring.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.crypto.Data;
import java.util.Date;


@Entity
@Table
public class Session {

    @Id
    @GeneratedValue
    private Integer id;
    private String sessionId;
    private Boolean sessionStatus;
    private Integer userId;
    private Date created;
    private Date expiresOn;


    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(Date expiresOn) {
        this.expiresOn = expiresOn;
    }

    public Integer getId() {
        return id;
    }


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Boolean getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(Boolean sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
