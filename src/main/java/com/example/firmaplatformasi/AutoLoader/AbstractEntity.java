package com.example.firmaplatformasi.AutoLoader;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.sql.Timestamp;

@MappedSuperclass
@Data
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(updatable = false)
    @CreatedBy
    private Integer addBy;

    @LastModifiedBy
    private Integer upDateBy;

    @CreationTimestamp
    private Timestamp registeredTime;

    @UpdateTimestamp
    private Timestamp updatedTime;
}
