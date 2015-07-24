package com.ecg.webclient.feature.administration.persistence.dbmodell;

import java.util.ArrayList;
import java.util.List;

public class Client extends BaseObject
{
    private String         color;
    private String         description;
    private String         name;
    private boolean        enabled;
    private List<Property> properties;

    public Client()
    {}

    @Override
    public boolean equals(Object otherClient)
    {
        return this.getRid().toString().equalsIgnoreCase(((Client) otherClient).getRid().toString());
    }

    public String getColor()
    {
        return color;
    }

    public String getDescription()
    {
        return description;
    }

    public String getName()
    {
        return name;
    }

    public List<Property> getProperties()
    {
        if (properties == null)
        {
            properties = new ArrayList<Property>();
        }
        return properties;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setProperties(List<Property> properties)
    {
        this.properties = properties;
    }

    @Override
    public String toString()
    {
        return this.getRid().toString();
    }

    public void update(Client newClient)
    {
        setColor(newClient.getColor());
        setName(newClient.getName());
        setDescription(newClient.getDescription());
        setEnabled(newClient.isEnabled());
        setProperties(newClient.getProperties());
    }

}
