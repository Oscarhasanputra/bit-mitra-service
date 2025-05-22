//package com.bit.microservices.mitra.model.entity;
//
//import jakarta.persistence.*;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.JdbcTypeCode;
//import org.hibernate.envers.Audited;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import java.io.Serial;
//import java.io.Serializable;
//import java.sql.Types;
//import java.util.Objects;
//import java.util.UUID;
//
//@Setter
//@Getter
//@EqualsAndHashCode(callSuper=false)
//@EntityListeners({AuditingEntityListener.class})
//@Audited
//@Entity
//@Table(name = "msc_mitra_category")
//public class MscMitraCategory extends AuditField implements Serializable {
//
//    @Serial
//    private static final long serialVersionUID = -8713459370581005783L;
//    @Id
//    @JdbcTypeCode(Types.VARCHAR)
//    private String id;
//
//    @PrePersist
//    private void ensureId(){
//        if(Objects.isNull(this.id) || this.id.isEmpty()){
//            this.id= UUID.randomUUID().toString();
//        }
//    }
//
//    @Column(name = "ms_mitra_id",columnDefinition = "varchar(255)",nullable = false)
//    private String msMitraId;
//
//    @Column(name = "category_mitra",columnDefinition = "varchar(255)",nullable = false)
//    private String categoryMitra;
//
//    @Column(name = "type_mitra",columnDefinition = "varchar(255)",nullable = false)
//    private String typeMitra;
//}
