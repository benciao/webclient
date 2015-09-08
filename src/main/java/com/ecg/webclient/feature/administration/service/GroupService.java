package com.ecg.webclient.feature.administration.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.common.authentication.AuthenticationUtil;
import com.ecg.webclient.feature.administration.persistence.mapper.ClientMapper;
import com.ecg.webclient.feature.administration.persistence.mapper.GroupMapper;
import com.ecg.webclient.feature.administration.persistence.modell.Group;
import com.ecg.webclient.feature.administration.persistence.repo.GroupRepository;
import com.ecg.webclient.feature.administration.persistence.repo.RoleRepository;
import com.ecg.webclient.feature.administration.viewmodell.ClientDto;
import com.ecg.webclient.feature.administration.viewmodell.GroupDto;

/**
 * Service zum Bearbeiten von Gruppen.
 * 
 * @author arndtmar
 */
@Component
public class GroupService
{
	static final Logger logger = LogManager.getLogger(GroupService.class.getName());

	AuthenticationUtil	authenticationUtil;
	GroupRepository		groupRepo;
	RoleRepository		roleRepo;

	@Autowired
	public GroupService(AuthenticationUtil authenticationUtil, GroupRepository groupRepo, RoleRepository roleRepo)
	{
		this.authenticationUtil = authenticationUtil;
		this.groupRepo = groupRepo;
		this.roleRepo = roleRepo;
	}

	/**
	 * Löscht die in der Liste enthaltenen Gruppen.
	 * 
	 * @param detachedGroups
	 *            Liste von zu löschenden Gruppen
	 */
	public void deleteGroups(List<GroupDto> detachedGroups)
	{
		try
		{
			for (GroupDto group : detachedGroups)
			{
				Group persistentGroup = groupRepo.findOne(group.getId());

				if (persistentGroup != null)
				{
					groupRepo.delete(persistentGroup);
				}
			}
		}
		catch (final Exception e)
		{
			logger.error(e);
		}
	}

	/**
	 * @param onlyEnabled
	 *            true, wenn nur die aktiven Gruppen geladen werden sollen,
	 *            sonst false
	 * @return Alle Gruppen, wenn false, sonst nur die aktivierten Gruppen
	 */
	public List<GroupDto> getAllGroups(boolean onlyEnabledGroups)
	{
		List<Group> attachedGroups = new ArrayList<Group>();

		try
		{
			if (!onlyEnabledGroups)
			{
				groupRepo.findAll().forEach(e -> attachedGroups.add(e));
			}
			else
			{
				groupRepo.findAllEnabledGroups(true).forEach(e -> attachedGroups.add(e));
			}
		}
		catch (final Exception e)
		{
			logger.error(e);
		}

		AutoPopulatingList<GroupDto> result = new AutoPopulatingList<GroupDto>(GroupDto.class);
		for (Group attachedGroup : attachedGroups)
		{
			result.add(GroupMapper.mapToDto(attachedGroup));
		}

		return result;
	}

	/**
	 * @param clientId
	 *            Mandanten-ID
	 * @return Alle zum Mandanten gehörende Gruppen
	 */
	public List<GroupDto> getAllGroupsForClient(Long clientId)
	{
		List<Group> attachedGroups = new ArrayList<Group>();

		try
		{
			groupRepo.findAllGroupsAssignedToClientId(clientId).forEach(e -> attachedGroups.add(e));
		}
		catch (final Exception e)
		{
			logger.error(e);
		}

		AutoPopulatingList<GroupDto> result = new AutoPopulatingList<GroupDto>(GroupDto.class);
		for (Group attachedGroup : attachedGroups)
		{
			result.add(GroupMapper.mapToDto(attachedGroup));
		}

		return result;
	}

	/**
	 * @param groupId
	 *            Gruppen-ID
	 * @return Der der Gruppe zugeordnete Mandant
	 */
	public ClientDto getClientForGroupId(Long groupId)
	{
		try
		{
			Group persistentGroup = groupRepo.findOne(groupId);
			if (persistentGroup != null)
			{
				return ClientMapper.mapToDto(persistentGroup.getClient());
			}
		}
		catch (final Exception e)
		{
			logger.error(e);
		}

		return null;
	}

	/**
	 * @param name
	 *            Gruppenname
	 * @return Die zum Namen gehörende Gruppe
	 */
	public GroupDto getGroupByName(String name)
	{
		try
		{
			Group persistentGroup = groupRepo.findGroupByName(name);
			return (persistentGroup != null) ? GroupMapper.mapToDto(persistentGroup) : null;
		}
		catch (final Exception e)
		{
			logger.error(e);
		}

		return null;
	}

	/**
	 * @param groupIds
	 *            Liste von Gruppen-IDs
	 * @return Liste von Gruppen
	 */
	public List<GroupDto> getGroupsForIds(List<Long> groupIds)
	{
		List<GroupDto> result = new ArrayList<GroupDto>();

		try
		{
			Iterable<Group> persistentGroups = groupRepo.findAll(groupIds);
			for (Group persistentGroup : persistentGroups)
			{
				result.add(GroupMapper.mapToDto(persistentGroup));
			}

		}
		catch (final Exception e)
		{
			logger.error(e);
		}

		return result;
	}

	/**
	 * Speichert eine Gruppe.
	 * 
	 * @param detachedGroup
	 *            Zu speichernde Gruppe
	 * @return Gespeicherte Gruppe
	 */
	public GroupDto saveGroup(GroupDto detachedGroup)
	{
		try
		{
			Group attachedGroup = GroupMapper.mapToEntity(detachedGroup, roleRepo,
					ClientMapper.mapToEntity(authenticationUtil.getSelectedClient()));
			Group persistentGroup = groupRepo.findOne(attachedGroup.getId());

			if (persistentGroup != null)
			{
				persistentGroup.bind(attachedGroup);
				persistentGroup = groupRepo.save(persistentGroup);
			}
			else
			{
				persistentGroup = groupRepo.save(attachedGroup);
			}

			return GroupMapper.mapToDto(persistentGroup);
		}
		catch (final Exception e)
		{
			logger.error(e);
		}

		return null;
	}

	/**
	 * Speichert eine Liste von Gruppen.
	 * 
	 * @param detachedGroups
	 *            Liste von zu speichernden Gruppen
	 */
	public void saveGroups(List<GroupDto> detachedGroups)
	{
		try
		{
			for (GroupDto detachedGroup : detachedGroups)
			{
				Group attachedGroup = GroupMapper.mapToEntity(detachedGroup, roleRepo,
						ClientMapper.mapToEntity(authenticationUtil.getSelectedClient()));
				Group persistentGroup = groupRepo.findOne(detachedGroup.getId());

				if (persistentGroup != null)
				{
					persistentGroup.bind(attachedGroup);
					persistentGroup = groupRepo.save(persistentGroup);
				}
				else
				{
					persistentGroup = groupRepo.save(attachedGroup);
				}
			}
		}
		catch (final Exception e)
		{
			logger.error(e);
		}
	}
}
