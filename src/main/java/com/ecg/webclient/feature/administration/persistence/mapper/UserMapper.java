package com.ecg.webclient.feature.administration.persistence.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.common.authentication.PasswordEncoder;
import com.ecg.webclient.feature.administration.persistence.modell.Group;
import com.ecg.webclient.feature.administration.persistence.modell.User;
import com.ecg.webclient.feature.administration.persistence.repo.ClientRepository;
import com.ecg.webclient.feature.administration.persistence.repo.GroupRepository;
import com.ecg.webclient.feature.administration.viewmodell.UserDto;

/**
 * Mapped die Eigenschaften einer in OrientDb bekannten Entität auf einen
 * detachten Benutzer oder umgekehrt.
 * 
 * @author arndtmar
 */
public class UserMapper
{
	/**
	 * Wandelt einen persistenten Benutzer in einen detachten um
	 * 
	 * @param user
	 *            persistenter Benutzer
	 * @return Detacheter Benutzer
	 */
	public static UserDto mapToDto(User user)
	{
		UserDto dto = new UserDto();
		dto.setLogin(user.getLogin());
		dto.setInternal(user.isInternal());
		dto.setLastname(user.getLastname());
		dto.setFirstname(user.getFirstname());
		dto.setEnabled(user.isEnabled());
		dto.setDelete(false);
		dto.setId(user.getId());
		dto.setEmail(user.getEmail());
		dto.setChangePasswordOnNextLogin(user.isChangePasswordOnNextLogin());

		if (user.getDefaultClient() != null)
		{
			dto.setDefaultClient(Long.toString(user.getDefaultClient().getId()));
		}

		if (user.getGroups() != null)
		{
			String groups = "";
			for (Group group : user.getGroups())
			{
				if (groups.length() == 0)
				{
					groups = Long.toString(group.getId());
				}
				else
				{
					groups = groups + "," + group.getId();
				}
			}
			dto.setGroupIds(groups);
		}

		return dto;
	}

	/**
	 * Wandelt eine Liste von persistenten Benutzern in eine Liste von detachten
	 * Benutzern um
	 * 
	 * @param users
	 *            Liste von persistenten Benutzern
	 * @return Liste von detachten Benutzern
	 */
	public static List<UserDto> mapToDtos(List<User> users)
	{
		List<UserDto> result = new AutoPopulatingList<UserDto>(UserDto.class);

		for (User user : users)
		{
			UserDto dto = new UserDto();
			dto.setLogin(user.getLogin());
			dto.setInternal(user.isInternal());
			dto.setLastname(user.getLastname());
			dto.setFirstname(user.getFirstname());
			dto.setEnabled(user.isEnabled());
			dto.setDelete(false);
			dto.setId(user.getId());
			dto.setEmail(user.getEmail());
			dto.setChangePasswordOnNextLogin(user.isChangePasswordOnNextLogin());

			if (user.getDefaultClient() != null)
			{
				dto.setDefaultClient(Long.toString(user.getDefaultClient().getId()));
			}

			if (user.getGroups() != null)
			{
				String groups = "";
				for (Group group : user.getGroups())
				{
					if (groups.length() == 0)
					{
						groups = Long.toString(group.getId());
					}
					else
					{
						groups = groups + "," + group.getId();
					}
				}
				dto.setGroupIds(groups);
			}

			result.add(dto);
		}

		return result;
	}

	/**
	 * Wandelt einen detachten Benutzer in einen persistenten um
	 * 
	 * @param dto
	 *            Detachter Benutzer
	 * @return Persistenter Benutzer
	 */
	public static User mapToEntity(UserDto dto, boolean setInitialPassword, ClientRepository clientRepo,
			GroupRepository groupRepo)
	{
		User entity = new User();
		entity.setLogin(dto.getLogin());
		entity.setInternal(dto.isInternal());
		entity.setLastname(dto.getLastname());
		entity.setFirstname(dto.getFirstname());
		entity.setEnabled(dto.isEnabled());
		entity.setEmail(dto.getEmail());

		if (setInitialPassword)
		{
			String pw = dto.getPassword();
			if (pw == null || pw.isEmpty())
			{
				pw = PasswordEncoder.encodeSimple("NewUser123?");
			}
			entity.setPassword(PasswordEncoder.encodeComplex(pw, Long.toString(dto.getId())));
		}

		entity.setChangePasswordOnNextLogin(dto.isChangePasswordOnNextLogin());
		entity.setDefaultClient(clientRepo.findOne(Long.parseLong(dto.getDefaultClient())));

		List<Group> groups = new ArrayList<Group>();
		groupRepo.findAll(dto.getGroupIdObjects()).forEach(e -> groups.add(e));

		entity.setGroups(groups);

		return entity;
	}
}
