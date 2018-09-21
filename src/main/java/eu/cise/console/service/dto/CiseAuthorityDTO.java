package eu.cise.console.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CiseAuthority entity.
 */
public class CiseAuthorityDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private String userId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CiseAuthorityDTO ciseAuthorityDTO = (CiseAuthorityDTO) o;
        if (ciseAuthorityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ciseAuthorityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CiseAuthorityDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", user='" + getUserId() + "'" +
            "}";
    }
}
