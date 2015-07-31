package com.ecg.webclient.feature.administration.viewmodell;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.api.IPropertyDto;

public class ClientProperties
{
    @Valid
    private List<IPropertyDto> properties;

    public List<IPropertyDto> getProperties()
    {
        if (properties == null)
        {
            properties = new AutoPopulatingList<IPropertyDto>(IPropertyDto.class);
        }
        return properties;
    }

    public void removeDeleted()
    {
        List<IPropertyDto> propertiesToRemove = new ArrayList<IPropertyDto>();
        for (IPropertyDto property : properties)
        {
            if (property.isDelete())
            {
                propertiesToRemove.add(property);
            }
        }
        properties.removeAll(propertiesToRemove);
    }

    public void setProperties(List<IPropertyDto> properties)
    {
        this.properties = properties;
    }
}
