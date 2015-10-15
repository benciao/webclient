package com.ecg.webclient.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ecg.webclient.feature.administration.service.EnvironmentService;

@Scope("session")
@Component
public class ApplicationUtil
{
    boolean            isMenuMinimized;
    EnvironmentService environmentService;

    @Autowired
    public ApplicationUtil(EnvironmentService environmentService)
    {
        this.isMenuMinimized = false;
        this.environmentService = environmentService;
    }

    public String getSystemIdentifier()
    {
        String identifier = environmentService.getEnvironment().getSystemIdentifier();
        if (identifier != null && identifier.length() == 0)
        {
            return null;
        }
        else
        {
            return identifier;
        }
    }

    public boolean isMenuMinimized()
    {
        return isMenuMinimized;
    }

    public void setMenuMinimized(boolean isMenuMinimized)
    {
        this.isMenuMinimized = isMenuMinimized;
    }
}
