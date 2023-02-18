package com.example.firmaplatformasi.Entity;

import com.example.firmaplatformasi.AutoLoader.AbstractEntity;
import com.example.firmaplatformasi.EnumStep.Huquqlar;
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
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@EntityListeners(AuditingEntityListener.class)
public class Lavozim{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String lavozimNomi;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<Huquqlar> huquqlars;

    @Column(nullable = false,columnDefinition = "text")
    private String izoh;

    public Lavozim(String lavozimNomi, List<Huquqlar> huquqlars, String izoh) {
        this.lavozimNomi = lavozimNomi;
        this.huquqlars = huquqlars;
        this.izoh = izoh;
    }

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
