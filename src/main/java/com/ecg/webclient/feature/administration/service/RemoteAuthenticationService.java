package com.ecg.webclient.feature.administration.service;

import java.net.HttpCookie;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ecg.webclient.feature.administration.service.remoteauthentication.HttpClient;
import com.ecg.webclient.feature.administration.service.remoteauthentication.RequestResult;
import com.ecg.webclient.feature.administration.viewmodell.RemoteSystemDto;

/**
 * Service zum Anmelden und Abmelden an Fremdsystemen per HTTP.
 * 
 * @author arndtmar
 *
 */
@Component
public class RemoteAuthenticationService
{
    static final Logger      logger = LogManager.getLogger(RemoteAuthenticationService.class.getName());

    private final HttpClient httpClient;

    public RemoteAuthenticationService()
    {
        httpClient = new HttpClient();
    }

    public HttpCookie login(final RemoteSystemDto remoteSystem, final String username,
            final String password)
    {
        final String content = createRequestParameters(remoteSystem, username, password);
        final RequestResult requestResult = httpClient.requestCookie(remoteSystem.getLoginUrl(), content);

        if (!requestResult.isOk())
        {
            return null;
        }

        return requestResult.getCookie();
    }

    public void logout(final RemoteSystemDto remoteSystem, final HttpCookie cookie)
    {
        final RequestResult requestResult = httpClient.executeRequestFor(remoteSystem.getLogoutUrl(), cookie);

        if (!requestResult.isOk())
        {
            logger.debug("Session with SessionId=" + cookie.toString() + " successfully logged out");
        }
    }

    private static String createRequestParameters(final RemoteSystemDto remoteSystem, final String username,
            final String password)
    {
        final StringBuilder content = new StringBuilder();
        content.append(remoteSystem.getLoginParameter());
        content.append("=");
        content.append(username);
        content.append("&");
        content.append(remoteSystem.getPasswordParameter());
        content.append("=");
        content.append(password);

        return content.toString();
    }
}
