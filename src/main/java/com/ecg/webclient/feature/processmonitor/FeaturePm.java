package com.ecg.webclient.feature.processmonitor;

import org.springframework.stereotype.Component;

import com.ecg.webclient.common.feature.IFeature;

/**
 * Dieses Feature beschreibt das des Proyess Monitors.
 * 
 * @author arndtmar
 *
 */
@Component
public class FeaturePm implements IFeature
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
        return "/icons/processmonitor_24_white.png";
    }

    @Override
    public String getId()
    {
        return "pm-feature";
    }

    @Override
    public String getLink()
    {
        return "/pm";
    }

    @Override
    public String getText()
    {
        return "process_monitor";
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
