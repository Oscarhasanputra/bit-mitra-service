package com.bit.microservices.mitra.model.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Types;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@EqualsAndHashCode(callSuper=false)
@EntityListeners({AuditingEntityListener.class})
@Audited
@Entity
@Table(name = "msc_owner")
public class MscOwner extends AuditField implements Serializable {

    @Serial
    private static final long serialVersionUID = 1962013606460245641L;
    @Id
    @JdbcTypeCode(Types.VARCHAR)
    private String id;

    @PrePersist
    private void ensureId(){
        if(Objects.isNull(this.id) || this.id.isEmpty()){
            this.id= UUID.randomUUID().toString();
        }
    }

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "ms_account_id", referencedColumnName = "id", insertable = false, updatable = false)
    })
    private MsAccount msAccount;

    @Column(name = "ms_account_id",columnDefinition = "varchar(255)",nullable = false)
    private String msAccountId;

    @Column(name = "name",columnDefinition = "varchar(255)",nullable = false)
    private String name;


    @Column(name = "identity_type",columnDefinition = "varchar(255)",nullable = false)
    private String identityType;

    @Column(name = "identity_no",columnDefinition = "varchar(255)",nullable = false)
    private String identityNo;

    @Column(name = "identity_issuer",columnDefinition = "varchar(255)",nullable = false)
    private String identityIssuer;

    @Column(name = "mobile_number",columnDefinition = "varchar(255)",nullable = false)
    private String mobileNumber;









}
