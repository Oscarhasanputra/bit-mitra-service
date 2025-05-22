package com.bit.microservices.mitra.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
@EntityListeners(AuditingEntityListener.class)
public class AuditField {


    @CreatedDate
    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = true, columnDefinition = "TIMESTAMP")
    private LocalDateTime modifiedDate;


    @CreatedBy
    @Column(nullable = true, columnDefinition = "varchar(255)")
    private String createdBy;


    @LastModifiedBy
    @Column(nullable = true, columnDefinition = "varchar(255)")
    private String modifiedBy;
}
