package com.sms.zuulgateway.bean.auth;

import javax.persistence.*;

import com.sms.zuulgateway.bean.auth.audit.DateAudit;

@Entity
@Table(name = "jwttoken")
public class JwtToken extends DateAudit {
    @Id
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    public JwtToken() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}