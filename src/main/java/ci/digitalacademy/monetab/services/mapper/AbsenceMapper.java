package ci.digitalacademy.monetab.services.mapper;

import ci.digitalacademy.monetab.models.Absence;
import ci.digitalacademy.monetab.services.dto.AbsenceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AbsenceMapper extends EntityMapper<AbsenceDTO, Absence>{
}
