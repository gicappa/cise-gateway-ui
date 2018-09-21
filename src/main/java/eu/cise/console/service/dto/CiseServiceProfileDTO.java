package eu.cise.console.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import eu.cise.console.domain.enumeration.CiseCommunityType;
import eu.cise.console.domain.enumeration.CiseDataFreshnessType;
import eu.cise.console.domain.enumeration.CiseFunctionType;
import eu.cise.console.domain.enumeration.CiseSeaBasinType;
import eu.cise.console.domain.enumeration.CiseServiceRoleType;
import eu.cise.console.domain.enumeration.CiseServiceType;

/**
 * A DTO for the CiseServiceProfile entity.
 */
public class CiseServiceProfileDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String serviceId;

    private CiseCommunityType community;

    private String country;

    private CiseDataFreshnessType dataFreshness;

    private CiseFunctionType serviceFunction;

    private CiseSeaBasinType seaBasin;

    private CiseServiceRoleType serviceRole;

    private CiseServiceType serviceType;

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

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public CiseCommunityType getCommunity() {
        return community;
    }

    public void setCommunity(CiseCommunityType community) {
        this.community = community;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public CiseDataFreshnessType getDataFreshness() {
        return dataFreshness;
    }

    public void setDataFreshness(CiseDataFreshnessType dataFreshness) {
        this.dataFreshness = dataFreshness;
    }

    public CiseFunctionType getServiceFunction() {
        return serviceFunction;
    }

    public void setServiceFunction(CiseFunctionType serviceFunction) {
        this.serviceFunction = serviceFunction;
    }

    public CiseSeaBasinType getSeaBasin() {
        return seaBasin;
    }

    public void setSeaBasin(CiseSeaBasinType seaBasin) {
        this.seaBasin = seaBasin;
    }

    public CiseServiceRoleType getServiceRole() {
        return serviceRole;
    }

    public void setServiceRole(CiseServiceRoleType serviceRole) {
        this.serviceRole = serviceRole;
    }

    public CiseServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(CiseServiceType serviceType) {
        this.serviceType = serviceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CiseServiceProfileDTO ciseServiceProfileDTO = (CiseServiceProfileDTO) o;
        if (ciseServiceProfileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ciseServiceProfileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CiseServiceProfileDTO{" +
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
