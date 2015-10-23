package com.ecg.webclient.feature.administration.service.remoteauthentication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Diese Klasse dient dem Aufbau von Http-Verbindungen. Sie wird für die Anmeldung von Nutzern an
 * Fremdsystemen benötigt.
 */
public class HttpClient
{
    private static final Logger            logger            = LoggerFactory.getLogger(HttpClient.class);

    private static final String            REQUEST_METHOD    = "POST";
    private static final String            COOKIE_FIELD_NAME = "Set-Cookie";

    private final HttpUrlConnectionCreator connectionCreator;
    private final CookieManager            cookieManager;

    /**
     * Erzeugt eine neue Instanz zum Aufbau einer Http-Verbindung zu einem Fremdsystem. Hierfür wird intern
     * der {@link HttpUrlConnectionCreator} sowie der {@link ThreadSafeCookieManager} genutzt.
     */
    public HttpClient()
    {
        this(new HttpUrlConnectionCreator()
        {

            @Override
            public HttpURLConnection createConnection(final String url) throws IOException
            {
                return (HttpURLConnection) new URL(url).openConnection();
            }

        }, new ThreadSafeCookieManager());
    }

    /**
     * Erzeugt eine neue Instanz zum Aufbau einer Http-Verbindung zu einem Fremdsystem. Hierfür wird intern
     * der {@link ThreadSafeCookieManager} genutzt.
     */
    public HttpClient(final HttpUrlConnectionCreator connectionCreator)
    {
        this(connectionCreator, new ThreadSafeCookieManager());
    }

    /**
     * Erzeugt eine neue Instanz zum Aufbau einer Http-Verbindung zu einem Fremdsystem.
     */
    public HttpClient(final HttpUrlConnectionCreator connectionCreator, final CookieManager cookieManager)
    {
        this.connectionCreator = Objects.requireNonNull(connectionCreator);
        this.cookieManager = Objects.requireNonNull(cookieManager);

        CookieHandler.setDefault(this.cookieManager);
    }

    /**
     * Führt ein Http Request aus und sendet einen Cookie mit
     */
    public RequestResult executeRequestFor(final String url, final HttpCookie httpCookie)
    {
        return executePostRequest(url, "", Optional.of(httpCookie));
    }

    /**
     * Sendet ein POST-Request an die angegebene URL liefert bei Erfolg einen Cookie.
     */
    public RequestResult requestCookie(final String url, final String content)
    {
        return executePostRequest(url, content, Optional.empty());
    }

    private HttpURLConnection createConnection(final String url) throws MalformedURLException, IOException
    {
        return connectionCreator.createConnection(url);
    }

    private RequestResult executePostRequest(final String url, final String content,
            final Optional<HttpCookie> optHttpCookie)
    {
        HttpCookie cookie = null;

        int responseCode = -1;

        boolean requestOK = false;
        String responseMessage = null;

        HttpURLConnection httpConnection = null;
        try
        {
            httpConnection = createConnection(url);
            responseCode = sendPostRequest(httpConnection, content, optHttpCookie);
            cookie = extractSessionCookie(cookieManager.getCookieStore());

            final String responseString = getResponseContent(httpConnection);
            requestOK = isRequestOk(responseCode, responseString);

            doLogging(url, cookie, responseCode, responseString);
        }
        catch (final Exception e)
        {
            logger.error("session post request failed", e);
            responseMessage = e.getMessage();
        }
        finally
        {
            if (httpConnection != null)
            {
                httpConnection.disconnect();
            }
        }

        return new RequestResult(responseCode, responseMessage, cookie, requestOK);
    }

    private static void doLogging(final String url, final HttpCookie cookie, final int responseCode,
            final String responseString)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("\nSending 'POST' request to URL : " + url);
            if (cookie != null)
            {
                logger.debug("\ncookie : " + cookie.toString());
            }
            logger.debug("\nResponse Code : " + responseCode);
            logger.debug("Response : " + responseString);
        }
    }

    private static HttpCookie extractSessionCookie(final CookieStore cookieStore)
    {
        HttpCookie cookie = null;
        final List<HttpCookie> cookies = cookieStore.getCookies();

        if (cookies.size() == 1)
        {
            cookie = cookies.get(0);
            cookieStore.removeAll();
        }
        else if (cookies.size() > 1)
        {
            logger.error("cookie size > 1");
        }

        return cookie;
    }

    private static String getResponseContent(final HttpURLConnection con) throws IOException
    {
        final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        final StringBuffer responseString = new StringBuffer();

        String inputLine;
        while ((inputLine = in.readLine()) != null)
        {
            responseString.append(inputLine);
        }
        in.close();
        return responseString.toString();
    }

    private static boolean isRequestOk(final int responseCode, final String responseString)
    {
        if (responseCode != 200 || responseString.toLowerCase().contains("?failure=true"))
        {
            return false;
        }
        return true;
    }

    private static int sendPostRequest(final HttpURLConnection connection, final String content,
            final Optional<HttpCookie> optHttpCookie) throws IOException
    {
        if (optHttpCookie.isPresent())
        {
            connection.addRequestProperty(COOKIE_FIELD_NAME, optHttpCookie.get().toString());
        }
        connection.setRequestMethod(REQUEST_METHOD);
        connection.setDoOutput(true);
        final DataOutputStream request = new DataOutputStream(connection.getOutputStream());
        request.writeBytes(content);
        request.flush();
        request.close();

        return connection.getResponseCode();
    }

    /**
     * Ermöglicht das Mocken der HTTP Verbindung
     */
    interface HttpUrlConnectionCreator
    {
        /**
         * Erzeugt eine {@link HttpURLConnection} für die übergebene URL
         */
        public HttpURLConnection createConnection(String url) throws IOException;
    }
}
