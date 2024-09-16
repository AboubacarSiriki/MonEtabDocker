package ci.digitalacademy.monetab.services.mapping;

import ci.digitalacademy.monetab.models.RoleUser;
import ci.digitalacademy.monetab.services.dto.RoleUserDTO;

public final class RoleUserMapping {

    private RoleUserMapping(){

    }

    public static void partialUpdate(RoleUser roleUser, RoleUserDTO roleUserDTO){
        if (roleUserDTO.getRole() != null){
            roleUser.setRole(roleUserDTO.getRole());
        }
    }
}
