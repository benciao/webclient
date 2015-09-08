package com.ecg.webclient.feature.nomination;

import org.springframework.stereotype.Controller;

import com.ecg.webclient.common.feature.Feature;

@Controller
public class FeatureNomination implements Feature
{
    private boolean isActive;

    @Override
    public boolean equals(Feature other)
    {
        return other.getText().equalsIgnoreCase(getText());
    }

    @Override
    public String getIconPath()
    {
        return "/icons/nomination_24_white.png";
    }

    @Override
    public String getId()
    {
        return "nom-feature";
    }

    @Override
    public String getLink()
    {
        return "/nom";
    }

    @Override
    public String getText()
    {
        return "nomination";
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
