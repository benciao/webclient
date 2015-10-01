package com.ecg.webclient.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SiteController
{
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login()
	{
		return "login";
	}
	
	@RequestMapping(value = "/login/error", method = RequestMethod.POST)
	public String loginError(Model model)
	{
		model.addAttribute("loginFailed", true);
		model.addAttribute("errorCause", "bla");
		return "login";
	}
}
