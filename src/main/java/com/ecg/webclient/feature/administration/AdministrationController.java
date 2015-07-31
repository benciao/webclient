package com.ecg.webclient.feature.administration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecg.webclient.common.authentication.AuthenticationUtil;
import com.ecg.webclient.common.authentication.PasswordEncoder;
import com.ecg.webclient.common.feature.FeatureRegistry;
import com.ecg.webclient.feature.administration.persistence.api.IClientDto;
import com.ecg.webclient.feature.administration.persistence.api.IClientRepository;
import com.ecg.webclient.feature.administration.persistence.api.IGroupDto;
import com.ecg.webclient.feature.administration.persistence.api.IGroupRepository;
import com.ecg.webclient.feature.administration.persistence.api.IPropertyDto;
import com.ecg.webclient.feature.administration.persistence.api.IRoleDto;
import com.ecg.webclient.feature.administration.persistence.api.IRoleRepository;
import com.ecg.webclient.feature.administration.persistence.api.IUserDto;
import com.ecg.webclient.feature.administration.persistence.api.IUserRepository;
import com.ecg.webclient.feature.administration.viewmodell.ClientConfig;
import com.ecg.webclient.feature.administration.viewmodell.ClientDto;
import com.ecg.webclient.feature.administration.viewmodell.ClientProperties;
import com.ecg.webclient.feature.administration.viewmodell.GroupConfig;
import com.ecg.webclient.feature.administration.viewmodell.GroupDto;
import com.ecg.webclient.feature.administration.viewmodell.RoleConfig;
import com.ecg.webclient.feature.administration.viewmodell.RoleDto;
import com.ecg.webclient.feature.administration.viewmodell.UserConfig;
import com.ecg.webclient.feature.administration.viewmodell.UserDto;

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
    static final Logger        logger = LogManager.getLogger(AdministrationController.class.getName());
    @Autowired
    private FeatureRegistry    featureRegistry;

    @Autowired
    private IClientRepository  clientRepository;

    @Autowired
    private IRoleRepository    roleRepository;

    @Autowired
    private IGroupRepository   groupRepository;

    @Autowired
    private IUserRepository    userRepository;

    @Autowired
    private AuthenticationUtil util;

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
    public String load(@ModelAttribute("isMinimized") Boolean isMinimized,
            @ModelAttribute("currentFeature") FeatureAdministration feature)
    {
        featureRegistry.updateActiveFeature(feature, isMinimized);
        return getLoadingRedirectTemplate() + "administration";
    }

    /**
     * Behandelt POST-Requests vom Typ "/admin/client/save". Speichert Änderungen an Mandanten.
     * 
     * @return Template
     */
    @RequestMapping(value = "/client/save", method = RequestMethod.POST)
    public String save(@Valid ClientConfig clientConfig, BindingResult bindingResult)
    {
        List<IClientDto> updateDtos = new ArrayList<IClientDto>();
        List<IClientDto> deleteDtos = new ArrayList<IClientDto>();

        for (IClientDto dto : clientConfig.getClients())
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

        clientRepository.deleteClients(deleteDtos);
        updateSelectedClient(deleteDtos);

        clientConfig.removeDeleted();

        if (bindingResult.hasErrors())
        {
            return getLoadingRedirectTemplate() + "clientConfig";
        }

        clientRepository.saveClients(updateDtos);
        updateSelectedClient(updateDtos);

        return "redirect:";
    }

    /**
     * Behandelt POST-Requests vom Typ "/admin/clientp/save". Speichert Änderungen an Mandanteneigenschaften.
     * 
     * @return Template
     */
    @RequestMapping(value = "/clientp/save", method = RequestMethod.POST)
    public String save(@Valid ClientProperties clientProperties, BindingResult bindingResult)
    {
        List<IPropertyDto> updateDtos = new ArrayList<IPropertyDto>();
        List<IPropertyDto> deleteDtos = new ArrayList<IPropertyDto>();

        for (IPropertyDto dto : clientProperties.getProperties())
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

        IClientDto updatedClient = util.getSelectedClient();
        updatedClient.setProperties(updateDtos);
        clientProperties.removeDeleted();

        if (bindingResult.hasErrors())
        {
            return getLoadingRedirectTemplate() + "clientProperties";
        }

        clientRepository.saveClient(updatedClient);
        util.setSelectedClientWithNewAuthority(updatedClient);

        return "redirect:";
    }

    /**
     * Behandelt POST-Requests vom Typ "/admin/usergroup/save". Speichert Änderungen an Benutzergruppen.
     * 
     * @return Template
     */
    @RequestMapping(value = "/usergroup/save", method = RequestMethod.POST)
    public String save(@Valid GroupConfig groupConfig, BindingResult bindingResult)
    {
        List<IGroupDto> updateDtos = new ArrayList<IGroupDto>();
        List<IGroupDto> deleteDtos = new ArrayList<IGroupDto>();

        for (IGroupDto dto : groupConfig.getGroups())
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

        groupRepository.deleteGroups(deleteDtos);

        groupConfig.removeDeleted();

        if (bindingResult.hasErrors())
        {
            return getLoadingRedirectTemplate() + "userGroup";
        }

        groupRepository.saveGroups(updateDtos);

        return "redirect:";
    }

    /**
     * Behandelt POST-Requests vom Typ "/admin/userrole/save". Speichert Änderungen an Benutzerrollen.
     * 
     * @return Template
     */
    @RequestMapping(value = "/userrole/save", method = RequestMethod.POST)
    public String save(@Valid RoleConfig roleConfig, BindingResult bindingResult)
    {
        List<IRoleDto> updateDtos = new ArrayList<IRoleDto>();
        List<IRoleDto> deleteDtos = new ArrayList<IRoleDto>();

        for (IRoleDto dto : roleConfig.getRoles())
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

        roleRepository.deleteRoles(deleteDtos);

        roleConfig.removeDeleted();

        if (bindingResult.hasErrors())
        {
            return getLoadingRedirectTemplate() + "userRole";
        }

        roleRepository.saveRoles(updateDtos);

        return "redirect:";
    }

    /**
     * Behandelt POST-Requests vom Typ "/admin/user/save". Speichert Änderungen an Benutzern.
     * 
     * @return Template
     */
    @RequestMapping(value = "/user/save", method = RequestMethod.POST)
    public String save(@Valid UserConfig userConfig, BindingResult bindingResult)
    {
        List<IUserDto> updateDtos = new ArrayList<IUserDto>();
        List<IUserDto> deleteDtos = new ArrayList<IUserDto>();

        for (IUserDto dto : userConfig.getUsers())
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

        userRepository.deleteUsers(deleteDtos);

        userConfig.removeDeleted();

        if (bindingResult.hasErrors())
        {
            return getLoadingRedirectTemplate() + "user";
        }

        userRepository.saveUsers(updateDtos);

        return "redirect:";
    }

    /**
     * Behandelt POST-Requests vom Typ "/admin/setup/system". Initialisiert das System mit Standardmandant,
     * Standardbenutzer usw.
     * 
     * @return Template
     */
    @RequestMapping(value = "/setup/system", method = RequestMethod.GET)
    public String setupSystem()
    {
        IClientDto setupClient = new ClientDto();
        setupClient.setEnabled(true);
        setupClient.setName("SETUP_CLIENT");
        setupClient.setDescription("Client to setup the system");
        setupClient.setColor("#ff0000");

        IClientDto savedClient = clientRepository.getClientByName("Setup Client");

        if (savedClient == null)
        {
            savedClient = clientRepository.saveClient(setupClient);
            logger.info("SETUP_CLIENT created");
        }

        List<IRoleDto> roles = roleRepository.getAllRoles(false);

        IRoleDto setupRole = null;
        IRoleDto savedRole = null;
        if (roles.isEmpty())
        {
            setupRole = new RoleDto();
            setupRole.setName("SETUP_ROLE");
            setupRole.setDescription("Role to setup the system");
            setupRole.setEnabled(true);

            savedRole = roleRepository.saveRole(setupRole);
            logger.info("SETUP_ROLE created");
        }

        IGroupDto savedGroup = groupRepository.getGroupByName("SETUP_GROUP");

        if (savedGroup == null)
        {
            IGroupDto setupGroup = new GroupDto();
            setupGroup.setClient(savedClient);
            setupGroup.setName("SETUP_GROUP");
            setupGroup.setDescription("Group to setup system");
            setupGroup.setEnabled(true);
            savedGroup = setupGroup;
            logger.info("SETUP_GROUP created");
        }

        List<Object> roleRids = new ArrayList<Object>();
        for (IRoleDto role : roles)
        {
            roleRids.add(role.getRid());
        }
        if (savedRole != null)
        {
            roleRids.add(savedRole.getRid());
        }
        savedGroup.setRoleRids(roleRids);

        savedGroup = groupRepository.saveGroup(savedGroup);

        List<Object> groupRids = new ArrayList<Object>();
        groupRids.add(savedGroup.getRid());

        IUserDto setupUser = userRepository.getUserByLogin("setupuser");

        if (setupUser == null)
        {
            setupUser = new UserDto();
            setupUser.setLogin("setupuser");
            setupUser.setPassword(PasswordEncoder.encodeSimple("SetupSystem!"));
            setupUser.setFirstname("Setup");
            setupUser.setLastname("User");
            setupUser.setEnabled(true);
            setupUser.setChangePasswordOnNextLogin(false);

            setupUser.setEmail("setupuser@ecg-leipzig.de");
            setupUser.setType(true);
            setupUser.setGroupRids(groupRids);

            logger.info("Setup-User created");
        }

        setupUser.setDefaultClientRid(savedClient.getRid());
        userRepository.saveUser(setupUser);

        return "login";
    }

    /**
     * Behandelt einen Ajax Request zum Anzeigen von außschließlich Mandanten, welchen die zugeordneten
     * Gruppen angehören.
     * 
     * @return
     */
    @RequestMapping(value = "/user/availableClients/{groupRids}/{userRid}", method = RequestMethod.GET)
    public String showAvailableClients(Model model, @PathVariable("groupRids") String groupRids,
            @PathVariable("userRid") String userRid)
    {
        List<Object> realGroupRids = getGroupRids(groupRids);
        List<IClientDto> clients = clientRepository.getAssignedClientsForGroups(realGroupRids);
        IUserDto user = userRepository.getUserById("#" + userRid);

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
    @RequestMapping(value = "/client", method = RequestMethod.GET)
    public String showClientConfig(Model model)
    {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClients(clientRepository.getAllClients(false));
        model.addAttribute("clientConfig", clientConfig);
        return getLoadingRedirectTemplate() + "clientConfig";
    }

    /**
     * Behandelt einen Ajax Request zum Anzeigen von zu einem Mandanten gehörende Gruppen.
     * 
     * @return
     */
    @RequestMapping(value = "/user/clientgroups/{clientId}", method = RequestMethod.GET)
    public String showClientGroups(Model model, @PathVariable("clientId") String clientId)
    {
        clientId = "#" + clientId;
        List<IGroupDto> groups = groupRepository.getAllGroupsForClient(clientId);

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
        clientProperties.setProperties(util.getSelectedClient().getProperties());
        model.addAttribute("clientProperties", clientProperties);
        return getLoadingRedirectTemplate() + "clientProperties";
    }

    /**
     * Behandelt GET-Requests vom Typ "/admin/usergroup". Lädt alle zum aktuell ausgewählten Mandanten
     * gehörige Benutzergruppen und deren zugeordnete Benutzerrollen.
     * 
     * @return Template
     */
    @RequestMapping(value = "/usergroup", method = RequestMethod.GET)
    public String showGroupConfig(Model model)
    {
        GroupConfig groupConfig = new GroupConfig();
        groupConfig.setGroups(groupRepository.getAllGroupsForClient(util.getSelectedClient().getRid()));
        groupConfig.setRoles(roleRepository.getAllRoles(false));
        model.addAttribute("groupConfig", groupConfig);

        return getLoadingRedirectTemplate() + "usergroup";
    }

    /**
     * Behandelt GET-Requests vom Typ "/admin/userrole". Lädt alle Benutzerrollen.
     * 
     * @return Template
     */
    @RequestMapping(value = "/userrole", method = RequestMethod.GET)
    public String showRoleConfig(Model model)
    {
        RoleConfig roleConfig = new RoleConfig();
        roleConfig.setRoles(roleRepository.getAllRoles(false));
        model.addAttribute("roleConfig", roleConfig);

        return getLoadingRedirectTemplate() + "userrole";
    }

    /**
     * Behandelt GET-Requests vom Typ "/admin/user". Lädt alle Benutzer.
     * 
     * @return Template
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String showUserConfig(Model model)
    {
        UserConfig userConfig = new UserConfig();
        userConfig.setUsers(userRepository.getAllUsers(false));
        model.addAttribute("userConfig", userConfig);

        return getLoadingRedirectTemplate() + "user";
    }

    protected String getLoadingRedirectTemplate()
    {
        return "feature/administration/";
    }

    private void updateSelectedClient(List<IClientDto> clientDtos)
    {
        for (IClientDto clientDto : clientDtos)
        {
            if (util.getSelectedClient().getRid().toString().equalsIgnoreCase(clientDto.getRid().toString()))
            {
                util.setSelectedClientWithNewAuthority(clientDto);
                break;
            }
        }
    }

    private static List<Object> getGroupRids(String groupRids)
    {
        List<Object> result = new ArrayList<Object>();

        List<String> rids = Arrays.asList(groupRids.split(","));

        for (String rid : rids)
        {
            result.add("#" + rid);
        }

        return result.size() != 0 ? result : null;
    }
}
