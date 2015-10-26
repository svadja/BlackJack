/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sasav.blackjack.model.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author Vadim
 */
@Entity
@Table(name = "login_authorities")
public class LoginAuthority implements GrantedAuthority {

    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="username", referencedColumnName="username")
    private LoginDetails user;

    @Column(name = "authority", nullable = false)
    private String authority;

    public LoginAuthority() {
    }

    public LoginAuthority(LoginDetails user, String authority) {
        this.user = user;
        this.authority = authority;
    }

    public LoginAuthority(int id, LoginDetails user, String authority) {
        this.id = id;
        this.user = user;
        this.authority = authority;
    }

    public LoginDetails getUser() {
        return user;
    }

    public void setUser(LoginDetails user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}
