package eu.cise.console.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import eu.cise.console.domain.enumeration.CiseServiceType;
import eu.cise.console.domain.enumeration.CiseServiceOperationType;

/**
 * A DTO for the CiseService entity.
 */
public class CiseServiceDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private CiseServiceType serviceType;

    @NotNull
    private CiseServiceOperationType serviceOperation;

    private Long ciseRuleSetId;

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

    public CiseServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(CiseServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public CiseServiceOperationType getServiceOperation() {
        return serviceOperation;
    }

    public void setServiceOperation(CiseServiceOperationType serviceOperation) {
        this.serviceOperation = serviceOperation;
    }

    public Long getCiseRuleSetId() {
        return ciseRuleSetId;
    }

    public void setCiseRuleSetId(Long ciseRuleSetId) {
        this.ciseRuleSetId = ciseRuleSetId;
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

        CiseServiceDTO ciseServiceDTO = (CiseServiceDTO) o;
        if (ciseServiceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ciseServiceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CiseServiceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", serviceType='" + getServiceType() + "'" +
            ", serviceOperation='" + getServiceOperation() + "'" +
            ", ciseRuleSet=" + getCiseRuleSetId() +
            ", ciseAuthority=" + getCiseAuthorityId() +
            "}";
    }
}
