package com.ecg.webclient.feature.administration.viewmodell;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.util.AutoPopulatingList;

public class RemoteSystemConfig
{
    @Valid
    private List<RemoteSystemDto> remoteSystems;

    public List<RemoteSystemDto> getRemoteSystems()
    {
        if (remoteSystems == null)
        {
            remoteSystems = new AutoPopulatingList<RemoteSystemDto>(RemoteSystemDto.class);
        }
        return remoteSystems;
    }

    public void removeDeleted()
    {
        List<RemoteSystemDto> remoteSystemsToRemove = new ArrayList<RemoteSystemDto>();
        for (RemoteSystemDto remoteSystem : remoteSystems)
        {
            if (remoteSystem.isDelete())
            {
                remoteSystemsToRemove.add(remoteSystem);
            }
        }
        remoteSystems.removeAll(remoteSystemsToRemove);
    }

    public void setRemoteSystems(List<RemoteSystemDto> remoteSystems)
    {
        this.remoteSystems = remoteSystems;
    }
}
