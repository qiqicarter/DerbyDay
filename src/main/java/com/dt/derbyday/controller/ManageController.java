package com.dt.derbyday.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dt.derbyday.service.ManageService;

@RestController
@RequestMapping("/manage")
public class ManageController {
	
	@Autowired
	ManageService manageService;

	@GetMapping("/createGame")
	public Map<String, Object> createGame(@RequestParam(required = true) String game,
			@RequestParam(required = true) String title, @RequestParam(required = true) String picUrl,
			@RequestParam(required = false) String start, @RequestParam(required = false) String end,
			HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();

		return resultMap;
	}
}
