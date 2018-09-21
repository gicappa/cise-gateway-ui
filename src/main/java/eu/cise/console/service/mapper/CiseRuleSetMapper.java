package eu.cise.console.service.mapper;

import eu.cise.console.domain.*;
import eu.cise.console.service.dto.CiseRuleSetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CiseRuleSet and its DTO CiseRuleSetDTO.
 */
@Mapper(componentModel = "spring", uses = {CiseAuthorityMapper.class})
public interface CiseRuleSetMapper extends EntityMapper<CiseRuleSetDTO, CiseRuleSet> {

    @Mapping(source = "ciseAuthority.id", target = "ciseAuthorityId")
    CiseRuleSetDTO toDto(CiseRuleSet ciseRuleSet);

    @Mapping(target = "ciseRules", ignore = true)
    @Mapping(target = "ciseService", ignore = true)
    @Mapping(source = "ciseAuthorityId", target = "ciseAuthority")
    CiseRuleSet toEntity(CiseRuleSetDTO ciseRuleSetDTO);

    default CiseRuleSet fromId(Long id) {
        if (id == null) {
            return null;
        }
        CiseRuleSet ciseRuleSet = new CiseRuleSet();
        ciseRuleSet.setId(id);
        return ciseRuleSet;
    }
}
