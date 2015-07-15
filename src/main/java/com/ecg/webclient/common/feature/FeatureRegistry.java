package com.ecg.webclient.common.feature;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecg.webclient.common.Util;

@Component
public class FeatureRegistry
{
    private List<IFeature> registeredFeatures;
    @Autowired
    private Util           util;

    @Autowired
    FeatureRegistry(List<IFeature> features)
    {
        this.registeredFeatures = features;
    }

    public List<IFeature> getRegisteredFeatures()
    {
        return registeredFeatures;
    }

    public void resetActiveFeature()
    {
        registeredFeatures.forEach(value -> value.setActive(false));
    }

    public void updateActiveFeature(IFeature feature, Boolean isMinimized)
    {
        registeredFeatures.forEach(value -> value.setActive(value.getId().equalsIgnoreCase(feature.getId())));
        util.setMenuMinimized(isMinimized);
    }
}
