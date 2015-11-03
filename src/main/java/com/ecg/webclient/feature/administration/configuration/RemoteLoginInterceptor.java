package com.ecg.webclient.feature.administration.configuration;

import java.net.HttpCookie;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ecg.webclient.feature.administration.service.UserService;
import com.ecg.webclient.feature.administration.service.remoteauthentication.RemoteSessionManager;
import com.ecg.webclient.feature.administration.viewmodell.UserDto;

/**
 * Dieser Interceptor wird ausgeführt nach erfolgreichem Login um die Cookies der Fremdsysteme zu setzen. Wird
 * beim Pfad "/" getriggert.
 * 
 * @author arndtmar
 *
 */

public class RemoteLoginInterceptor extends HandlerInterceptorAdapter
{
    static final Logger  logger = LogManager.getLogger(RemoteLoginInterceptor.class.getName());

    @Autowired
    RemoteSessionManager remoteSessionManager;
    @Autowired
    UserService          userService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) throws Exception
    {
        // Anmeldung an Fremdsystemen versuchen
        try
        {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto user = userService.getUserByLogin(auth.getName());

            List<HttpCookie> cookies = remoteSessionManager.createSessions(user.getId());

            for (HttpCookie httpCookie : cookies)
            {
                Cookie cookie = new Cookie(httpCookie.getName(), httpCookie.getValue());
                cookie.setDomain(httpCookie.getDomain());
                cookie.setHttpOnly(true);
                cookie.setPath(httpCookie.getPath());

                response.addCookie(cookie);
            }

        }
        catch (Exception exc)
        {
            logger.error("remote login failed.", exc);
        }
    }
}
