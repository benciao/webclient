package com.ecg.webclient.common.controller;

import java.net.HttpCookie;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecg.webclient.common.ApplicationUtil;
import com.ecg.webclient.common.feature.FeatureRegistry;
import com.ecg.webclient.feature.administration.authentication.AuthenticationUtil;
import com.ecg.webclient.feature.administration.service.ClientService;
import com.ecg.webclient.feature.administration.service.UserService;
import com.ecg.webclient.feature.administration.service.remoteauthentication.RemoteSessionManager;
import com.ecg.webclient.feature.administration.viewmodell.ClientDto;
import com.ecg.webclient.feature.administration.viewmodell.UserDto;

@Scope("request")
@Controller
public class MainController
{
    static final Logger        logger = LogManager.getLogger(MainController.class.getName());

    @Autowired
    RemoteSessionManager       remoteSessionManager;
    @Autowired
    private FeatureRegistry    featureRegistry;
    @Autowired
    private ClientService      clientService;
    @Autowired
    UserService                userService;
    @Autowired
    private AuthenticationUtil authUtil;

    @Autowired
    private ApplicationUtil    util;

    public MainController()
    {
    }

    @RequestMapping(value = "/changeClient", method = RequestMethod.POST)
    public String changeClient(@ModelAttribute("selectedClient") Long selectedClient)
    {
        authUtil.setSelectedClientWithNewAuthority(clientService.getClient(selectedClient));
        featureRegistry.resetActiveFeature();
        return "/main";
    }

    @RequestMapping(value = "/main/tooglemenue/true", method = RequestMethod.GET)
    public void hideMenu()
    {
        util.setMenuMinimized(true);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loginOk(HttpServletRequest request, HttpServletResponse response)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        featureRegistry.resetActiveFeature();
        UserDto user = userService.getUserByLogin(auth.getName());
        ClientDto defaultClient = userService.getDefaultClientForUser(user);
        authUtil.setSelectedClient(defaultClient);

        // Anmeldung an Fremdsystemen versuchen
        try
        {
            List<HttpCookie> cookies = remoteSessionManager.createSessions(user.getId());

            for (HttpCookie httpCookie : cookies)
            {
                Cookie cookie = new Cookie(httpCookie.getName(), httpCookie.getValue());
                cookie.setDomain(httpCookie.getDomain());
                cookie.setPath(httpCookie.getPath());

                response.addCookie(cookie);
            }

        }
        catch (Exception ex)
        {
            logger.error("remote login failed.", ex);
        }

        return "/main";
    }

    @RequestMapping(value = "/main/tooglemenue/false", method = RequestMethod.GET)
    public void showMenu()
    {
        util.setMenuMinimized(false);
    }
}
