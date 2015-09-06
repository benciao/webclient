package com.ecg.webclient.feature.administration.viewmodell;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.api.IGroupDto;
import com.ecg.webclient.feature.administration.persistence.api.IRoleDto;

public class GroupConfig
{
    @Valid
    private List<IGroupDto> groups;
    private List<IRoleDto>  roles;

    public List<IGroupDto> getGroups()
    {
        if (groups == null)
        {
            groups = new AutoPopulatingList<IGroupDto>(IGroupDto.class);
        }
        return groups;
    }

    public List<IRoleDto> getRoles()
    {
        if (roles == null)
        {
            roles = new ArrayList<IRoleDto>();
        }
        return roles;
    }

    public void removeDeleted()
    {
        List<IGroupDto> groupsToRemove = new ArrayList<IGroupDto>();
        for (IGroupDto group : groups)
        {
            if (group.isDelete())
            {
                groupsToRemove.add(group);
            }
        }
        groups.removeAll(groupsToRemove);
    }

    public void setGroups(List<IGroupDto> groups)
    {
        this.groups = groups;
    }

    public void setRoles(List<IRoleDto> roles)
    {
        this.roles = roles;
    }
}
