package org.choo.security;

import lombok.extern.log4j.Log4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        log.warn("Login Success");
        List<String> roleNames = new ArrayList<>();
        authentication.getAuthorities().forEach(authority  -> {
            roleNames.add(authority.getAuthority());
        });
        log.warn("ROL NAMES: " + roleNames);

        if (roleNames.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/sample/admin");
            return;
        }
        if (roleNames.contains("ROLE_MEMBER")) {
            httpServletResponse.sendRedirect("/sample/member");
            return;
        }
        httpServletResponse.sendRedirect("/");
    }
}
