package com.ecg.webclient.feature.administration.service.remoteauthentication;

import java.net.HttpCookie;

/**
 * Das RequestResult ist das Ergebnis bei Kommunikation mit einem HTTP-Service
 */
public class RequestResult
{
    private final int        responseCode;
    private final boolean    ok;
    private final String     responseText;
    private final HttpCookie cookie;

    /**
     * Erzeugt ein RequestResult als Ergebnis der Kommunikation mit einem Http-Service
     * 
     * @param responseCode
     *            Code des Anfrageergebnisses
     * @param responseText
     *            Nachricht des Anfrageergebnisses
     * @param cookie
     *            Cookie des Anfrageergebnisses
     * @param ok
     *            OK-Status des Anfrageergebnisses
     */
    public RequestResult(final int responseCode, final String responseText, final HttpCookie cookie,
            final boolean ok)
    {
        this.responseCode = responseCode;
        this.responseText = responseText;
        this.cookie = cookie;
        this.ok = ok;
    }

    /**
     * Gibt den Cookie des Anfrageergebnisses zurück
     */
    public HttpCookie getCookie()
    {
        if (cookie == null)
        {
            return null;
        }
        return (HttpCookie) cookie.clone();
    }

    /**
     * Gibt den Code des Anfrageergebnisses zurück
     */
    public int getResponseCode()
    {
        return responseCode;
    }

    /**
     * Gibt die Nachricht des Anfrageergebnisses zurück
     */
    public String getResponseText()
    {
        return responseText;
    }

    /**
     * Gibt den OK-Status des Anfrageergebnisses zurück
     */
    public boolean isOk()
    {
        return ok;
    }

}
