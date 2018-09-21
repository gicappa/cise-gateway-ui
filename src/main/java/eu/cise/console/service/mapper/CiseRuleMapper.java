package eu.cise.console.service.mapper;

import eu.cise.console.domain.*;
import eu.cise.console.service.dto.CiseRuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CiseRule and its DTO CiseRuleDTO.
 */
@Mapper(componentModel = "spring", uses = {CiseServiceProfileMapper.class, CiseRuleSetMapper.class})
public interface CiseRuleMapper extends EntityMapper<CiseRuleDTO, CiseRule> {

    @Mapping(source = "ciseServiceProfile.id", target = "ciseServiceProfileId")
    @Mapping(source = "ciseRuleSet.id", target = "ciseRuleSetId")
    CiseRuleDTO toDto(CiseRule ciseRule);

    @Mapping(source = "ciseServiceProfileId", target = "ciseServiceProfile")
    @Mapping(source = "ciseRuleSetId", target = "ciseRuleSet")
    CiseRule toEntity(CiseRuleDTO ciseRuleDTO);

    default CiseRule fromId(Long id) {
        if (id == null) {
            return null;
        }
        CiseRule ciseRule = new CiseRule();
        ciseRule.setId(id);
        return ciseRule;
    }
}
