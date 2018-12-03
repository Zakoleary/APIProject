package com.userFront.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/") /*registering the root path to method, which redirects to our Homepage*/
		public String home() {
			return "redirect:/index";
		}
	
	@RequestMapping("/index") /*making sure we hit the homepage, spring with know the extension is html!*/
		public String index() {
		return "index";
	}
}

