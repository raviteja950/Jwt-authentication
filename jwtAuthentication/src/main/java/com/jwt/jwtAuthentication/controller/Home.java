package com.jwt.jwtAuthentication.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

	@RequestMapping("/welcome")
	public String welcome() {

		String text = "Heelo welcome to this world";
		text += "Enjoy";
		return text;
	}

}
