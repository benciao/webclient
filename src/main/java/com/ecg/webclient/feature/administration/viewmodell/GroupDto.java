package com.ecg.webclient.feature.administration.viewmodell;

import javax.validation.constraints.NotNull;

public class GroupDto extends BaseObjectDto
{
    @NotNull
    private String  name;
    @NotNull
    private String  description;
    private boolean enabled;
    private String  roleRids;

    public GroupDto()
    {}

    public boolean equals(GroupDto otherDto)
    {
        return otherDto.getRid().toString().equalsIgnoreCase(this.getRid().toString()) ? true : false;
    }

    public String getDescription()
    {
        return description;
    }

    public String getName()
    {
        return name;
    }

    public String getRoleRids()
    {
        return roleRids;
    }

    public boolean isEnabled()
    {
        return enabled;
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

    public void setRoleRids(String roleRids)
    {
        this.roleRids = roleRids;
    }
}
