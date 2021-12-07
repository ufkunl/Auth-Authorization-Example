package com.ufkunl.authandauthorizationtemplate.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(	name = "USER",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "USER_NAME"),
                @UniqueConstraint(columnNames = "EMAIL")
        })
@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "USER_ID", nullable=false)
    private String userId;

    @NotBlank
    @Column(name = "USER_NAME")
    private String userName;

    @NotBlank
    @Email
    @Column(name = "EMAIL")
    private String email;

    @NotBlank
    @Size(max = 120)
    @Column(name = "PASSWORD")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> roles = new HashSet<>();

}
