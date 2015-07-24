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

import com.ecg.webclient.common.Util;
import com.ecg.webclient.common.authentication.PasswordEncoder;
import com.ecg.webclient.common.feature.FeatureRegistry;
import com.ecg.webclient.feature.administration.persistence.api.IClientRepository;
import com.ecg.webclient.feature.administration.persistence.api.IGroupRepository;
import com.ecg.webclient.feature.administration.persistence.api.IRoleRepository;
import com.ecg.webclient.feature.administration.persistence.api.IUserRepository;
import com.ecg.webclient.feature.administration.persistence.dbmodell.Client;
import com.ecg.webclient.feature.administration.persistence.dbmodell.Group;
import com.ecg.webclient.feature.administration.persistence.dbmodell.Role;
import com.ecg.webclient.feature.administration.persistence.dbmodell.User;
import com.ecg.webclient.feature.administration.viewmodell.ClientConfig;
import com.ecg.webclient.feature.administration.viewmodell.ClientDto;
import com.ecg.webclient.feature.administration.viewmodell.ClientProperties;
import com.ecg.webclient.feature.administration.viewmodell.GroupConfig;
import com.ecg.webclient.feature.administration.viewmodell.GroupDto;
import com.ecg.webclient.feature.administration.viewmodell.PropertyDto;
import com.ecg.webclient.feature.administration.viewmodell.RoleConfig;
import com.ecg.webclient.feature.administration.viewmodell.RoleDto;
import com.ecg.webclient.feature.administration.viewmodell.UserConfig;
import com.ecg.webclient.feature.administration.viewmodell.UserDto;
import com.ecg.webclient.feature.administration.viewmodell.mapper.ClientMapper;
import com.ecg.webclient.feature.administration.viewmodell.mapper.GroupMapper;
import com.ecg.webclient.feature.administration.viewmodell.mapper.RoleMapper;
import com.ecg.webclient.feature.administration.viewmodell.mapper.UserMapper;

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
    static final Logger       logger = LogManager.getLogger(AdministrationController.class.getName());
    @Autowired
    private FeatureRegistry   featureRegistry;

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private IRoleRepository   roleRepository;

    @Autowired
    private IGroupRepository  groupRepository;

    @Autowired
    private IUserRepository   userRepository;

    @Autowired
    private Util              util;

    /**
     * Behandelt GET-Requests vom Typ "/admin".
     * 
     * @return Template
     */
    @RequestMapping(method = RequestMethod.GET)
    public String load()
    {
        return getLoadingRedirectTemplate();
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
        return getLoadingRedirectTemplate();
    }

    /**
     * Behandelt POST-Requests vom Typ "/admin/client/save". Speichert Änderungen an Mandanten.
     * 
     * @return Template
     */
    @RequestMapping(value = "/client/save", method = RequestMethod.POST)
    public String save(@Valid ClientConfig clientConfig, BindingResult bindingResult)
    {
        List<ClientDto> updateDtos = new ArrayList<ClientDto>();
        List<ClientDto> deleteDtos = new ArrayList<ClientDto>();

        for (ClientDto dto : clientConfig.getClients())
        {
            if (dto.getDelete())
            {
                deleteDtos.add(dto);
            }
            else
            {
                updateDtos.add(dto);
            }
        }

        clientRepository.deleteClients(ClientMapper.mapToEntities(deleteDtos));
        updateSelectedClient(deleteDtos);

        clientConfig.removeDeleted();

        if (bindingResult.hasErrors())
        {
            return "administration/clientConfig";
        }

        clientRepository.saveClients(ClientMapper.mapToEntities(updateDtos));
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
        List<PropertyDto> updateDtos = new ArrayList<PropertyDto>();
        List<PropertyDto> deleteDtos = new ArrayList<PropertyDto>();

        for (PropertyDto dto : clientProperties.getProperties())
        {
            if (dto.getDelete())
            {
                deleteDtos.add(dto);
            }
            else
            {
                updateDtos.add(dto);
            }
        }

        ClientDto updatedClient = util.getSelectedClient();
        updatedClient.setProperties(updateDtos);
        clientProperties.removeDeleted();

        if (bindingResult.hasErrors())
        {
            return "administration/clientProperties";
        }

        clientRepository.saveClient(ClientMapper.mapToEntity(updatedClient));
        util.setSelectedClient(updatedClient);

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
        List<GroupDto> updateDtos = new ArrayList<GroupDto>();
        List<GroupDto> deleteDtos = new ArrayList<GroupDto>();

        for (GroupDto dto : groupConfig.getGroups())
        {
            if (dto.getDelete())
            {
                deleteDtos.add(dto);
            }
            else
            {
                updateDtos.add(dto);
            }
        }

        groupRepository.deleteGroups(GroupMapper.mapToEntities(deleteDtos));

        groupConfig.removeDeleted();

        if (bindingResult.hasErrors())
        {
            return "administration/userGroup";
        }

        List<Group> groups = GroupMapper.mapToEntities(updateDtos);
        groups.forEach(element -> element.setClient(ClientMapper.mapToEntity(util.getSelectedClient())));

        groupRepository.saveGroups(groups);

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
        List<RoleDto> updateDtos = new ArrayList<RoleDto>();
        List<RoleDto> deleteDtos = new ArrayList<RoleDto>();

        for (RoleDto dto : roleConfig.getRoles())
        {
            if (dto.getDelete())
            {
                deleteDtos.add(dto);
            }
            else
            {
                updateDtos.add(dto);
            }
        }

        roleRepository.deleteRoles(RoleMapper.mapToEntities(deleteDtos));

        roleConfig.removeDeleted();

        if (bindingResult.hasErrors())
        {
            return "administration/userRole";
        }

        roleRepository.saveRoles(RoleMapper.mapToEntities(updateDtos));

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
        List<UserDto> updateDtos = new ArrayList<UserDto>();
        List<UserDto> deleteDtos = new ArrayList<UserDto>();

        for (UserDto dto : userConfig.getUsers())
        {
            if (dto.getDelete())
            {
                deleteDtos.add(dto);
            }
            else
            {
                updateDtos.add(dto);
            }
        }

        userRepository.deleteUsers(UserMapper.mapToEntities(deleteDtos));

        userConfig.removeDeleted();

        if (bindingResult.hasErrors())
        {
            return "administration/user";
        }

        List<User> users = UserMapper.mapToEntities(updateDtos);
        userRepository.saveUsers(users);

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
        Client setupClient = new Client();
        setupClient.setEnabled(true);
        setupClient.setName("Setup Client");
        setupClient.setDescription("Client to setup the system");
        setupClient.setColor("#ff0000");

        Client savedClient = clientRepository.saveClient(setupClient);

        logger.info("Setup-Client created");

        List<Role> roles = roleRepository.getAllRoles();

        Role setupRole = null;
        Role savedRole = null;
        if (roles.isEmpty())
        {
            setupRole = new Role();
            setupRole.setName("Setup Role");
            setupRole.setDescription("Role to setup the system");
            setupRole.setEnabled(true);

            savedRole = roleRepository.saveRole(setupRole);
            logger.info("Setup-Role created");
        }

        Group setupGroup = new Group();
        setupGroup.setClient(savedClient);
        setupGroup.setName("Setup Group");
        setupGroup.setDescription("Group to setup system");
        setupGroup.setEnabled(true);

        List<Object> roleRids = new ArrayList<Object>();
        for (Role role : roles)
        {
            roleRids.add(role.getRid());
        }
        if (savedRole != null)
        {
            roleRids.add(savedRole.getRid());
        }
        setupGroup.setRoleRids(roleRids);

        Group savedGroup = groupRepository.saveGroup(setupGroup);
        logger.info("Setup-Group created");

        List<Object> groupRids = new ArrayList<Object>();
        groupRids.add(savedGroup.getRid());

        User setupUser = new User();
        setupUser.setLogin("setupuser");
        setupUser.setPassword(PasswordEncoder.encodeSimple("SetupSystem!"));
        setupUser.setFirstname("Setup");
        setupUser.setLastname("User");
        setupUser.setEnabled(true);
        setupUser.setChangePasswordOnNextLogin(false);
        setupUser.setDefaultClientRid(savedClient.getRid());
        setupUser.setEmail("setupuser@ecg-leipzig.de");
        setupUser.setType(true);
        setupUser.setGroupRids(groupRids);

        userRepository.saveUser(setupUser);;
        logger.info("Setup-User created");

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
        List<Client> clients = clientRepository.getAssignedClientsForGroups(realGroupRids);
        User user = userRepository.getUserById("#" + userRid);

        model.addAttribute("availableClients", ClientMapper.mapToDtos(clients));
        model.addAttribute("currentUser", UserMapper.mapToDto(user));

        return "administration/user :: availableClients";
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
        clientConfig.setClients(ClientMapper.mapToDtos(clientRepository.getAllClients()));
        model.addAttribute("clientConfig", clientConfig);
        return "administration/clientConfig";
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
        List<Group> groups = groupRepository.getAllGroupsForClient(clientId);

        model.addAttribute("groups", GroupMapper.mapToDtos(groups));

        return "administration/user :: clientGroups";
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
        return "administration/clientProperties";
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
        groupConfig.setGroups(GroupMapper.mapToDtos(groupRepository.getAllGroupsForClient(util
                .getSelectedClient().getRid())));
        groupConfig.setRoles(RoleMapper.mapToDtos(roleRepository.getAllRoles()));
        model.addAttribute("groupConfig", groupConfig);

        return "administration/usergroup";
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
        roleConfig.setRoles(RoleMapper.mapToDtos(roleRepository.getAllRoles()));
        model.addAttribute("roleConfig", roleConfig);

        return "administration/userrole";
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
        userConfig.setUsers(UserMapper.mapToDtos(userRepository.getAllUsers()));
        model.addAttribute("userConfig", userConfig);

        return "administration/user";
    }

    protected String getLoadingRedirectTemplate()
    {
        return "administration/administration";
    }

    private void updateSelectedClient(List<ClientDto> clientDtos)
    {
        for (ClientDto clientDto : clientDtos)
        {
            if (util.getSelectedClient().getRid().toString().equalsIgnoreCase(clientDto.getRid().toString()))
            {
                util.setSelectedClient(clientDto);
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
