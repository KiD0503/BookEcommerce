package com.book.service;

import com.book.common.entity.Setting;
import com.book.common.entity.SettingCategory;
import com.book.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingService {
    @Autowired
    private SettingRepository settingRepository;


    public List<Setting> getGeneralSettings() {
        return settingRepository.findByTwoCategories(SettingCategory.GENERAL, SettingCategory.CURRENCY);
    }

}
