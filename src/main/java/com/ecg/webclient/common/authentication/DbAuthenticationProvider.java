package com.ecg.webclient.common.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.ecg.webclient.feature.administration.service.GroupService;
import com.ecg.webclient.feature.administration.service.RoleService;
import com.ecg.webclient.feature.administration.service.UserService;
import com.ecg.webclient.feature.administration.viewmodell.ClientDto;
import com.ecg.webclient.feature.administration.viewmodell.GroupDto;
import com.ecg.webclient.feature.administration.viewmodell.RoleDto;
import com.ecg.webclient.feature.administration.viewmodell.UserDto;

@Component("dbAuthenticationProvider")
public class DbAuthenticationProvider implements AuthenticationProvider
{
	@Autowired
	UserService			userService;
	@Autowired
	GroupService		groupService;
	@Autowired
	RoleService			roleService;
	@Autowired
	AuthenticationUtil	util;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{
		String login = authentication.getName();
		String password = authentication.getCredentials().toString();

		if (userService.isUserAuthorized(login, password))
		{
			UserDto user = userService.getUserByLogin(login);
			ClientDto defaultClient = userService.getDefaultClientForUser(user);
			util.setSelectedClient(defaultClient);

			List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
			// zugeordnete Rollen für den Client setzen
			for (GroupDto group : groupService.getGroupsForIds(user.getGroupIdObjects()))
			{
				if (groupService.getClientForGroupId(group.getId()).getId() == defaultClient.getId())
				{
					for (RoleDto role : roleService.getRolesForIds(group.getRoleIdObjects()))
					{
						DbGrantedAuthoritiy newAuth = new DbGrantedAuthoritiy(role.getName());
						grantedAuths.add(newAuth);
					}
				}
			}

			Authentication auth = new UsernamePasswordAuthenticationToken(login, password, grantedAuths);

			return auth;
		}

		return null;
	}

	@Override
	public boolean supports(Class<?> authentication)
	{
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}