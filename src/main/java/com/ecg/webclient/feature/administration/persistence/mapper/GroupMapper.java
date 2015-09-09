package com.ecg.webclient.feature.administration.persistence.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.modell.Client;
import com.ecg.webclient.feature.administration.persistence.modell.Group;
import com.ecg.webclient.feature.administration.persistence.modell.Role;
import com.ecg.webclient.feature.administration.persistence.repo.RoleRepository;
import com.ecg.webclient.feature.administration.viewmodell.GroupDto;

/**
 * Mapped die Eigenschaften einer in OrientDb bekannten Entität auf eine
 * detachted Gruppe oder umgekehrt.
 * 
 * @author arndtmar
 */
public class GroupMapper
{
	/**
	 * Wandelt eine persistente Gruppe in eine detachte um
	 * 
	 * @param group
	 *            persistente Gruppe
	 * @return Detachete Gruppe
	 */
	public static GroupDto mapToDto(Group group)
	{
		GroupDto dto = new GroupDto();
		dto.setDescription(group.getDescription());
		dto.setName(group.getName());
		dto.setEnabled(group.isEnabled());
		dto.setDelete(false);
		dto.setId(group.getId());

		if (group.getRoles() != null)
		{
			String roles = "";
			for (Role role : group.getRoles())
			{
				if (roles.length() == 0)
				{
					roles = Long.toString(role.getId());
				}
				else
				{
					roles = roles + "," + role.getId();
				}
			}
			dto.setRoleIds(roles);
		}

		return dto;
	}

	/**
	 * Wandelt eine Liste von persistenten Gruppen in eine Liste von detachten
	 * Gruppen um
	 * 
	 * @param groups
	 *            Liste von Persistenten Gruppen
	 * @return Liste von detachten Gruppen
	 */
	public static List<GroupDto> mapToDtos(List<Group> groups)
	{
		List<GroupDto> result = new AutoPopulatingList<GroupDto>(GroupDto.class);

		for (Group group : groups)
		{
			GroupDto dto = new GroupDto();
			dto.setDescription(group.getDescription());
			dto.setName(group.getName());
			dto.setEnabled(group.isEnabled());
			dto.setDelete(false);
			dto.setId(group.getId());

			if (group.getRoles() != null)
			{
				String roles = "";
				for (Role role : group.getRoles())
				{
					if (roles.length() == 0)
					{
						roles = Long.toString(role.getId());
					}
					else
					{
						roles = roles + "," + role.getId();
					}
				}
				dto.setRoleIds(roles);
			}

			result.add(dto);
		}

		return result;
	}

	/**
	 * Wandelt eine Liste von detachten Gruppen in eine Liste von persistenten
	 * Gruppen um
	 * 
	 * @param dtos
	 *            Liste von detachten Gruppen
	 * @return Liste von persistenten Gruppen
	 */
	public static List<Group> mapToEntities(List<GroupDto> dtos, RoleRepository roleRepo, Client client)
	{
		List<Group> result = new ArrayList<Group>();

		for (GroupDto dto : dtos)
		{
			Group entity = new Group();
			entity.setDescription(dto.getDescription());
			entity.setName(dto.getName());
			entity.setEnabled(dto.isEnabled());
			entity.setClient(client);

			List<Role> roles = new ArrayList<Role>();
			roleRepo.findAll(dto.getRoleIdObjects()).forEach(e -> roles.add(e));

			entity.setRoles(roles);

			result.add(entity);
		}

		return result;
	}

	/**
	 * Wandelt eine detachte Gruppen in eine persistente um
	 * 
	 * @param dto
	 *            Detachte Gruppen
	 * @return Persistente Gruppen
	 */
	public static Group mapToEntity(GroupDto dto, RoleRepository roleRepo, Client client)
	{
		Group entity = new Group();
		entity.setDescription(dto.getDescription());
		entity.setName(dto.getName());
		entity.setEnabled(dto.isEnabled());
		entity.setClient(client);

		List<Role> roles = new ArrayList<Role>();
		roleRepo.findAll(dto.getRoleIdObjects()).forEach(e -> roles.add(e));

		entity.setRoles(roles);

		return entity;
	}
}
