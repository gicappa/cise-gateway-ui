package eu.cise.console.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import eu.cise.console.domain.enumeration.CiseServiceType;

import eu.cise.console.domain.enumeration.CiseServiceOperationType;

/**
 * A CiseService.
 */
@Entity
@Table(name = "cise_service")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ciseservice")
public class CiseService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", nullable = false)
    private CiseServiceType serviceType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "service_operation", nullable = false)
    private CiseServiceOperationType serviceOperation;

    @OneToOne
    @JoinColumn(unique = true)
    private CiseRuleSet ciseRuleSet;

    @ManyToOne
    @JsonIgnoreProperties("ciseServices")
    private CiseAuthority ciseAuthority;

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

    public CiseService name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CiseServiceType getServiceType() {
        return serviceType;
    }

    public CiseService serviceType(CiseServiceType serviceType) {
        this.serviceType = serviceType;
        return this;
    }

    public void setServiceType(CiseServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public CiseServiceOperationType getServiceOperation() {
        return serviceOperation;
    }

    public CiseService serviceOperation(CiseServiceOperationType serviceOperation) {
        this.serviceOperation = serviceOperation;
        return this;
    }

    public void setServiceOperation(CiseServiceOperationType serviceOperation) {
        this.serviceOperation = serviceOperation;
    }

    public CiseRuleSet getCiseRuleSet() {
        return ciseRuleSet;
    }

    public CiseService ciseRuleSet(CiseRuleSet ciseRuleSet) {
        this.ciseRuleSet = ciseRuleSet;
        return this;
    }

    public void setCiseRuleSet(CiseRuleSet ciseRuleSet) {
        this.ciseRuleSet = ciseRuleSet;
    }

    public CiseAuthority getCiseAuthority() {
        return ciseAuthority;
    }

    public CiseService ciseAuthority(CiseAuthority ciseAuthority) {
        this.ciseAuthority = ciseAuthority;
        return this;
    }

    public void setCiseAuthority(CiseAuthority ciseAuthority) {
        this.ciseAuthority = ciseAuthority;
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
        CiseService ciseService = (CiseService) o;
        if (ciseService.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ciseService.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CiseService{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", serviceType='" + getServiceType() + "'" +
            ", serviceOperation='" + getServiceOperation() + "'" +
            "}";
    }
}
