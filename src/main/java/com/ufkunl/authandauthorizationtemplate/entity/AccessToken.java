package com.ufkunl.authandauthorizationtemplate.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ACCESS_TOKEN")
@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
public class AccessToken {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "ACCESS_TOKEN_ID", nullable=false)
    private String accessTokenId;

    @OneToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    private User user;

    @Column(name = "TOKEN",nullable = false, unique = true)
    private String token;

    @Column(name = "EXPIRY_DATE",nullable = false)
    private LocalDateTime expiryDate;

}
