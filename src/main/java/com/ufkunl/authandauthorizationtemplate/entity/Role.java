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
@Table(name = "role")
@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
public class Role {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "role_id", nullable=false)
    private String roleId;

    @NotBlank
    @Column(name = "role_name")
    private String roleName;

}
