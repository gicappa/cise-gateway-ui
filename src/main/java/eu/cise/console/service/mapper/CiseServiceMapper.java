package eu.cise.console.service.mapper;

import eu.cise.console.domain.*;
import eu.cise.console.service.dto.CiseServiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CiseService and its DTO CiseServiceDTO.
 */
@Mapper(componentModel = "spring", uses = {CiseRuleSetMapper.class, CiseAuthorityMapper.class})
public interface CiseServiceMapper extends EntityMapper<CiseServiceDTO, CiseService> {

    @Mapping(source = "ciseRuleSet.id", target = "ciseRuleSetId")
    @Mapping(source = "ciseAuthority.id", target = "ciseAuthorityId")
    CiseServiceDTO toDto(CiseService ciseService);

    @Mapping(source = "ciseRuleSetId", target = "ciseRuleSet")
    @Mapping(source = "ciseAuthorityId", target = "ciseAuthority")
    CiseService toEntity(CiseServiceDTO ciseServiceDTO);

    default CiseService fromId(Long id) {
        if (id == null) {
            return null;
        }
        CiseService ciseService = new CiseService();
        ciseService.setId(id);
        return ciseService;
    }
}
