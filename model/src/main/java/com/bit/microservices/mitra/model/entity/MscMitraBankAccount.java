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
//@Table(name = "msc_mitra_bankaccount")
//public class MscMitraBankAccount extends AuditField implements Serializable {
//
//    @Serial
//    private static final long serialVersionUID = -1680288176567477665L;
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
//    @Column(name = "ms_bank_id",columnDefinition = "varchar(255)",nullable = false)
//    private String msBankId;
//
//    @Column(name = "ms_bank_id",columnDefinition = "varchar(255)",nullable = false)
//    private String msBankCode;
//
//    @Column(name = "no",columnDefinition = "varchar(255)",nullable = false)
//    private String no;
//
//    @Column(name = "name",columnDefinition = "varchar(255)",nullable = false)
//    private String name;
//
//    @Column(name = "branch",columnDefinition = "varchar(255)",nullable = false)
//    private String branch;
//    @Column(name = "currency",columnDefinition = "varchar(255)",nullable = false)
//    private String currency;
//    @Column(name = "remarks",columnDefinition = "varchar(255)",nullable = false)
//    private String remarks = "";
//}
