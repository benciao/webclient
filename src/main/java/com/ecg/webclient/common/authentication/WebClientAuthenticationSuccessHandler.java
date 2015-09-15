package com.ecg.webclient.common.authentication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ecg.webclient.feature.administration.service.UserService;
import com.ecg.webclient.feature.administration.viewmodell.UserDto;

/**
 * Dieser Handler entscheided, ob der Benutzer nach erfolgreichem Login sein Passwort ändern muss oder nicht.
 * 
 * @author arndtmar
 */
@Component("webClientAuthenticationSuccessHandler")
public class WebClientAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
    @Autowired
    UserService userService;

    RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication auth) throws IOException, ServletException
    {
        String login = auth.getName();
        UserDto user = userService.getUserByLogin(login);

        if (user.isChangePasswordOnNextLogin())
        {
            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            String password = auth.getCredentials().toString();
            auth.getAuthorities().forEach(e -> grantedAuths.add(e));
            
            grantedAuths.add(new DbGrantedAuthoritiy("SEC_FORCE_CHANGE_PASSWORD"));

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(login, password, grantedAuths));
            redirectStrategy.sendRedirect(request, response, "/changepw");
        }
        else
        {
            redirectStrategy.sendRedirect(request, response, "/");
        }
    }

}
