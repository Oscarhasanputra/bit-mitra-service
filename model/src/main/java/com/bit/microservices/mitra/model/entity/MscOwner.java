package com.bit.microservices.mitra.model.entity;

import com.bit.microservices.mitra.model.constant.mscowner.IdentityTypeEnum;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "identity_type",columnDefinition = "varchar(255)",nullable = false)
    private IdentityTypeEnum identityType;

    @Column(name = "identity_no",columnDefinition = "varchar(255)",nullable = false)
    private String identityNo;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "identity_issuer_id", referencedColumnName = "id", insertable = false, updatable = false)
    })
    private MsCountry msCountry;

    @Column(name = "identity_issuer_id",columnDefinition = "varchar(255)",nullable = false)
    private String identityIssuerId;
    @Column(name = "identity_issuer_code",columnDefinition = "varchar(255)",nullable = false)
    private String identityIssuerCode;

    @Column(name = "mobile_number",columnDefinition = "varchar(255)",nullable = false)
    private String mobileNumber;









}
