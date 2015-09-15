package com.ecg.webclient.feature.administration.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecg.webclient.common.authentication.AuthenticationUtil;
import com.ecg.webclient.feature.administration.viewmodell.NewPassword;

/**
 * Controller zum Ändern des Passwortes durch den Benutzer.
 * 
 * @author arndtmar
 *
 */
@Controller
@RequestMapping(value = "/changepw")
public class ChangePasswordController
{
    static final Logger logger = LogManager.getLogger(ChangePasswordController.class.getName());

    @Autowired
    AuthenticationUtil  authUtil;

    /**
     * Behandelt POST-Requests vom Typ "/changepw". Speichert das neue Passwort am Nutzer.
     * 
     * @return Template
     */
    @Secured("SEC_FORCE_CHANGE_PASSWORD")
    @RequestMapping(method = RequestMethod.POST)
    public String changePassword(@ModelAttribute NewPassword newPassword, Model model)
    {
        authUtil.changeUserPassword(newPassword.getPw());
        return "/main";
    }

    /**
     * Behandelt GET-Requests vom Typ "/changepw".
     * 
     * @return Template
     */
    @RequestMapping(method = RequestMethod.GET)
    public String load(Model model)
    {
        model.addAttribute("newPassword", new NewPassword());

        return getLoadingRedirectTemplate() + "changepw";
    }

    protected String getLoadingRedirectTemplate()
    {
        return "feature/administration/";
    }
}
