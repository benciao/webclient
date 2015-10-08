package com.ecg.webclient.feature.administration.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecg.webclient.common.feature.FeatureRegistry;
import com.ecg.webclient.feature.administration.FeatureAdministration;
import com.ecg.webclient.feature.administration.accessrole.AdministrationFeature;
import com.ecg.webclient.feature.administration.accessrole.SecurityAdminAccessRole;
import com.ecg.webclient.feature.administration.accessrole.SetupSystemAccessRole;
import com.ecg.webclient.feature.administration.authentication.AuthenticationUtil;
import com.ecg.webclient.feature.administration.authentication.PasswordEncoder;
import com.ecg.webclient.feature.administration.service.ClientService;
import com.ecg.webclient.feature.administration.service.EnvironmentService;
import com.ecg.webclient.feature.administration.service.FeatureService;
import com.ecg.webclient.feature.administration.service.GroupService;
import com.ecg.webclient.feature.administration.service.RoleService;
import com.ecg.webclient.feature.administration.service.UserService;
import com.ecg.webclient.feature.administration.viewmodell.ClientConfig;
import com.ecg.webclient.feature.administration.viewmodell.ClientDto;
import com.ecg.webclient.feature.administration.viewmodell.ClientProperties;
import com.ecg.webclient.feature.administration.viewmodell.EnvironmentDto;
import com.ecg.webclient.feature.administration.viewmodell.FeatureConfig;
import com.ecg.webclient.feature.administration.viewmodell.GroupConfig;
import com.ecg.webclient.feature.administration.viewmodell.GroupDto;
import com.ecg.webclient.feature.administration.viewmodell.PropertyDto;
import com.ecg.webclient.feature.administration.viewmodell.RoleConfig;
import com.ecg.webclient.feature.administration.viewmodell.RoleDto;
import com.ecg.webclient.feature.administration.viewmodell.UserConfig;
import com.ecg.webclient.feature.administration.viewmodell.UserDto;
import com.ecg.webclient.feature.administration.viewmodell.validator.ClientDtoValidator;
import com.ecg.webclient.feature.administration.viewmodell.validator.EnvironmentDtoValidator;
import com.ecg.webclient.feature.administration.viewmodell.validator.GroupDtoValidator;
import com.ecg.webclient.feature.administration.viewmodell.validator.PropertyDtoValidator;
import com.ecg.webclient.feature.administration.viewmodell.validator.UserDtoValidator;

/**
 * Controller zur Bearbeitung von Requests aus Administrationsdialogen.
 * 
 * @author arndtmar
 *
 */
@Controller
@RequestMapping(value = "/admin")
public class AdministrationController
{
    static final Logger        logger                            = LogManager
                                                                         .getLogger(AdministrationController.class
                                                                                 .getName());
    static final String        PROPERTY_NAME_SETUP_USER_PASSWORD = "sec.setup.user.pw";

    @Autowired
    private FeatureRegistry    featureRegistry;

    @Autowired
    private ClientService      clientService;

    @Autowired
    private RoleService        roleService;

    @Autowired
    private GroupService       groupService;

    @Autowired
    private UserService        userService;

    @Autowired
    private FeatureService     featureService;

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private AuthenticationUtil authUtil;

    @Autowired
    private Environment        env;

    @Autowired
    ClientDtoValidator         clientDtoValidator;

    @Autowired
    PropertyDtoValidator       propertyDtoValidator;

    @Autowired
    UserDtoValidator           userDtoValidator;

    @Autowired
    GroupDtoValidator          groupDtoValidator;

    @Autowired
    EnvironmentDtoValidator    environmentDtoValidator;

    /**
     * Behandelt GET-Requests vom Typ "/admin".
     * 
     * @return Template
     */
    @RequestMapping(method = RequestMethod.GET)
    public String load()
    {
        return getLoadingRedirectTemplate() + "administration";
    }

    /**
     * Behandelt POST-Requests vom Typ "/admin". Aktualisiert serverseitig das ausgewählte Feature.
     * 
     * @return Template
     */
    @RequestMapping(method = RequestMethod.POST)
    public String load(@ModelAttribute("currentFeature") FeatureAdministration feature)
    {
        featureRegistry.updateActiveFeature(feature);
        return getLoadingRedirectTemplate() + "administration";
    }

    @PreAuthorize("hasRole('" + AdministrationFeature.KEY + "_" + SecurityAdminAccessRole.KEY
            + "') OR hasRole('" + AdministrationFeature.KEY + "_" + SetupSystemAccessRole.KEY + "')")
    @RequestMapping(value = "/user/loginas/{userId}", method = RequestMethod.GET)
    public String loginAsUser(Model model, @PathVariable("userId") String userId)
    {
        authUtil.loginAsUser(Long.parseLong(userId));

        return "/main";
    }

    /**
     * Behandelt POST-Requests vom Typ "/admin/usergroup/save". Speichert Änderungen an Benutzergruppen.
     * 
     * @return Template
     */
    @PreAuthorize("hasRole('" + AdministrationFeature.KEY + "_" + SecurityAdminAccessRole.KEY
            + "') OR hasRole('" + AdministrationFeature.KEY + "_" + SetupSystemAccessRole.KEY + "')")
    @RequestMapping(value = "/usergroup/save", method = RequestMethod.POST)
    public String save(@Valid GroupConfig groupConfig, BindingResult bindingResult)
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
            return getLoadingRedirectTemplate() + "userGroup";
        }

        groupService.saveGroups(updateDtos);

        return "redirect:";
    }

    /**
     * Behandelt POST-Requests vom Typ "/admin/userrole/save". Speichert Änderungen an Benutzerrollen.
     * 
     * @return Template
     */
    @PreAuthorize("hasRole('" + AdministrationFeature.KEY + "_" + SecurityAdminAccessRole.KEY
            + "') OR hasRole('" + AdministrationFeature.KEY + "_" + SetupSystemAccessRole.KEY + "')")
    @RequestMapping(value = "/userrole/save", method = RequestMethod.POST)
    public String save(@Valid RoleConfig roleConfig, BindingResult bindingResult)
    {
        List<RoleDto> updateDtos = new ArrayList<RoleDto>();
        List<RoleDto> deleteDtos = new ArrayList<RoleDto>();

        for (RoleDto dto : roleConfig.getRoles())
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

        roleService.deleteRoles(deleteDtos);

        roleConfig.removeDeleted();

        if (bindingResult.hasErrors())
        {
            return getLoadingRedirectTemplate() + "userRole";
        }

        roleService.saveRoles(updateDtos);

        return "redirect:";
    }

    /**
     * Behandelt POST-Requests vom Typ "/admin/user/save". Speichert Änderungen an Benutzern.
     * 
     * @return Template
     */
    @PreAuthorize("hasRole('" + AdministrationFeature.KEY + "_" + SecurityAdminAccessRole.KEY
            + "') OR hasRole('" + AdministrationFeature.KEY + "_" + SetupSystemAccessRole.KEY + "')")
    @RequestMapping(value = "/user/save", method = RequestMethod.POST)
    public String save(@Valid UserConfig userConfig, BindingResult bindingResult)
    {
        List<UserDto> updateDtos = new ArrayList<UserDto>();
        List<UserDto> deleteDtos = new ArrayList<UserDto>();

        for (UserDto dto : userConfig.getUsers())
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

        userService.deleteUsers(deleteDtos);

        userConfig.removeDeleted();

        if (bindingResult.hasErrors())
        {
            return getLoadingRedirectTemplate() + "user";
        }

        userService.saveUsers(updateDtos);

        return "redirect:";
    }

    /**
     * Behandelt POST-Requests vom Typ "/admin/client/save". Speichert Änderungen an Mandanten.
     * 
     * @return Template
     */
    @PreAuthorize("hasRole('" + AdministrationFeature.KEY + "_" + SecurityAdminAccessRole.KEY
            + "') OR hasRole('" + AdministrationFeature.KEY + "_" + SetupSystemAccessRole.KEY + "')")
    @RequestMapping(value = "/client/save", method = RequestMethod.POST)
    public String saveClient(@Valid ClientConfig clientConfig, BindingResult bindingResult)
    {
        List<ClientDto> updateDtos = new ArrayList<ClientDto>();
        List<ClientDto> deleteDtos = new ArrayList<ClientDto>();

        for (ClientDto dto : clientConfig.getClients())
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

        clientService.deleteClients(deleteDtos);
        updateSelectedClient(deleteDtos);

        clientConfig.removeDeleted();

        if (bindingResult.hasErrors())
        {
            return getLoadingRedirectTemplate() + "clientConfig";
        }

        clientService.saveClients(updateDtos);
        updateSelectedClient(updateDtos);

        return "redirect:";
    }

    /**
     * Behandelt POST-Requests vom Typ "/admin/environment/save". Speichert Änderungen an der Systemumgebung.
     * 
     * @return Template
     */
    @PreAuthorize("hasRole('" + AdministrationFeature.KEY + "_" + SecurityAdminAccessRole.KEY
            + "') OR hasRole('" + AdministrationFeature.KEY + "_" + SetupSystemAccessRole.KEY + "')")
    @RequestMapping(value = "/environment/save", method = RequestMethod.POST)
    public String saveEnvironment(@Valid EnvironmentDto environment, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return getLoadingRedirectTemplate() + "environment";
        }

        environmentService.saveEnvironment(environment);

        return "redirect:";
    }

    /**
     * Behandelt POST-Requests vom Typ "/admin/feature/save". Speichert Änderungen an Feature-Konfiguration.
     * 
     * @return Template
     */
    @PreAuthorize("hasRole('" + AdministrationFeature.KEY + "_" + SecurityAdminAccessRole.KEY
            + "') OR hasRole('" + AdministrationFeature.KEY + "_" + SetupSystemAccessRole.KEY + "')")
    @RequestMapping(value = "/feature/save", method = RequestMethod.POST)
    public String saveFeatures(@Valid FeatureConfig featureConfig, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return getLoadingRedirectTemplate() + "feature";
        }

        featureService.saveFeatures(featureConfig.getFeatures());

        return "redirect:";
    }

    /**
     * Behandelt POST-Requests vom Typ "/admin/clientp/save". Speichert Änderungen an Mandanteneigenschaften.
     * 
     * @return Template
     */
    @RequestMapping(value = "/clientp/save", method = RequestMethod.POST)
    public String saveProperties(@Valid ClientProperties clientProperties, BindingResult bindingResult)
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
            return getLoadingRedirectTemplate() + "clientProperties";
        }

        clientService.saveClient(updatedClient);
        authUtil.setSelectedClientWithNewAuthority(updatedClient);

        return "redirect:";
    }

    /**
     * Behandelt POST-Requests vom Typ "/admin/setup/system". Initialisiert das System mit Standardmandant,
     * Standardbenutzer usw.
     * 
     * @return Template
     */
    @RequestMapping(value = "/setup/system", method = RequestMethod.GET)
    @Transactional
    public String setupSystem()
    {
        // Mandant erstellen
        ClientDto setupClient = new ClientDto();
        setupClient.setEnabled(true);
        setupClient.setName("SETUP_CLIENT");
        setupClient.setDescription("Client to setup the system");
        setupClient.setColor("#ff0000");

        ClientDto savedClient = clientService.getClientByName("SETUP_CLIENT");

        if (savedClient == null)
        {
            savedClient = clientService.saveClient(setupClient);
            logger.info("SETUP_CLIENT created");
        }

        // Benutzerrollen erstellen
        List<RoleDto> roles = roleService.getAllRoles(false);

        GroupDto savedGroup = groupService.getGroupByName("SETUP_GROUP");

        if (savedGroup == null)
        {
            GroupDto setupGroup = new GroupDto();
            setupGroup.setClient(savedClient);
            setupGroup.setName("SETUP_GROUP");
            setupGroup.setDescription("Group to setup system");
            setupGroup.setEnabled(true);
            savedGroup = setupGroup;
            logger.info("SETUP_GROUP created");
        }

        String roleIds = "";
        for (RoleDto role : roles)
        {
            if (roleIds.isEmpty())
            {
                roleIds = Long.toString(role.getId());
            }
            else
            {
                roleIds = roleIds + "," + Long.toString(role.getId());
            }
        }
        savedGroup.setRoleIds(roleIds);

        savedGroup = groupService.saveGroup(savedGroup);

        String groupIds = "";
        groupIds = Long.toString(savedGroup.getId());

        UserDto setupUser = userService.getUserByLogin("setupuser");

        if (setupUser == null)
        {
            setupUser = new UserDto();
            setupUser.setLogin("setupuser");
            setupUser.setPassword(PasswordEncoder.encodeSimple(env
                    .getRequiredProperty(PROPERTY_NAME_SETUP_USER_PASSWORD)));
            setupUser.setFirstname("Setup");
            setupUser.setLastname("User");
            setupUser.setEnabled(true);
            setupUser.setChangePasswordOnNextLogin(false);

            setupUser.setEmail("setupuser@ecg-leipzig.de");
            setupUser.setInternal(true);
            setupUser.setGroupIds(groupIds);

            logger.info("Setup-User created");
        }

        setupUser.setDefaultClient(Long.toString(savedClient.getId()));
        userService.saveUser(setupUser);

        // Umgebung mit Eigenschaften erstellen
        EnvironmentDto detachedEnvironment = environmentService.getEnvironment();
        if (detachedEnvironment == null)
        {
            EnvironmentDto newEnvironment = new EnvironmentDto();
            newEnvironment.setPasswordChangeInterval(30);
            newEnvironment.setAllowedLoginAttempts(10);
            environmentService.saveEnvironment(newEnvironment);
        }

        return "login";
    }

    /**
     * Behandelt einen Ajax Request zum Anzeigen von ausschließlich Mandanten, welchen die zugeordneten
     * Gruppen angehören.
     * 
     * @return
     */
    @PreAuthorize("hasRole('" + AdministrationFeature.KEY + "_" + SecurityAdminAccessRole.KEY
            + "') OR hasRole('" + AdministrationFeature.KEY + "_" + SetupSystemAccessRole.KEY + "')")
    @RequestMapping(value = "/user/availableClients/{groupIds}/{userId}", method = RequestMethod.GET)
    public String showAvailableClients(Model model, @PathVariable("groupIds") String groupIds,
            @PathVariable("userId") String userId)
    {
        List<Long> realGroupIds = getGroupIds(groupIds);
        List<ClientDto> clients = clientService.getAssignedClientsForGroups(realGroupIds);
        UserDto user = userService.getUserById(Long.parseLong(userId));

        model.addAttribute("availableClients", clients);

        if (user != null)
        {
            model.addAttribute("defaultClient", user.getDefaultClient());
        }
        else
        {
            model.addAttribute("defaultClient", "");
        }

        return getLoadingRedirectTemplate() + "user :: availableClients";
    }

    /**
     * Behandelt GET-Requests vom Typ "/admin/client". Lädt alle Mandanten.
     * 
     * @return Template
     */
    @PreAuthorize("hasRole('" + AdministrationFeature.KEY + "_" + SecurityAdminAccessRole.KEY
            + "') OR hasRole('" + AdministrationFeature.KEY + "_" + SetupSystemAccessRole.KEY + "')")
    @RequestMapping(value = "/client", method = RequestMethod.GET)
    public String showClientConfig(Model model)
    {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClients(clientService.getAllClients(false));
        model.addAttribute("clientConfig", clientConfig);
        return getLoadingRedirectTemplate() + "clientconfig";
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

        return getLoadingRedirectTemplate() + "user :: clientGroups";
    }

    /**
     * Behandelt GET-Requests vom Typ "/admin/clientp". Lädt alle zum aktuell ausgewählten Mandanten gehörige
     * Mandanteneigenschaften.
     * 
     * @return Template
     */
    @RequestMapping(value = "/clientp", method = RequestMethod.GET)
    public String showClientProperties(Model model)
    {
        ClientProperties clientProperties = new ClientProperties();
        clientProperties.setProperties(authUtil.getSelectedClient().getProperties());
        model.addAttribute("clientProperties", clientProperties);
        return getLoadingRedirectTemplate() + "clientproperties";
    }

    /**
     * Behandelt GET-Requests vom Typ "/admin/environment". Lädt alle Umgebungseigenschaften.
     * 
     * @return Template
     */
    @PreAuthorize("hasRole('" + AdministrationFeature.KEY + "_" + SecurityAdminAccessRole.KEY
            + "') OR hasRole('" + AdministrationFeature.KEY + "_" + SetupSystemAccessRole.KEY + "')")
    @RequestMapping(value = "/environment", method = RequestMethod.GET)
    public String showEnvironment(Model model)
    {
        EnvironmentDto environment = environmentService.getEnvironment();
        model.addAttribute("environmentDto", environment);

        return getLoadingRedirectTemplate() + "environment";
    }

    /**
     * Behandelt GET-Requests vom Typ "/admin/feature". Lädt alle Features.
     * 
     * @return Template
     */
    @PreAuthorize("hasRole('" + AdministrationFeature.KEY + "_" + SecurityAdminAccessRole.KEY
            + "') OR hasRole('" + AdministrationFeature.KEY + "_" + SetupSystemAccessRole.KEY + "')")
    @RequestMapping(value = "/feature", method = RequestMethod.GET)
    public String showFeatureConfig(Model model)
    {
        FeatureConfig featureConfig = new FeatureConfig();
        featureConfig.setFeatures(featureService.getAllFeatures());
        model.addAttribute("featureConfig", featureConfig);

        return getLoadingRedirectTemplate() + "feature";
    }

    /**
     * Behandelt GET-Requests vom Typ "/admin/usergroup". Lädt alle zum aktuell ausgewählten Mandanten
     * gehörige Benutzergruppen und deren zugeordnete Benutzerrollen.
     * 
     * @return Template
     */
    @PreAuthorize("hasRole('" + AdministrationFeature.KEY + "_" + SecurityAdminAccessRole.KEY
            + "') OR hasRole('" + AdministrationFeature.KEY + "_" + SetupSystemAccessRole.KEY + "')")
    @RequestMapping(value = "/usergroup", method = RequestMethod.GET)
    public String showGroupConfig(Model model)
    {
        GroupConfig groupConfig = new GroupConfig();
        groupConfig.setGroups(groupService.getAllGroupsForClient(authUtil.getSelectedClient().getId()));
        groupConfig.setRoles(roleService.getAllRoles(false));
        model.addAttribute("groupConfig", groupConfig);

        return getLoadingRedirectTemplate() + "usergroup";
    }

    /**
     * Behandelt GET-Requests vom Typ "/admin/userrole". Lädt alle Benutzerrollen.
     * 
     * @return Template
     */
    @PreAuthorize("hasRole('" + AdministrationFeature.KEY + "_" + SecurityAdminAccessRole.KEY
            + "') OR hasRole('" + AdministrationFeature.KEY + "_" + SetupSystemAccessRole.KEY + "')")
    @RequestMapping(value = "/userrole", method = RequestMethod.GET)
    public String showRoleConfig(Model model)
    {
        RoleConfig roleConfig = new RoleConfig();
        roleConfig.setRoles(roleService.getAllRoles(false));
        model.addAttribute("roleConfig", roleConfig);

        return getLoadingRedirectTemplate() + "userrole";
    }

    /**
     * Behandelt GET-Requests vom Typ "/admin/user". Lädt alle Benutzer.
     * 
     * @return Template
     */
    @PreAuthorize("hasRole('" + AdministrationFeature.KEY + "_" + SecurityAdminAccessRole.KEY
            + "') OR hasRole('" + AdministrationFeature.KEY + "_" + SetupSystemAccessRole.KEY + "')")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String showUserConfig(Model model)
    {
        UserConfig userConfig = new UserConfig();
        userConfig.setUsers(userService.getAllUsers(false));
        model.addAttribute("userConfig", userConfig);

        return getLoadingRedirectTemplate() + "user";
    }

    protected String getLoadingRedirectTemplate()
    {
        return "feature/administration/";
    }

    @InitBinder("clientConfig")
    protected void initClientBinder(WebDataBinder binder)
    {
        binder.setValidator(clientDtoValidator);
    }

    @InitBinder("environmentDto")
    protected void initEnvironmentBinder(WebDataBinder binder)
    {
        binder.setValidator(environmentDtoValidator);
    }

    @InitBinder("groupConfig")
    protected void initGroupBinder(WebDataBinder binder)
    {
        binder.setValidator(groupDtoValidator);
    }

    @InitBinder("clientProperties")
    protected void initPropertyBinder(WebDataBinder binder)
    {
        binder.setValidator(propertyDtoValidator);
    }

    @InitBinder("userConfig")
    protected void initUserBinder(WebDataBinder binder)
    {
        binder.setValidator(userDtoValidator);
    }

    private void updateSelectedClient(List<ClientDto> clientDtos)
    {
        for (ClientDto clientDto : clientDtos)
        {
            if (authUtil.getSelectedClient().getId() == clientDto.getId())
            {
                authUtil.setSelectedClientWithNewAuthority(clientDto);
                break;
            }
        }
    }

    private static List<Long> getGroupIds(String groupIds)
    {
        List<Long> result = new ArrayList<Long>();

        List<String> ids = Arrays.asList(groupIds.split(","));

        for (String id : ids)
        {
            result.add(Long.parseLong(id));
        }

        return result.size() != 0 ? result : null;
    }
}
