package com.ecg.webclient.common.feature;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecg.webclient.common.authentication.AuthenticationUtil;

@Component
public class FeatureRegistry
{
    private List<Feature> registeredFeatures;
    @Autowired
    private AuthenticationUtil           util;

    @Autowired
    FeatureRegistry(List<Feature> features)
    {
        this.registeredFeatures = features;
    }

    public List<Feature> getRegisteredFeatures()
    {
        return registeredFeatures;
    }

    public void resetActiveFeature()
    {
        registeredFeatures.forEach(value -> value.setActive(false));
    }

    public void updateActiveFeature(Feature feature, Boolean isMinimized)
    {
        registeredFeatures.forEach(value -> value.setActive(value.getId().equalsIgnoreCase(feature.getId())));
        util.setMenuMinimized(isMinimized);
    }
}
