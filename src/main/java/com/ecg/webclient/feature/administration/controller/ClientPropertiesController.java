package com.ecg.webclient.feature.administration.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecg.webclient.feature.administration.authentication.AuthenticationUtil;
import com.ecg.webclient.feature.administration.service.ClientService;
import com.ecg.webclient.feature.administration.viewmodell.ClientDto;
import com.ecg.webclient.feature.administration.viewmodell.ClientProperties;
import com.ecg.webclient.feature.administration.viewmodell.PropertyDto;
import com.ecg.webclient.feature.administration.viewmodell.validator.PropertyDtoValidator;

/**
 * Controller zur Bearbeitung von Requests aus Administrationsdialogen (Mandanteneigenschaften).
 * 
 * @author arndtmar
 *
 */
@Controller
@RequestMapping(value = "/admin/clientp")
public class ClientPropertiesController
{
    static final Logger        logger = LogManager.getLogger(ClientPropertiesController.class.getName());

    @Autowired
    private AuthenticationUtil authUtil;
    @Autowired
    private ClientService      clientService;
    @Autowired
    PropertyDtoValidator       propertyDtoValidator;

    /**
     * Behandelt POST-Requests vom Typ "/admin/clientp/save". Speichert Änderungen an Mandanteneigenschaften.
     * 
     * @return Template
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveClientProperties(@Valid ClientProperties clientProperties, BindingResult bindingResult)
    {
        List<PropertyDto> updateDtos = new ArrayList<PropertyDto>();
        List<PropertyDto> deleteDtos = new ArrayList<PropertyDto>();

        for (PropertyDto dto : clientProperties.getProperties())
        {
            if (dto.isDelete())
            {
                deleteDtos.add(dto);
            }
            else
            {
                updateDtos.add(dto);
            }
        }

        ClientDto updatedClient = authUtil.getSelectedClient();
        updatedClient.setProperties(updateDtos);
        clientProperties.removeDeleted();

        if (bindingResult.hasErrors())
        {
            return getLoadingRedirectTemplate();
        }

        clientService.saveClient(updatedClient);
        authUtil.setSelectedClientWithNewAuthority(updatedClient);

        return "redirect:";
    }

    /**
     * Behandelt GET-Requests vom Typ "/admin/clientp". Lädt alle zum aktuell ausgewählten Mandanten gehörige
     * Mandanteneigenschaften.
     * 
     * @return Template
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showClientProperties(Model model)
    {
        ClientProperties clientProperties = new ClientProperties();
        clientProperties.setProperties(authUtil.getSelectedClient().getProperties());
        model.addAttribute("clientProperties", clientProperties);
        return getLoadingRedirectTemplate();
    }

    protected String getLoadingRedirectTemplate()
    {
        return "feature/administration/clientproperties";
    }

    @InitBinder("clientProperties")
    protected void initPropertyBinder(WebDataBinder binder)
    {
        binder.setValidator(propertyDtoValidator);
    }
}