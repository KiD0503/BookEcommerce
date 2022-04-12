package com.book.admin.user.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.book.admin.FileUploadUtil;
import com.book.admin.user.GeneralSettingBag;
import com.book.admin.user.repository.CurrencyRepository;
import com.book.admin.user.service.SettingService;
import com.book.common.entity.Currency;
import com.book.common.entity.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SettingController {

    @Autowired
    private
    SettingService settingService;

    @Autowired private CurrencyRepository currencyRepository;

    @GetMapping("/settings")
    public String listAll(Model model) {
        List<Setting> listSettings = settingService.listAllSettings();
        List<Currency> listCurrencies = currencyRepository.findAllByOrderByNameAsc();

        model.addAttribute("listCurrencies", listCurrencies);

        for (Setting setting : listSettings) {
            model.addAttribute(setting.getKey(), setting.getValue());
        }

        return "settings/settings";
    }

    @PostMapping("/settings/save_general")
    public String saveGeneralSettings(HttpServletRequest request, RedirectAttributes ra) throws IOException {
        GeneralSettingBag settingBag = settingService.getGeneralSettings();

        saveCurrencySymbol(request, settingBag);

        updateSettingValuesFromForm(request, settingBag.list());

        ra.addFlashAttribute("message", "General settings have been saved.");

        return "redirect:/settings";
    }

    private void saveCurrencySymbol(HttpServletRequest request, GeneralSettingBag settingBag) {
        Integer currencyId = Integer.parseInt(request.getParameter("CURRENCY_ID"));
        Optional<Currency> findByIdResult = currencyRepository.findById(currencyId);

        if (findByIdResult.isPresent()) {
            Currency currency = findByIdResult.get();
            settingBag.updateCurrencySymbol(currency.getSymbol());
        }
    }

    private void updateSettingValuesFromForm(HttpServletRequest request, List<Setting> listSettings) {
        for (Setting setting : listSettings) {
            String value = request.getParameter(setting.getKey());
            if (value != null) {
                setting.setValue(value);
            }
        }

        settingService.saveAll(listSettings);
    }

    @PostMapping("/settings/save_mail_server")
    public String saveMailServerSetttings(HttpServletRequest request, RedirectAttributes ra) {
        List<Setting> mailServerSettings = settingService.getMailServerSettings();
        updateSettingValuesFromForm(request, mailServerSettings);

        ra.addFlashAttribute("message", "Mail server settings have been saved");

        return "redirect:/settings#mailServer";
    }

    @PostMapping("/settings/save_mail_templates")
    public String saveMailTemplateSetttings(HttpServletRequest request, RedirectAttributes ra) {
        List<Setting> mailTemplateSettings = settingService.getMailTemplateSettings();
        updateSettingValuesFromForm(request, mailTemplateSettings);

        ra.addFlashAttribute("message", "Mail template settings have been saved");

        return "redirect:/settings#mailTemplates";
    }
}
