package com.ecg.webclient.feature.administration.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.common.authentication.PasswordEncoder;
import com.ecg.webclient.feature.administration.persistence.mapper.ClientMapper;
import com.ecg.webclient.feature.administration.persistence.mapper.UserMapper;
import com.ecg.webclient.feature.administration.persistence.modell.User;
import com.ecg.webclient.feature.administration.persistence.repo.ClientRepository;
import com.ecg.webclient.feature.administration.persistence.repo.GroupRepository;
import com.ecg.webclient.feature.administration.persistence.repo.UserRepository;
import com.ecg.webclient.feature.administration.viewmodell.ClientDto;
import com.ecg.webclient.feature.administration.viewmodell.UserDto;

/**
 * Service zum Bearbeiten von Benutzern.
 * 
 * @author arndtmar
 */
@Component
public class UserService
{
	static final Logger logger = LogManager.getLogger(UserService.class.getName());

	UserRepository		userRepo;
	GroupRepository		groupRepo;
	ClientRepository	clientRepo;

	@Autowired
	public UserService(UserRepository userRepo, GroupRepository groupRepo, ClientRepository clientRepo)
	{
		this.userRepo = userRepo;
		this.groupRepo = groupRepo;
		this.clientRepo = clientRepo;
	}

	/**
	 * Löscht die in der Liste enthaltenen Benutzer.
	 * 
	 * @param detachedUsers
	 *            Liste von zu löschenden Benutzern
	 */
	public void deleteUsers(List<UserDto> detachedUsers)
	{
		try
		{
			for (UserDto user : detachedUsers)
			{
				User persistentUser = userRepo.findOne(user.getId());

				if (persistentUser != null)
				{
					userRepo.delete(persistentUser);
				}
			}
		}
		catch (final Exception e)
		{
			logger.error(e);
		}
	}

	/**
	 * @param onlyEnabledUsers
	 *            true, wenn nur die aktiven Benutzer geladen werden sollen,
	 *            sonst false
	 * @return Alle Benutzer, wenn false, sonst nur die aktivierten Benutzer
	 */
	public List<UserDto> getAllUsers(boolean onlyEnabledUsers)
	{
		List<User> attachedUsers = new ArrayList<User>();

		try
		{
			if (!onlyEnabledUsers)
			{
				userRepo.findAll().forEach(e -> attachedUsers.add(e));
			}
			else
			{
				userRepo.findAllEnabledUsers(true).forEach(e -> attachedUsers.add(e));
			}
		}
		catch (final Exception e)
		{
			logger.error(e);
		}

		AutoPopulatingList<UserDto> result = new AutoPopulatingList<UserDto>(UserDto.class);

		for (User attachedUser : attachedUsers)
		{
			result.add(UserMapper.mapToDto(attachedUser));
		}

		return result;
	}

	/**
	 * @param user
	 *            Benutzer
	 * @return Den dem Benutzer zugeordneten Standardmandanten
	 */
	public ClientDto getDefaultClientForUser(UserDto user)
	{
		try
		{
			User persistentUser = userRepo.findOne(user.getId());

			if (persistentUser != null)
			{
				return ClientMapper.mapToDto(persistentUser.getDefaultClient());
			}
		}
		catch (final Exception e)
		{
			logger.error(e);
		}

		return null;
	}

	/**
	 * @param id
	 *            Benutzer-ID
	 * @return Den zur ID gehördenen Benutzer
	 */
	public UserDto getUserById(Long id)
	{
		try
		{
			User user = userRepo.findOne(id);

			return (user != null) ? UserMapper.mapToDto(user) : null;

		}
		catch (final Exception e)
		{
			logger.error(e);
		}

		return null;
	}

	/**
	 * @param login
	 *            Login des Benutzers
	 * @return Den zum Login gehörenden Benutzer
	 */
	public UserDto getUserByLogin(String login)
	{
		try
		{
			User user = userRepo.findUserByLogin(login);

			return (user != null) ? UserMapper.mapToDto(user) : null;

		}
		catch (final Exception e)
		{
			logger.error(e);
		}

		return null;
	}

	/**
	 * @param login
	 *            Benutzer-Login
	 * @param password
	 *            Durch Javascript einfach codiertes Passwort
	 * @return true, wenn Benutzer bekannt, Passwort stimmt, Benutzer aktiviert
	 *         ist, Benutzer mindestens einer Gruppe zugeordnet ist, der
	 *         Standardmandant des Benutzers aktiviert ist... sonst false
	 */
	public boolean isUserAuthorized(String login, String password)
	{
		User persistentUser = userRepo.findUserByLogin(login);

		if (persistentUser == null)
		{
			return false;
		}
		else
		{
			String finalPw = PasswordEncoder.encodeComplex(password, Long.toString(persistentUser.getId()));
			if (finalPw.equalsIgnoreCase(persistentUser.getPassword()))
			{
				if (persistentUser.isEnabled() && !persistentUser.getGroups().isEmpty()
						&& persistentUser.getDefaultClient().isEnabled())
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
	}

	/**
	 * Speichert einen Benutzer.
	 * 
	 * @param detachedUser
	 *            Zu speichernder Benutzer
	 */
	public void saveUser(UserDto detachedUser)
	{
		try
		{
			User persistentUser = userRepo.findOne(detachedUser.getId());

			if (persistentUser != null)
			{
				User attachedUser = UserMapper.mapToEntity(detachedUser, false, clientRepo, groupRepo);
				persistentUser.bind(attachedUser);
				persistentUser = userRepo.save(persistentUser);
			}
			else
			{
				User attachedUser = UserMapper.mapToEntity(detachedUser, true, clientRepo, groupRepo);
				persistentUser = userRepo.save(attachedUser);
			}
		}
		catch (final Exception e)
		{
			logger.error(e);
		}

	}

	/**
	 * Speichert eine Liste von Benutzern.
	 * 
	 * @param detachedUsers
	 *            Zu speichernde Benutzer
	 */
	public void saveUsers(List<UserDto> detachedUsers)
	{
		try
		{
			for (UserDto user : detachedUsers)
			{
				saveUser(user);
			}
		}
		catch (final Exception e)
		{
			logger.error(e);
		}
	}
}
