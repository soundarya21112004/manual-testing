package com.eagle.mas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "token_details")
public class TokenDetails {
    @Id
//    @SequenceGenerator(name = "seq", sequenceName = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private String refresh_token;

    @ManyToOne
    @JoinColumn(name="userid")
    private Userdetails userdetails;
    private boolean expired;
    private boolean revoked;
    @Column(name = "access_expired_at")
    private Date access_expiredAt;
    @Column(name = "access_created_at")
    private Date access_createdAt;
    @Column(name ="access_updated_at")
    private Date access_updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public Userdetails getUserdetails() {
        return userdetails;
    }

    public void setUserdetails(Userdetails userdetails) {
        this.userdetails = userdetails;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public Date getAccess_expiredAt() {
        return access_expiredAt;
    }

    public void setAccess_expiredAt(Date access_expiredAt) {
        this.access_expiredAt = access_expiredAt;
    }

    public Date getAccess_createdAt() {
        return access_createdAt;
    }

    public void setAccess_createdAt(Date access_createdAt) {
        this.access_createdAt = access_createdAt;
    }

    public Date getAccess_updatedAt() {
        return access_updatedAt;
    }

    public void setAccess_updatedAt(Date access_updatedAt) {
        this.access_updatedAt = access_updatedAt;
    }
}
