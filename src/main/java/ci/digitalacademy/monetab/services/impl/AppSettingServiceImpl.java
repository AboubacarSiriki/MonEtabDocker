package ci.digitalacademy.monetab.services.impl;

import ci.digitalacademy.monetab.models.AppSetting;
import ci.digitalacademy.monetab.repositories.AppSettingRepository;
import ci.digitalacademy.monetab.services.AppSettingService;
import ci.digitalacademy.monetab.services.dto.AppSettingDTO;
import ci.digitalacademy.monetab.services.dto.RoleUserDTO;
import ci.digitalacademy.monetab.services.dto.SchoolDTO;
import ci.digitalacademy.monetab.services.dto.UserDTO;
import ci.digitalacademy.monetab.services.mapper.AppSettingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class AppSettingServiceImpl implements AppSettingService {

    private final AppSettingRepository appSettingRepository;
    private  final AppSettingMapper appSettingMapper;


    @Override
    public AppSettingDTO save(AppSettingDTO appSettingDTO) {
        log.debug("Resquest to save : {}",appSettingDTO);
        AppSetting appSetting = appSettingMapper.toEntity(appSettingDTO);
        appSetting= appSettingRepository.save(appSetting);

        return  appSettingMapper.toDto(appSetting);
    }

    @Override
    public AppSettingDTO update(AppSettingDTO appSettingDTO) {
        return null;
    }

    @Override
    public Optional<AppSettingDTO> findOne(Long id) {
        return appSettingRepository.findById(id).map(appSetting -> {
            return appSettingMapper.toDto(appSetting);
        });
    }

    @Override
    public List<AppSettingDTO> findAll() {
        return appSettingRepository.findAll().stream().map(appSetting -> {
            return appSettingMapper.toDto(appSetting);
        }).toList();
    }

    @Override
    public void delecte(Long id) {

    }

    @Override
    public List<AppSettingDTO> findAllBySmtpUserName(String smtpuserneme) {
        return List.of();
    }

    @Override
    public AppSettingDTO initApp(AppSettingDTO appSettingDTO) {
        log.debug("Request to init app {}", appSettingDTO);
        AppSettingDTO settingDTO = existingParameter();
        if (settingDTO == null){
            return save(appSettingDTO);
        }
        return settingDTO;

    }

    @Override
    public AppSettingDTO existingParameter() {
        log.debug("Request to check existing Parameter");
        List<AppSettingDTO> appSettingDTO = findAll();
        //récuperation du 1er enregistrement
        return appSettingDTO.stream().findFirst().orElse(null);

    }


    @Override
    public SchoolDTO initSchool(SchoolDTO schoolDTO, AppSettingDTO appSettingDTO) {
        return null;
    }

    @Override
    public List<RoleUserDTO> initRoles(List<RoleUserDTO> roleUserDTO) {
        return List.of();
    }

    @Override
    public void initUsers(List<UserDTO> userDTOS, List<RoleUserDTO> roleUserDTO, SchoolDTO schoolDTO) {

    }


}
