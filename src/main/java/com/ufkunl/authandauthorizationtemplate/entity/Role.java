package com.ufkunl.authandauthorizationtemplate.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


/**
 * Created by Ufuk UNAL on 07.12.2021
 */
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
