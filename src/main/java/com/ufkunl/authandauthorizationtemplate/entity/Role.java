package com.ufkunl.authandauthorizationtemplate.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "ROLE")
@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
public class Role {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "ROLE_ID", nullable=false)
    private String roleId;

    @NotBlank
    @Column(name = "ROLE_NAME")
    private String roleName;

}
