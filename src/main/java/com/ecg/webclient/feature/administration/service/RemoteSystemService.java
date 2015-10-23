package com.ecg.webclient.feature.administration.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.mapper.RemoteSystemMapper;
import com.ecg.webclient.feature.administration.persistence.modell.RemoteSystem;
import com.ecg.webclient.feature.administration.persistence.repo.RemoteSystemRepository;
import com.ecg.webclient.feature.administration.viewmodell.RemoteLoginDto;
import com.ecg.webclient.feature.administration.viewmodell.RemoteSystemDto;

/**
 * Service zum Bearbeiten von Fremdsystemen.
 * 
 * @author arndtmar
 */
@Component
public class RemoteSystemService
{
    static final Logger            logger = LogManager.getLogger(RemoteSystemService.class.getName());
    private RemoteSystemRepository remoteSystemRepo;
    private RemoteLoginService     remoteLoginService;
    private RemoteSystemMapper     remoteSystemMapper;

    @Autowired
    public RemoteSystemService(RemoteSystemRepository remoteSystemRepo,
            RemoteSystemMapper remoteSystemMapper, RemoteLoginService remoteLoginService)
    {
        this.remoteSystemRepo = remoteSystemRepo;
        this.remoteSystemMapper = remoteSystemMapper;
        this.remoteLoginService = remoteLoginService;
    }

    /**
     * Löscht die in der Liste enthaltenen Fremdsysteme.
     * 
     * @param detachedRemoteSystems
     *            Liste mit zu löschenden Fremdsystemen
     */
    public void deleteRemoteSystems(List<RemoteSystemDto> detachedRemoteSystems)
    {
        try
        {
            for (RemoteSystemDto detachedRemoteSystem : detachedRemoteSystems)
            {
                RemoteSystem persistentRemoteSystem = remoteSystemRepo.findOne(detachedRemoteSystem.getId());

                if (persistentRemoteSystem != null)
                {
                    remoteLoginService.deleteRemoteLoginsForRemoteSystemId(persistentRemoteSystem.getId());
                    remoteSystemRepo.delete(persistentRemoteSystem);
                }
            }
        }
        catch (final Exception e)
        {
            logger.error(e);
        }
    }

    /**
     * @param onlyEnabledRemoteSystems
     *            true, wenn nur die aktiven Fremdsysteme geladen werden sollen, sonst false
     * @return Alle Fremdsysteme, wenn false, sonst nur die aktivierten Fremdsysteme
     */
    public List<RemoteSystemDto> getAllRemoteSystems(boolean onlyEnabledRemoteSystems)
    {
        List<RemoteSystem> attachedRemoteSystems = new ArrayList<RemoteSystem>();

        try
        {
            if (!onlyEnabledRemoteSystems)
            {
                remoteSystemRepo.findAll().forEach(e -> attachedRemoteSystems.add(e));
            }
            else
            {
                remoteSystemRepo.findAllEnabledRemoteSystems().forEach(e -> attachedRemoteSystems.add(e));
            }
        }
        catch (final Exception e)
        {
            logger.error(e);
        }

        AutoPopulatingList<RemoteSystemDto> result = new AutoPopulatingList<RemoteSystemDto>(
                RemoteSystemDto.class);

        for (RemoteSystem attachedRemoteSystem : attachedRemoteSystems)
        {
            result.add(remoteSystemMapper.mapToDto(attachedRemoteSystem));
        }

        return result;
    }

    /**
     * @param id
     *            Id des Fremdsystems
     * @return Das Fremdsystem, welches zur übergebenen Id gehört
     */
    public RemoteSystemDto getRemoteSystemById(Long id)
    {
        try
        {
            RemoteSystem persistentRemoteSystem = remoteSystemRepo.findOne(id);

            if (persistentRemoteSystem != null)
            {
                RemoteSystemDto result = remoteSystemMapper.mapToDto(persistentRemoteSystem);

                return result;
            }

            return null;
        }
        catch (final Exception e)
        {
            logger.error(e);
        }

        return null;
    }

    /**
     * @param name
     *            Name des Fremdsystems
     * @return Das Fremdsystem, welches zum übergebenen Namen gehört
     */
    public RemoteSystemDto getRemoteSystemByName(String name)
    {
        try
        {
            RemoteSystem persistentRemoteSystem = null;

            Iterable<RemoteSystem> remoteSystems = remoteSystemRepo.findByName(name);
            if (remoteSystems.iterator().hasNext())
            {
                persistentRemoteSystem = remoteSystems.iterator().next();
            }

            if (persistentRemoteSystem != null)
            {
                RemoteSystemDto result = remoteSystemMapper.mapToDto(persistentRemoteSystem);

                return result;
            }

            return null;
        }
        catch (final Exception e)
        {
            logger.error(e);
        }

        return null;
    }

    /**
     * @param remoteSystemIds
     *            Liste mit IDs von Fremdsystemen
     * @return Liste mit zu den IDs gehörenden Fremdsystemen
     */
    public List<RemoteSystemDto> getRemoteSystemsForIds(List<Long> remoteSystemIds)
    {
        List<RemoteSystemDto> result = new ArrayList<RemoteSystemDto>();

        try
        {
            Iterable<RemoteSystem> persistentRemoteSystems = remoteSystemRepo.findAll(remoteSystemIds);
            for (RemoteSystem persistentRemoteSystem : persistentRemoteSystems)
            {
                result.add(remoteSystemMapper.mapToDto(persistentRemoteSystem));
            }
        }
        catch (final Exception e)
        {
            logger.error(e);
        }

        return result;
    }

    /**
     * Speichert das zu übergebende Fremdsystem.
     * 
     * @param detachedRemoteSystem
     *            das zu speichernde Fremdsystem
     * @return Das gespeicherte Fremdsystem
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public RemoteSystemDto saveRemoteSystem(RemoteSystemDto detachedRemoteSystem)
    {
        try
        {
            RemoteSystem draftRemoteSystem = remoteSystemMapper.mapToEntity(detachedRemoteSystem);

            RemoteSystem persistedRemoteSystem = remoteSystemRepo.save(draftRemoteSystem);

            if (persistedRemoteSystem != null)
            {
                remoteLoginService.deleteRemoteLoginsForRemoteSystemId(persistedRemoteSystem.getId());

                for (Long userId : detachedRemoteSystem.getAssignedUserIdObjects())
                {
                    RemoteLoginDto rl = new RemoteLoginDto();
                    rl.setEnabled(false);
                    rl.setUserId(userId.toString());
                    rl.setRemoteSystemId(Long.toString(persistedRemoteSystem.getId()));
                    remoteLoginService.save(rl);
                }

                RemoteSystemDto result = remoteSystemMapper.mapToDto(persistedRemoteSystem);

                return result;
            }
            else
            {
                return null;
            }
        }
        catch (final Exception e)
        {
            logger.error(e);
        }

        return null;
    }

    /**
     * Speichert die in der Liste enthaltenen Fremdsysteme.
     * 
     * @param detachedRemoteSystems
     *            Liste mit zu speichernden Fremdsystemen
     */
    public void saveRemoteSystems(List<RemoteSystemDto> detachedRemoteSystems)
    {
        try
        {
            detachedRemoteSystems.forEach(e -> saveRemoteSystem(e));
        }
        catch (final Exception e)
        {
            logger.error(e);
        }
    }
}
