package com.example.firmaplatformasi.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.security.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Topshiriq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nomi;

    @Column(nullable = false)
    private String matni;

    @Column(nullable = false)
    private String deadline;

    @OneToOne
    private Users hodim;

    private boolean holati;

    @JoinColumn(updatable = false)
    @CreatedBy
    private Integer addBy;

    @LastModifiedBy
    private Integer upDateBy;

//    @CreationTimestamp
//    private Timestamp registeredTime;
//
//    @UpdateTimestamp
//    private Timestamp updatedTime;
}
