package eu.cise.console.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import eu.cise.console.domain.enumeration.CiseCommunityType;

import eu.cise.console.domain.enumeration.CiseDataFreshnessType;

import eu.cise.console.domain.enumeration.CiseFunctionType;

import eu.cise.console.domain.enumeration.CiseSeaBasinType;

import eu.cise.console.domain.enumeration.CiseServiceRoleType;

import eu.cise.console.domain.enumeration.CiseServiceType;

/**
 * A CiseServiceProfile.
 */
@Entity
@Table(name = "cise_service_profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ciseserviceprofile")
public class CiseServiceProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "service_id")
    private String serviceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "community")
    private CiseCommunityType community;

    @Column(name = "country")
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_freshness")
    private CiseDataFreshnessType dataFreshness;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_function")
    private CiseFunctionType serviceFunction;

    @Enumerated(EnumType.STRING)
    @Column(name = "sea_basin")
    private CiseSeaBasinType seaBasin;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_role")
    private CiseServiceRoleType serviceRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type")
    private CiseServiceType serviceType;

    @OneToOne(mappedBy = "ciseServiceProfile")
    @JsonIgnore
    private CiseRule ciseRule;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CiseServiceProfile name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceId() {
        return serviceId;
    }

    public CiseServiceProfile serviceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public CiseCommunityType getCommunity() {
        return community;
    }

    public CiseServiceProfile community(CiseCommunityType community) {
        this.community = community;
        return this;
    }

    public void setCommunity(CiseCommunityType community) {
        this.community = community;
    }

    public String getCountry() {
        return country;
    }

    public CiseServiceProfile country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public CiseDataFreshnessType getDataFreshness() {
        return dataFreshness;
    }

    public CiseServiceProfile dataFreshness(CiseDataFreshnessType dataFreshness) {
        this.dataFreshness = dataFreshness;
        return this;
    }

    public void setDataFreshness(CiseDataFreshnessType dataFreshness) {
        this.dataFreshness = dataFreshness;
    }

    public CiseFunctionType getServiceFunction() {
        return serviceFunction;
    }

    public CiseServiceProfile serviceFunction(CiseFunctionType serviceFunction) {
        this.serviceFunction = serviceFunction;
        return this;
    }

    public void setServiceFunction(CiseFunctionType serviceFunction) {
        this.serviceFunction = serviceFunction;
    }

    public CiseSeaBasinType getSeaBasin() {
        return seaBasin;
    }

    public CiseServiceProfile seaBasin(CiseSeaBasinType seaBasin) {
        this.seaBasin = seaBasin;
        return this;
    }

    public void setSeaBasin(CiseSeaBasinType seaBasin) {
        this.seaBasin = seaBasin;
    }

    public CiseServiceRoleType getServiceRole() {
        return serviceRole;
    }

    public CiseServiceProfile serviceRole(CiseServiceRoleType serviceRole) {
        this.serviceRole = serviceRole;
        return this;
    }

    public void setServiceRole(CiseServiceRoleType serviceRole) {
        this.serviceRole = serviceRole;
    }

    public CiseServiceType getServiceType() {
        return serviceType;
    }

    public CiseServiceProfile serviceType(CiseServiceType serviceType) {
        this.serviceType = serviceType;
        return this;
    }

    public void setServiceType(CiseServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public CiseRule getCiseRule() {
        return ciseRule;
    }

    public CiseServiceProfile ciseRule(CiseRule ciseRule) {
        this.ciseRule = ciseRule;
        return this;
    }

    public void setCiseRule(CiseRule ciseRule) {
        this.ciseRule = ciseRule;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CiseServiceProfile ciseServiceProfile = (CiseServiceProfile) o;
        if (ciseServiceProfile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ciseServiceProfile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CiseServiceProfile{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", serviceId='" + getServiceId() + "'" +
            ", community='" + getCommunity() + "'" +
            ", country='" + getCountry() + "'" +
            ", dataFreshness='" + getDataFreshness() + "'" +
            ", serviceFunction='" + getServiceFunction() + "'" +
            ", seaBasin='" + getSeaBasin() + "'" +
            ", serviceRole='" + getServiceRole() + "'" +
            ", serviceType='" + getServiceType() + "'" +
            "}";
    }
}
