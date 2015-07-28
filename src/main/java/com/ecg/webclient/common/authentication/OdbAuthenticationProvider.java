package com.ecg.webclient.common.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.ecg.webclient.common.Util;
import com.ecg.webclient.feature.administration.persistence.api.IUserRepository;
import com.ecg.webclient.feature.administration.persistence.dbmodell.User;
import com.ecg.webclient.feature.administration.viewmodell.mapper.ClientMapper;

@Component("odbAuthenticationProvider")
public class OdbAuthenticationProvider implements AuthenticationProvider
{
    @Autowired
    IUserRepository userRepository;
    @Autowired
    Util            util;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (userRepository.isUserAuthorized(login, password))
        {
            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
            Authentication auth = new UsernamePasswordAuthenticationToken(login, password, grantedAuths);

            User user = userRepository.getUserByLogin(login);
            util.setSelectedClient(ClientMapper.mapToDto(user.getDefaultClient()));

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
