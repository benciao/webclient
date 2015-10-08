package com.ecg.webclient.feature.administration.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component("ldapAuthenticationProvider")
public class LdapAuthenticationProvider implements AuthenticationProvider
{

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{
		String login = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication)
	{
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
