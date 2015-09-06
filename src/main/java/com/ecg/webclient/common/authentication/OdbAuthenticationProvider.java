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

import com.ecg.webclient.feature.administration.persistence.api.IClientDto;
import com.ecg.webclient.feature.administration.persistence.api.IGroupDto;
import com.ecg.webclient.feature.administration.persistence.api.IGroupRepository;
import com.ecg.webclient.feature.administration.persistence.api.IRoleDto;
import com.ecg.webclient.feature.administration.persistence.api.IRoleRepository;
import com.ecg.webclient.feature.administration.persistence.api.IUserDto;
import com.ecg.webclient.feature.administration.persistence.api.IUserRepository;

@Component("odbAuthenticationProvider")
public class OdbAuthenticationProvider implements AuthenticationProvider
{
    @Autowired
    IUserRepository    userRepository;
    @Autowired
    IGroupRepository   groupRepository;
    @Autowired
    IRoleRepository    roleRepository;
    @Autowired
    AuthenticationUtil util;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (userRepository.isUserAuthorized(login, password))
        {
            IUserDto user = userRepository.getUserByLogin(login);
            IClientDto defaultClient = userRepository.getDefaultClientForUser(user);
            util.setSelectedClient(defaultClient);

            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            // zugeordnete Rollen für den Client setzen
            for (IGroupDto group : groupRepository.getGroupsForIds(user.getGroupRidObjects()))
            {
                if (groupRepository.getClientForGroupId(group.getRid()).getRid().toString()
                        .equals(defaultClient.getRid().toString()))
                {
                    for (IRoleDto role : roleRepository.getRolesForIds(group.getRoleRidObjects()))
                    {
                        OdbGrantedAuthoritiy newAuth = new OdbGrantedAuthoritiy(role.getName());
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
