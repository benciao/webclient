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
import com.ecg.webclient.feature.administration.service.ClientService;
import com.ecg.webclient.feature.administration.service.GroupService;
import com.ecg.webclient.feature.administration.service.RoleService;
import com.ecg.webclient.feature.administration.service.UserService;
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
	static final Logger		logger	= LogManager.getLogger(AdministrationController.class.getName());
	@Autowired
	private FeatureRegistry	featureRegistry;

	@Autowired
	private ClientService clientService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserService userService;

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
	 * Behandelt POST-Requests vom Typ "/admin". Aktualisiert serverseitig das
	 * ausgewählte Feature.
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
	 * Behandelt POST-Requests vom Typ "/admin/client/save". Speichert
	 * Änderungen an Mandanten.
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
	 * Behandelt POST-Requests vom Typ "/admin/clientp/save". Speichert
	 * Änderungen an Mandanteneigenschaften.
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
			if (dto.isDelete())
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
			return getLoadingRedirectTemplate() + "clientProperties";
		}

		clientService.saveClient(updatedClient);
		util.setSelectedClientWithNewAuthority(updatedClient);

		return "redirect:";
	}

	/**
	 * Behandelt POST-Requests vom Typ "/admin/usergroup/save". Speichert
	 * Änderungen an Benutzergruppen.
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
	 * Behandelt POST-Requests vom Typ "/admin/userrole/save". Speichert
	 * Änderungen an Benutzerrollen.
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
	 * Behandelt POST-Requests vom Typ "/admin/user/save". Speichert Änderungen
	 * an Benutzern.
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
	 * Behandelt POST-Requests vom Typ "/admin/setup/system". Initialisiert das
	 * System mit Standardmandant, Standardbenutzer usw.
	 * 
	 * @return Template
	 */
	@RequestMapping(value = "/setup/system", method = RequestMethod.GET)
	public String setupSystem()
	{
		ClientDto setupClient = new ClientDto();
		setupClient.setEnabled(true);
		setupClient.setName("SETUP_CLIENT");
		setupClient.setDescription("Client to setup the system");
		setupClient.setColor("#ff0000");

		ClientDto savedClient = clientService.getClientByName("Setup Client");

		if (savedClient == null)
		{
			savedClient = clientService.saveClient(setupClient);
			logger.info("SETUP_CLIENT created");
		}

		List<RoleDto> roles = roleService.getAllRoles(false);

		RoleDto setupRole = null;
		RoleDto savedRole = null;
		if (roles.isEmpty())
		{
			setupRole = new RoleDto();
			setupRole.setName("SETUP_ROLE");
			setupRole.setDescription("Role to setup the system");
			setupRole.setEnabled(true);

			savedRole = roleService.saveRole(setupRole);
			logger.info("SETUP_ROLE created");
		}

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
		if (savedRole != null)
		{
			roleIds = roleIds + "," + Long.toString(savedRole.getId());
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
			setupUser.setPassword(PasswordEncoder.encodeSimple("SetupSystem!"));
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

		return "login";
	}

	/**
	 * Behandelt einen Ajax Request zum Anzeigen von außschließlich Mandanten,
	 * welchen die zugeordneten Gruppen angehören.
	 * 
	 * @return
	 */
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
	@RequestMapping(value = "/client", method = RequestMethod.GET)
	public String showClientConfig(Model model)
	{
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.setClients(clientService.getAllClients(false));
		model.addAttribute("clientConfig", clientConfig);
		return getLoadingRedirectTemplate() + "clientConfig";
	}

	/**
	 * Behandelt einen Ajax Request zum Anzeigen von zu einem Mandanten
	 * gehörende Gruppen.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/clientgroups/{clientId}", method = RequestMethod.GET)
	public String showClientGroups(Model model, @PathVariable("clientId") String clientId)
	{
		List<GroupDto> groups = groupService.getAllGroupsForClient(Long.parseLong(clientId));

		model.addAttribute("groups", groups);

		return getLoadingRedirectTemplate() + "user :: clientGroups";
	}

	/**
	 * Behandelt GET-Requests vom Typ "/admin/clientp". Lädt alle zum aktuell
	 * ausgewählten Mandanten gehörige Mandanteneigenschaften.
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
	 * Behandelt GET-Requests vom Typ "/admin/usergroup". Lädt alle zum aktuell
	 * ausgewählten Mandanten gehörige Benutzergruppen und deren zugeordnete
	 * Benutzerrollen.
	 * 
	 * @return Template
	 */
	@RequestMapping(value = "/usergroup", method = RequestMethod.GET)
	public String showGroupConfig(Model model)
	{
		GroupConfig groupConfig = new GroupConfig();
		groupConfig.setGroups(groupService.getAllGroupsForClient(util.getSelectedClient().getId()));
		groupConfig.setRoles(roleService.getAllRoles(false));
		model.addAttribute("groupConfig", groupConfig);

		return getLoadingRedirectTemplate() + "usergroup";
	}

	/**
	 * Behandelt GET-Requests vom Typ "/admin/userrole". Lädt alle
	 * Benutzerrollen.
	 * 
	 * @return Template
	 */
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

	private void updateSelectedClient(List<ClientDto> clientDtos)
	{
		for (ClientDto clientDto : clientDtos)
		{
			if (util.getSelectedClient().getId() == clientDto.getId())
			{
				util.setSelectedClientWithNewAuthority(clientDto);
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
