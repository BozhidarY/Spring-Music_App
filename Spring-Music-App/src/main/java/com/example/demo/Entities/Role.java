package com.example.demo.Entities;

import jakarta.persistence.*;
//import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "Roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Integer id;

    private String authority;

    public Role(Integer id, String authority) {
        this.id = id;
        this.authority = authority;
    }
    public Role(){

    }

    public Role(String authority) {
        this.authority = authority;
    }


//    @Override
//    public String getAuthority() {
//        return this.authority;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
