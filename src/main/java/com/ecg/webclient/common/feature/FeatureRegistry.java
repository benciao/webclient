package com.ecg.webclient.common.feature;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeatureRegistry
{
    private List<Feature> registeredFeatures;

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

    public void updateActiveFeature(Feature feature)
    {
        registeredFeatures.forEach(value -> value.setActive(value.getId().equalsIgnoreCase(feature.getId())));
    }
}
