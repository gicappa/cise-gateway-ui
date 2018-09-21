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
 * A CiseAuthority.
 */
@Entity
@Table(name = "cise_authority")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ciseauthority")
public class CiseAuthority implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "ciseAuthority")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CiseService> ciseServices = new HashSet<>();

    @OneToMany(mappedBy = "ciseAuthority")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CiseRuleSet> ciseRuleSets = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("")
    private User user;

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

    public CiseAuthority name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public CiseAuthority description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CiseService> getCiseServices() {
        return ciseServices;
    }

    public CiseAuthority ciseServices(Set<CiseService> ciseServices) {
        this.ciseServices = ciseServices;
        return this;
    }

    public CiseAuthority addCiseService(CiseService ciseService) {
        this.ciseServices.add(ciseService);
        ciseService.setCiseAuthority(this);
        return this;
    }

    public CiseAuthority removeCiseService(CiseService ciseService) {
        this.ciseServices.remove(ciseService);
        ciseService.setCiseAuthority(null);
        return this;
    }

    public void setCiseServices(Set<CiseService> ciseServices) {
        this.ciseServices = ciseServices;
    }

    public Set<CiseRuleSet> getCiseRuleSets() {
        return ciseRuleSets;
    }

    public CiseAuthority ciseRuleSets(Set<CiseRuleSet> ciseRuleSets) {
        this.ciseRuleSets = ciseRuleSets;
        return this;
    }

    public CiseAuthority addCiseRuleSet(CiseRuleSet ciseRuleSet) {
        this.ciseRuleSets.add(ciseRuleSet);
        ciseRuleSet.setCiseAuthority(this);
        return this;
    }

    public CiseAuthority removeCiseRuleSet(CiseRuleSet ciseRuleSet) {
        this.ciseRuleSets.remove(ciseRuleSet);
        ciseRuleSet.setCiseAuthority(null);
        return this;
    }

    public void setCiseRuleSets(Set<CiseRuleSet> ciseRuleSets) {
        this.ciseRuleSets = ciseRuleSets;
    }

    public User getUser() {
        return user;
    }

    public CiseAuthority user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        CiseAuthority ciseAuthority = (CiseAuthority) o;
        if (ciseAuthority.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ciseAuthority.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CiseAuthority{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
