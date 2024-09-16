package ci.digitalacademy.monetab.services.mapping;

import ci.digitalacademy.monetab.models.Teacher;
import ci.digitalacademy.monetab.models.User;
import ci.digitalacademy.monetab.services.dto.TeacherDTO;
import ci.digitalacademy.monetab.services.dto.UserDTO;

public final class UserMapping {

    private UserMapping(){

    }

    public static void partialUpdate(User user, UserDTO userDTO){
        if (userDTO.getSpeudo() != null){
            user.setSpeudo(userDTO.getSpeudo());
        }
        if (userDTO.getPassword()!=null){
            user.setPassword(userDTO.getPassword());
        }

    }
}
