package com.ecg.webclient.feature.administration.persistence.odbmodell;

import java.util.ArrayList;
import java.util.List;

import com.ecg.webclient.feature.administration.persistence.api.IClient;
import com.ecg.webclient.feature.administration.persistence.api.IProperty;

/**
 * Implementierung eines Mandanten. OrientDb spezifisch.
 * 
 * @author arndtmar
 */
public class OdbClient extends OdbBaseObject implements IClient
{
    private String          color;
    private String          description;
    private String          name;
    private boolean         enabled;
    private List<IProperty> properties;

    public OdbClient()
    {}

    @Override
    public void bind(IClient newClient)
    {
        setColor(newClient.getColor());
        setName(newClient.getName());
        setDescription(newClient.getDescription());
        setEnabled(newClient.isEnabled());
        setProperties(newClient.getProperties());
    }

    @Override
    public String getColor()
    {
        return color;
    }

    @Override
    public String getDescription()
    {
        return description;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public List<IProperty> getProperties()
    {
        if (properties == null)
        {
            properties = new ArrayList<IProperty>();
        }
        return properties;
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }

    @Override
    public void setColor(String color)
    {
        this.color = color;
    }

    @Override
    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public void setProperties(List<IProperty> properties)
    {
        this.properties = properties;
    }

    @Override
    public String toString()
    {
        return this.getRid().toString();
    }

}
