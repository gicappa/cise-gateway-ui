package eu.cise.console.service.mapper;

import eu.cise.console.domain.*;
import eu.cise.console.service.dto.CiseServiceProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CiseServiceProfile and its DTO CiseServiceProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CiseServiceProfileMapper extends EntityMapper<CiseServiceProfileDTO, CiseServiceProfile> {


    @Mapping(target = "ciseRule", ignore = true)
    CiseServiceProfile toEntity(CiseServiceProfileDTO ciseServiceProfileDTO);

    default CiseServiceProfile fromId(Long id) {
        if (id == null) {
            return null;
        }
        CiseServiceProfile ciseServiceProfile = new CiseServiceProfile();
        ciseServiceProfile.setId(id);
        return ciseServiceProfile;
    }
}
