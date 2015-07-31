package com.ecg.webclient.feature.administration.viewmodell;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.api.IRoleDto;

public class RoleConfig
{
    @Valid
    private List<IRoleDto> roles;

    public List<IRoleDto> getRoles()
    {
        if (roles == null)
        {
            roles = new AutoPopulatingList<IRoleDto>(IRoleDto.class);
        }
        return roles;
    }

    public void removeDeleted()
    {
        List<IRoleDto> rolesToRemove = new ArrayList<IRoleDto>();
        for (IRoleDto role : roles)
        {
            if (role.isDelete())
            {
                rolesToRemove.add(role);
            }
        }
        roles.removeAll(rolesToRemove);
    }

    public void setRoles(List<IRoleDto> roles)
    {
        this.roles = roles;
    }
}
