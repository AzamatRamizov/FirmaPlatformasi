package com.example.firmaplatformasi.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;
import javax.persistence.*;
import javax.validation.constraints.Email;
import java.security.Timestamp;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Users implements UserDetails{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(nullable = false)
    private String ism;

    @Column(nullable = false)
    private String familiya;

    @Column(nullable = false)
    private String telefon;

    @Email
    @Column(unique = true)
    private String username;


    @Column(nullable = false)
    private String password;

    public Users(String ism, String familiya, String telefon, String username, String password, Lavozim lavozim, boolean enabled) {
        this.ism = ism;
        this.familiya = familiya;
        this.telefon = telefon;
        this.username = username;
        this.password = password;
        this.lavozim = lavozim;
        this.enabled = enabled;
    }

    private String emailpassword;

    @ManyToOne
    private Lavozim lavozim;

    @ManyToOne
    private Section section;

    @JoinColumn(updatable = false)
    @CreatedBy
    private Integer addBy;

    @LastModifiedBy
    @Column(nullable = true)
    private Integer upDateBy;

//    @CreationTimestamp
//    private Timestamp registeredTime;
//
//    @UpdateTimestamp
//    private Timestamp updatedTime;


    private boolean accountNonExpired=true;
    private boolean accountNonLocked=true;
    private boolean credentialsNonExpired=true;
    private boolean enabled=false;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

}
