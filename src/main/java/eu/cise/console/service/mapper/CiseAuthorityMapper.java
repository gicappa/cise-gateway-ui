package eu.cise.console.service.mapper;

import eu.cise.console.domain.*;
import eu.cise.console.service.dto.CiseAuthorityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CiseAuthority and its DTO CiseAuthorityDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CiseAuthorityMapper extends EntityMapper<CiseAuthorityDTO, CiseAuthority> {

    @Mapping(source = "user.id", target = "userId")
    CiseAuthorityDTO toDto(CiseAuthority ciseAuthority);

    @Mapping(target = "ciseServices", ignore = true)
    @Mapping(target = "ciseRuleSets", ignore = true)
    @Mapping(source = "userId", target = "user")
    CiseAuthority toEntity(CiseAuthorityDTO ciseAuthorityDTO);

    default CiseAuthority fromId(Long id) {
        if (id == null) {
            return null;
        }
        CiseAuthority ciseAuthority = new CiseAuthority();
        ciseAuthority.setId(id);
        return ciseAuthority;
    }
}
