package eu.cise.console.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CiseRuleSet.
 */
@Entity
@Table(name = "cise_rule_set")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ciseruleset")
public class CiseRuleSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 4096)
    @Column(name = "description", length = 4096)
    private String description;

    @OneToMany(mappedBy = "ciseRuleSet")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CiseRule> ciseRules = new HashSet<>();

    @OneToOne(mappedBy = "ciseRuleSet")
    @JsonIgnore
    private CiseService ciseService;

    @ManyToOne
    @JsonIgnoreProperties("ciseRuleSets")
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

    public CiseRuleSet name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public CiseRuleSet description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CiseRule> getCiseRules() {
        return ciseRules;
    }

    public CiseRuleSet ciseRules(Set<CiseRule> ciseRules) {
        this.ciseRules = ciseRules;
        return this;
    }

    public CiseRuleSet addCiseRule(CiseRule ciseRule) {
        this.ciseRules.add(ciseRule);
        ciseRule.setCiseRuleSet(this);
        return this;
    }

    public CiseRuleSet removeCiseRule(CiseRule ciseRule) {
        this.ciseRules.remove(ciseRule);
        ciseRule.setCiseRuleSet(null);
        return this;
    }

    public void setCiseRules(Set<CiseRule> ciseRules) {
        this.ciseRules = ciseRules;
    }

    public CiseService getCiseService() {
        return ciseService;
    }

    public CiseRuleSet ciseService(CiseService ciseService) {
        this.ciseService = ciseService;
        return this;
    }

    public void setCiseService(CiseService ciseService) {
        this.ciseService = ciseService;
    }

    public CiseAuthority getCiseAuthority() {
        return ciseAuthority;
    }

    public CiseRuleSet ciseAuthority(CiseAuthority ciseAuthority) {
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
        CiseRuleSet ciseRuleSet = (CiseRuleSet) o;
        if (ciseRuleSet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ciseRuleSet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CiseRuleSet{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
