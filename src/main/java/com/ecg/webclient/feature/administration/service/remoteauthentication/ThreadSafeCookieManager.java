package com.ecg.webclient.feature.administration.service.remoteauthentication;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * @author arndtmar
 *
 */
public class ThreadSafeCookieManager extends CookieManager
{
    private ThreadLocal<CookieManager> cookieManager = new ThreadLocal<CookieManager>();

    @Override
    public Map<String, List<String>> get(URI paramURI, Map<String, List<String>> paramMap) throws IOException
    {
        return getManager().get(paramURI, paramMap);
    }

    @Override
    public CookieStore getCookieStore()
    {
        return getManager().getCookieStore();
    }

    @Override
    public void put(URI paramURI, Map<String, List<String>> paramMap) throws IOException
    {

        getManager().put(paramURI, paramMap);
    }

    private CookieManager getManager()
    {
        CookieManager threadInstance = cookieManager.get();
        if (threadInstance == null)
        {
            threadInstance = new CookieManager();
            cookieManager.set(threadInstance);
        }
        return threadInstance;
    }
}
