package eu.cise.console.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import eu.cise.console.domain.enumeration.CiseRuleType;

/**
 * A CiseRule.
 */
@Entity
@Table(name = "cise_rule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ciserule")
public class CiseRule implements Serializable {

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
    @Column(name = "rule_type", nullable = false)
    private CiseRuleType ruleType;

    @Size(max = 4096)
    @Column(name = "entity_template", length = 4096)
    private String entityTemplate;

    @OneToOne
    @JoinColumn(unique = true)
    private CiseServiceProfile ciseServiceProfile;

    @ManyToOne
    @JsonIgnoreProperties("ciseRules")
    private CiseRuleSet ciseRuleSet;

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

    public CiseRule name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CiseRuleType getRuleType() {
        return ruleType;
    }

    public CiseRule ruleType(CiseRuleType ruleType) {
        this.ruleType = ruleType;
        return this;
    }

    public void setRuleType(CiseRuleType ruleType) {
        this.ruleType = ruleType;
    }

    public String getEntityTemplate() {
        return entityTemplate;
    }

    public CiseRule entityTemplate(String entityTemplate) {
        this.entityTemplate = entityTemplate;
        return this;
    }

    public void setEntityTemplate(String entityTemplate) {
        this.entityTemplate = entityTemplate;
    }

    public CiseServiceProfile getCiseServiceProfile() {
        return ciseServiceProfile;
    }

    public CiseRule ciseServiceProfile(CiseServiceProfile ciseServiceProfile) {
        this.ciseServiceProfile = ciseServiceProfile;
        return this;
    }

    public void setCiseServiceProfile(CiseServiceProfile ciseServiceProfile) {
        this.ciseServiceProfile = ciseServiceProfile;
    }

    public CiseRuleSet getCiseRuleSet() {
        return ciseRuleSet;
    }

    public CiseRule ciseRuleSet(CiseRuleSet ciseRuleSet) {
        this.ciseRuleSet = ciseRuleSet;
        return this;
    }

    public void setCiseRuleSet(CiseRuleSet ciseRuleSet) {
        this.ciseRuleSet = ciseRuleSet;
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
        CiseRule ciseRule = (CiseRule) o;
        if (ciseRule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ciseRule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CiseRule{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", ruleType='" + getRuleType() + "'" +
            ", entityTemplate='" + getEntityTemplate() + "'" +
            "}";
    }
}
