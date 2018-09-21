package eu.cise.console.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CiseRuleSet entity.
 */
public class CiseRuleSetDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @Size(max = 4096)
    private String description;

    private Long ciseAuthorityId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCiseAuthorityId() {
        return ciseAuthorityId;
    }

    public void setCiseAuthorityId(Long ciseAuthorityId) {
        this.ciseAuthorityId = ciseAuthorityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CiseRuleSetDTO ciseRuleSetDTO = (CiseRuleSetDTO) o;
        if (ciseRuleSetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ciseRuleSetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CiseRuleSetDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", ciseAuthority=" + getCiseAuthorityId() +
            "}";
    }
}
