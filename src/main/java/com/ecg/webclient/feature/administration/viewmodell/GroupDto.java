package com.ecg.webclient.feature.administration.viewmodell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.ecg.webclient.feature.administration.persistence.api.IGroupDto;

/**
 * Implementierung einer von der Persistenz detachten Benutzergruppe.
 * 
 * @author arndtmar
 */
public class GroupDto extends BaseObjectDto implements IGroupDto
{
    @NotNull
    private String  name;
    @NotNull
    private String  description;
    private boolean enabled;
    private String  roleRids;

    public GroupDto()
    {}

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
    public List<Object> getRoleRidObjects()
    {
        List<Object> result = new ArrayList<Object>();

        if (roleRids != null && !roleRids.isEmpty())
        {
            List<String> rids = Arrays.asList(roleRids.split(","));

            for (String rid : rids)
            {
                result.add("#" + rid);
            }
        }

        return result.size() != 0 ? result : null;
    }

    @Override
    public String getRoleRids()
    {
        return roleRids;
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
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
    public void setRoleRids(String roleRids)
    {
        this.roleRids = roleRids;
    }
}
