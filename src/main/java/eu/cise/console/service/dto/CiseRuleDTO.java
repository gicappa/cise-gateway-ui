package eu.cise.console.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import eu.cise.console.domain.enumeration.CiseRuleType;

/**
 * A DTO for the CiseRule entity.
 */
public class CiseRuleDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private CiseRuleType ruleType;

    @Size(max = 4096)
    private String entityTemplate;

    private Long ciseServiceProfileId;

    private Long ciseRuleSetId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CiseRuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(CiseRuleType ruleType) {
        this.ruleType = ruleType;
    }

    public String getEntityTemplate() {
        return entityTemplate;
    }

    public void setEntityTemplate(String entityTemplate) {
        this.entityTemplate = entityTemplate;
    }

    public Long getCiseServiceProfileId() {
        return ciseServiceProfileId;
    }

    public void setCiseServiceProfileId(Long ciseServiceProfileId) {
        this.ciseServiceProfileId = ciseServiceProfileId;
    }

    public Long getCiseRuleSetId() {
        return ciseRuleSetId;
    }

    public void setCiseRuleSetId(Long ciseRuleSetId) {
        this.ciseRuleSetId = ciseRuleSetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CiseRuleDTO ciseRuleDTO = (CiseRuleDTO) o;
        if (ciseRuleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ciseRuleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CiseRuleDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", ruleType='" + getRuleType() + "'" +
            ", entityTemplate='" + getEntityTemplate() + "'" +
            ", ciseServiceProfile=" + getCiseServiceProfileId() +
            ", ciseRuleSet=" + getCiseRuleSetId() +
            "}";
    }
}
