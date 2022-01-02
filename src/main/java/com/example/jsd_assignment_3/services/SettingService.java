package com.example.jsd_assignment_3.services;

import com.example.jsd_assignment_3.entities.Setting;
import com.example.jsd_assignment_3.repositories.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;


public class SettingService {
    @Autowired
    private SettingRepository settingRepository;
    public boolean save(Setting setting){
        LocalDateTime localDate = LocalDateTime.now();
        setting.setLastUpdatedTime(localDate);
        try {
            settingRepository.save(setting);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public Setting getSetting(){
        return  settingRepository.getById(1L);
    }
}
