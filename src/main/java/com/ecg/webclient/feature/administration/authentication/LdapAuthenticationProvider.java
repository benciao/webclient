package com.ecg.webclient.feature.administration.authentication;

import javax.naming.ldap.LdapName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.ecg.webclient.feature.administration.service.LdapConfigService;
import com.ecg.webclient.feature.administration.viewmodell.LdapConfigDto;

@Component
public class LdapAuthenticationProvider
{
    @Autowired
    LdapTemplate      ldapTemplate;
    @Autowired
    LdapConfigService ldapConfigService;

    public boolean isAuthenticated(Authentication authentication) throws AuthenticationException
    {
        try
        {
            String login = authentication.getName();
            String password = authentication.getCredentials().toString();

            LdapConfigDto ldapConfig = ldapConfigService.getLdapConfig();
            setupLdapConnection(ldapConfig);

            Object[] filterParams =
            { login };
            Filter filter = LdapQueryBuilder.query().filter(ldapConfig.getFilter(), filterParams).filter();

            LdapName

            base = LdapUtils.newLdapName(ldapConfig.getBase());

            return ldapTemplate.authenticate(base, filter.encode(), password);
        }
        catch (Exception ex)
        {
            throw new InsufficientAuthenticationException("");
        }
    }

    private void setupLdapConnection(LdapConfigDto ldapConfig) throws Exception
    {
        LdapContextSource ctxSrc = new LdapContextSource();
        ctxSrc.setUrl(ldapConfig.getUrl());
        ctxSrc.setUserDn(ldapConfig.getUsername());
        ctxSrc.setPassword(ldapConfig.getPassword());
        ctxSrc.afterPropertiesSet();

        ldapTemplate = new LdapTemplate(ctxSrc);
    }

}
