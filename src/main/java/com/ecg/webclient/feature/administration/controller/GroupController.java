package com.ecg.webclient.feature.administration.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecg.webclient.feature.administration.accessrole.AdministrationFeature;
import com.ecg.webclient.feature.administration.accessrole.SecurityAdminAccessRole;
import com.ecg.webclient.feature.administration.accessrole.SetupSystemAccessRole;
import com.ecg.webclient.feature.administration.authentication.AuthenticationUtil;
import com.ecg.webclient.feature.administration.service.GroupService;
import com.ecg.webclient.feature.administration.service.RoleService;
import com.ecg.webclient.feature.administration.viewmodell.GroupConfig;
import com.ecg.webclient.feature.administration.viewmodell.GroupDto;
import com.ecg.webclient.feature.administration.viewmodell.validator.GroupDtoValidator;

/**
 * Controller zur Bearbeitung von Requests aus Administrationsdialogen (Gruppen).
 * 
 * @author arndtmar
 *
 */
@Controller
@RequestMapping(value = "/admin/usergroup")
public class GroupController
{
    static final Logger        logger = LogManager.getLogger(GroupController.class.getName());

    @Autowired
    private GroupService       groupService;
    @Autowired
    private RoleService        roleService;
    @Autowired
    private AuthenticationUtil authUtil;
    @Autowired
    GroupDtoValidator          groupDtoValidator;

    /**
     * Behandelt POST-Requests vom Typ "/admin/usergroup/save". Speichert Änderungen an Benutzergruppen.
     * 
     * @return Template
     */
    @PreAuthorize("hasRole('" + AdministrationFeature.KEY + "_" + SecurityAdminAccessRole.KEY
            + "') OR hasRole('" + AdministrationFeature.KEY + "_" + SetupSystemAccessRole.KEY + "')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveGroup(@Valid GroupConfig groupConfig, BindingResult bindingResult)
    {
        List<GroupDto> updateDtos = new ArrayList<GroupDto>();
        List<GroupDto> deleteDtos = new ArrayList<GroupDto>();

        for (GroupDto dto : groupConfig.getGroups())
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

        groupService.deleteGroups(deleteDtos);

        groupConfig.removeDeleted();

        if (bindingResult.hasErrors())
        {
            return getLoadingRedirectTemplate();
        }

        groupService.saveGroups(updateDtos);

        return "redirect:";
    }

    /**
     * Behandelt einen Ajax Request zum Anzeigen von zu einem Mandanten gehörende Gruppen.
     * 
     * @return
     */
    @PreAuthorize("hasRole('" + AdministrationFeature.KEY + "_" + SecurityAdminAccessRole.KEY
            + "') OR hasRole('" + AdministrationFeature.KEY + "_" + SetupSystemAccessRole.KEY + "')")
    @RequestMapping(value = "/user/clientgroups/{clientId}", method = RequestMethod.GET)
    public String showClientGroups(Model model, @PathVariable("clientId") String clientId)
    {
        List<GroupDto> groups = groupService.getAllGroupsForClient(Long.parseLong(clientId));

        model.addAttribute("groups", groups);

        return "feature/administration/user :: clientGroups";
    }

    /**
     * Behandelt GET-Requests vom Typ "/admin/usergroup". Lädt alle zum aktuell ausgewählten Mandanten
     * gehörige Benutzergruppen und deren zugeordnete Benutzerrollen.
     * 
     * @return Template
     */
    @PreAuthorize("hasRole('" + AdministrationFeature.KEY + "_" + SecurityAdminAccessRole.KEY
            + "') OR hasRole('" + AdministrationFeature.KEY + "_" + SetupSystemAccessRole.KEY + "')")
    @RequestMapping(method = RequestMethod.GET)
    public String showGroupConfig(Model model)
    {
        GroupConfig groupConfig = new GroupConfig();
        groupConfig.setGroups(groupService.getAllGroupsForClient(authUtil.getSelectedClient().getId()));
        groupConfig.setRoles(roleService.getAllRoles(false));
        model.addAttribute("groupConfig", groupConfig);

        return getLoadingRedirectTemplate();
    }

    protected String getLoadingRedirectTemplate()
    {
        return "feature/administration/userGroup";
    }

    @InitBinder("groupConfig")
    protected void initGroupBinder(WebDataBinder binder)
    {
        binder.setValidator(groupDtoValidator);
    }
}
