package com.ecg.webclient.feature.administration.service.remoteauthentication;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ecg.webclient.feature.administration.service.RemoteAuthenticationService;
import com.ecg.webclient.feature.administration.service.RemoteLoginService;
import com.ecg.webclient.feature.administration.service.RemoteSystemService;
import com.ecg.webclient.feature.administration.viewmodell.RemoteLoginDto;
import com.ecg.webclient.feature.administration.viewmodell.RemoteSystemDto;

/**
 * Schnittstelle zur Verwaltung von Nutzer-Sessions auf Fremdsystemen
 */
@Scope("session")
@Component
public class RemoteSessionManager
{
    private RemoteLoginService          remoteLoginService;
    private RemoteSystemService         remoteSystemService;
    private RemoteAuthenticationService remoteAuthenticationService;
    private Map<Long, HttpCookie>       remoteSessionCookies = new HashMap<Long, HttpCookie>();

    /**
     * Erzeugt den {@link RemoteSessionManager} zur Verwaltung von {@link RemoteSession}s
     * 
     * @param remoteSessionCacheService
     *            Service zum Lesen und Schreiben der RemoteSession
     * @param remoteService
     *            Service zum Lesen von RemoteSystem (Fremdsystem) und RemoteLogin
     * @param remoteAuthentication
     *            Service zum Authentifizieren am Fremdsystem
     */
    @Autowired
    public RemoteSessionManager(RemoteLoginService remoteLoginService,
            RemoteSystemService remoteSystemService, RemoteAuthenticationService remoteAuthenticationService)
    {
        this.remoteLoginService = remoteLoginService;
        this.remoteSystemService = remoteSystemService;
        this.remoteAuthenticationService = remoteAuthenticationService;
    }

    /**
     * Erzeugt neue Nutzer-Sessions auf Fremdsystemen für den Benutzer. Der Benutzer wird entsprechend an den
     * für ihn konfigurierten Fremdsystemen angemeldet.
     *
     * @param userId
     *            Id des Nutzers
     * @return Liste der Sessions-Cookies für die Fremdsysteme an denen der Nutzer erfolgreich angemeldet
     *         werden konnte.
     */
    public List<HttpCookie> createSessions(final long userId)
    {
        List<RemoteLoginDto> enabledRemoteLogins = remoteLoginService.getEnabledRemoteLoginsForUserId(userId);

        for (RemoteLoginDto enabledRemoteLogin : enabledRemoteLogins)
        {
            final HttpCookie cookie = createLogin(enabledRemoteLogin);
            if (cookie != null)
            {
                remoteSessionCookies.put(Long.parseLong(enabledRemoteLogin.getRemoteSystemId()), cookie);
            }
        }

        return new ArrayList<HttpCookie>(remoteSessionCookies.values());
    }

    /**
     * Löscht alle Nutzer-Sessions auf Fremdsystemen. Der Benutzer wird entsprechend der vorhandenen Sessions
     * an den Fremdsystemen abgemeldet.
     *
     * @param userId
     *            Id des Nutzers
     */
    public void deleteSessions(final long userId)
    {
        List<RemoteLoginDto> remoteLogins = remoteLoginService.getAllRemoteLoginsForUserId(userId);

        for (RemoteLoginDto remoteLogin : remoteLogins)
        {
            remoteAuthenticationService.logout(
                    remoteSystemService.getRemoteSystemById(Long.parseLong(remoteLogin.getRemoteSystemId())),
                    remoteSessionCookies.get(Long.parseLong(remoteLogin.getRemoteSystemId())));
            remoteSessionCookies.remove(Long.parseLong(remoteLogin.getRemoteSystemId()));
        }
    }

    private HttpCookie createLogin(RemoteLoginDto enabledRemoteLogin)
    {
        RemoteSystemDto remoteSystem = remoteSystemService.getRemoteSystemById(Long
                .parseLong(enabledRemoteLogin.getRemoteSystemId()));
        if (!remoteSystem.isEnabled())
        {
            return null;
        }

        HttpCookie cookie = login(remoteSystem, enabledRemoteLogin);
        if (cookie == null)
        {
            return null;
        }

        return cookie;
    }

    private HttpCookie login(final RemoteSystemDto remoteSystem, final RemoteLoginDto remoteLogin)
    {
        final String login = remoteLogin.getRemoteSystemLoginName();
        final String password = remoteLogin.getRemoteSystemPassword();

        return remoteAuthenticationService.login(remoteSystem, login, password);
    }
}
