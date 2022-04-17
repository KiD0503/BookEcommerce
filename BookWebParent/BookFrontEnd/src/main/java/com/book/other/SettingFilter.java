package com.book.other;


import com.book.common.entity.Setting;
import com.book.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Component
public class SettingFilter implements Filter {

    @Autowired
    private SettingService settingService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String url = servletRequest.getRequestURL().toString();

        if (url.endsWith(".css") || url.endsWith(".js") || url.endsWith(".png") ||
                url.endsWith(".jpg")) {
            chain.doFilter(request, response);
            return;
        }

        List<Setting> generalSettings = settingService.getGeneralSettings();

        generalSettings.forEach(setting -> {
            request.setAttribute(setting.getKey(), setting.getValue());
        });

        chain.doFilter(request, response);

    }

}