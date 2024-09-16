package ci.digitalacademy.monetab.services;

import ci.digitalacademy.monetab.services.dto.RoleUserDTO;
import ci.digitalacademy.monetab.services.dto.TeacherDTO;

import java.util.List;
import java.util.Optional;

public interface RoleUserService {

    RoleUserDTO save(RoleUserDTO roleUserDTO);

    RoleUserDTO update(RoleUserDTO roleUserDTO);

    Optional<RoleUserDTO> findOne(Long id);

    RoleUserDTO update(RoleUserDTO roleUserDTO, Long id);

    List<RoleUserDTO> initRoles(List<RoleUserDTO> roleUsers);

    List<RoleUserDTO> findAll();

    void delete(Long id);

    RoleUserDTO partialUpdate(RoleUserDTO roleUserDTO, Long id);
}
