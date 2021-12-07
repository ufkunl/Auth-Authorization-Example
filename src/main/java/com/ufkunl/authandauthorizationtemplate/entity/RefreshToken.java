package com.ufkunl.authandauthorizationtemplate.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;



/**
 * Created by Ufuk UNAL on 07.12.2021
 */
@Data
@Entity
@Table(name = "REFRESH_TOKEN")
@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
public class RefreshToken {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "REFRESH_TOKEN_ID", nullable=false)
    private String refreshTokenId;

    @OneToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    private User user;

    @Column(name = "TOKEN",nullable = false, unique = true)
    private String token;

    @Column(name = "EXPIRY_DATE",nullable = false)
    private LocalDateTime expiryDate;

    @Column(name = "REVOKED",nullable = false)
    private boolean revoked;

}
