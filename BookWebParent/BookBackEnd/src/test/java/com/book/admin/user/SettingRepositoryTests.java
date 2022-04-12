package com.book.admin.user;

import com.book.admin.user.repository.SettingRepository;
import com.book.common.entity.Setting;
import com.book.common.entity.SettingCategory;
import com.sun.tools.javac.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class SettingRepositoryTests {

    @Autowired
    SettingRepository settingRepository;

    @Test
    public void testCreateGeneralSettings() {
        Setting siteName = new Setting("SITE_NAME", "KidBook", SettingCategory.GENERAL);

        settingRepository.saveAll(List.of(siteName));

        Iterable<Setting> iterable = settingRepository.findAll();

        assertThat(iterable).size().isGreaterThan(0);
    }

    @Test
    public void testCreateCurrencySettings() {
        Setting currencyId = new Setting("CURRENCY_ID", "1", SettingCategory.CURRENCY);
        Setting symbol = new Setting("CURRENCY_SYMBOL", "$", SettingCategory.CURRENCY);
        Setting symbolPosition = new Setting("CURRENCY_SYMBOL_POSITION", "before", SettingCategory.CURRENCY);
        Setting decimalPointType = new Setting("DECIMAL_POINT_TYPE", "POINT", SettingCategory.CURRENCY);
        Setting decimalDigits = new Setting("DECIMAL_DIGITS", "2", SettingCategory.CURRENCY);
        Setting thousandsPointType = new Setting("THOUSANDS_POINT_TYPE", "COMMA", SettingCategory.CURRENCY);

        settingRepository.saveAll(List.of(currencyId, symbol, symbolPosition, decimalPointType,
                decimalDigits, thousandsPointType));

    }

    @Test
    public void testListSettingsByCategory() {
        List<Setting> settings = (List<Setting>) settingRepository.findByCategory(SettingCategory.GENERAL);

        settings.forEach(System.out::println);
    }
}