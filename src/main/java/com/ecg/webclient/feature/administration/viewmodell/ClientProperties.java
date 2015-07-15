package com.ecg.webclient.feature.administration.viewmodell;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.util.AutoPopulatingList;

public class ClientProperties
{
    @Valid
    private List<PropertyDto> properties;

    public List<PropertyDto> getProperties()
    {
        if (properties == null)
        {
            properties = new AutoPopulatingList<PropertyDto>(PropertyDto.class);
        }
        return properties;
    }

    public void removeDeleted()
    {
        List<PropertyDto> propertiesToRemove = new ArrayList<PropertyDto>();
        for (PropertyDto property : properties)
        {
            if (property.getDelete())
            {
                propertiesToRemove.add(property);
            }
        }
        properties.removeAll(propertiesToRemove);
    }

    public void setProperties(List<PropertyDto> properties)
    {
        this.properties = properties;
    }
}
