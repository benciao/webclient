package com.ecg.webclient.feature.administration.viewmodell;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.api.IUserDto;

public class UserConfig
{
    @Valid
    private List<IUserDto> users;

    public List<IUserDto> getUsers()
    {
        if (users == null)
        {
            users = new AutoPopulatingList<IUserDto>(IUserDto.class);
        }
        return users;
    }

    public void removeDeleted()
    {
        List<IUserDto> usersToRemove = new ArrayList<IUserDto>();
        for (IUserDto user : users)
        {
            if (user.isDelete())
            {
                usersToRemove.add(user);
            }
        }
        users.removeAll(usersToRemove);
    }

    public void setUsers(List<IUserDto> users)
    {
        this.users = users;
    }
}
