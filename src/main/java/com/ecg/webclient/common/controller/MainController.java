package com.ecg.webclient.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecg.webclient.common.ApplicationUtil;
import com.ecg.webclient.common.feature.FeatureRegistry;
import com.ecg.webclient.feature.administration.authentication.AuthenticationUtil;
import com.ecg.webclient.feature.administration.service.ClientService;
import com.ecg.webclient.feature.administration.service.UserService;
import com.ecg.webclient.feature.administration.viewmodell.ClientDto;
import com.ecg.webclient.feature.administration.viewmodell.UserDto;

@Scope("request")
@Controller
public class MainController
{
	@Autowired
	private FeatureRegistry	featureRegistry;
	@Autowired
	private ClientService	clientService;
	@Autowired
	UserService				userService;
	@Autowired
	private AuthenticationUtil authUtil;

	@Autowired
	private ApplicationUtil util;

	public MainController()
	{
		System.out.print("MainController instantiated");
	}

	@RequestMapping(value = "/changeClient", method = RequestMethod.POST)
	public String changeClient(@ModelAttribute("selectedClient") Long selectedClient)
	{
		authUtil.setSelectedClientWithNewAuthority(clientService.getClient(selectedClient));
		featureRegistry.resetActiveFeature();
		return "/main";
	}

	@RequestMapping(value = "/main/tooglemenue/true", method = RequestMethod.GET)
	public void hideMenu()
	{
		util.setMenuMinimized(true);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String loginOk()
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		featureRegistry.resetActiveFeature();
		UserDto user = userService.getUserByLogin(auth.getName());
		ClientDto defaultClient = userService.getDefaultClientForUser(user);
		authUtil.setSelectedClient(defaultClient);
		return "/main";
	}

	@RequestMapping(value = "/main/tooglemenue/false", method = RequestMethod.GET)
	public void showMenu()
	{
		util.setMenuMinimized(false);
	}
}
