package com.ecg.webclient.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecg.webclient.common.authentication.AuthenticationUtil;
import com.ecg.webclient.common.feature.FeatureRegistry;
import com.ecg.webclient.feature.administration.service.ClientService;

@Controller
public class MainController
{
    @Autowired
    private FeatureRegistry     featureRegistry;

    @Autowired
    private ClientService clientRepository;

    @Autowired
    private AuthenticationUtil  util;

    @RequestMapping(value = "/changeClient", method = RequestMethod.POST)
    public String changeClient(@ModelAttribute("selectedClient") Object selectedClient)
    {
        util.setSelectedClientWithNewAuthority(clientRepository.getClient(selectedClient));
        featureRegistry.resetActiveFeature();
        return "/main";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loginOk()
    {
        featureRegistry.resetActiveFeature();
        return "/main";
    }
}
