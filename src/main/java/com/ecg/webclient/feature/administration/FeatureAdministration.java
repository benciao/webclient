package com.ecg.webclient.feature.administration;

import org.springframework.stereotype.Component;

import com.ecg.webclient.common.feature.IFeature;

/**
 * Implementierung des Features Administration.
 * 
 * @author arndtmar
 *
 */
@Component
public class FeatureAdministration implements IFeature
{
    private boolean isActive;

    @Override
    public boolean equals(IFeature other)
    {
        return other.getText().equalsIgnoreCase(getText());
    }

    @Override
    public String getIconPath()
    {
        return "/icons/administration_24_white.png";
    }

    @Override
    public String getId()
    {
        return "administration-feature";
    }

    @Override
    public String getLink()
    {
        return "/admin";
    }

    @Override
    public String getText()
    {
        return "administration";
    }

    @Override
    public boolean isActive()
    {
        return this.isActive;
    }

    @Override
    public void setActive(boolean isActive)
    {
        this.isActive = isActive;
    }

}
