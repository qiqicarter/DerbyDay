package com.dt.derbyday.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dt.derbyday.model.Choice;
import com.dt.derbyday.model.GameInfo;
import com.dt.derbyday.model.Question;
import com.dt.derbyday.service.ManageService;

@RestController
@RequestMapping("/manage")
public class ManageController {
	
	@Autowired
	ManageService manageService;

	@PostMapping("/createGame")
	public Map<String, Object> createGame(@RequestParam(required = true) String game,
			@RequestParam(required = true) String title, @RequestParam(required = true) String picUrl,
			@RequestParam(required = false) String start, @RequestParam(required = false) String end,
			HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date sdate = sdf.parse(start);
			Date edate = sdf.parse(end);
			
			GameInfo gi = new GameInfo();
			gi.setTitle(title);
			gi.setGame(game);
			gi.setPicUrl(picUrl);
			gi.setStartDate(sdate);
			gi.setEndDate(edate);
			
			manageService.createGame(gi);
			
			resultMap.put("code", 200);
			resultMap.put("message", "OK");
		} catch (ParseException e) {
			resultMap.put("code", 500);
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@PostMapping("/getGames")
	public Map<String, Object> getGames(HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<GameInfo> list = manageService.getGames();
			
			resultMap.put("code", 200);
			resultMap.put("games", list);
			resultMap.put("message", "OK");
		} catch (Exception e) {
			resultMap.put("code", 500);
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
		}
		
		return resultMap;
	}
	
	@PostMapping("/delGame")
	public Map<String, Object> delGame(@RequestParam(required = true) String gameId,HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			manageService.deleteGame(Integer.parseInt(gameId));
			
			resultMap.put("code", 200);
			resultMap.put("message", "OK");
		} catch (Exception e) {
			resultMap.put("code", 500);
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
		}
		
		return resultMap;
	}
	
	@PostMapping("/addQuestion")
	public Map<String, Object> addQuestion(@RequestParam(required = true) String json,HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		//{"question":{"game":"111","maxChoice":5,"question":"问题问题问题","seq":1},"choices":[{"choice":"选项内容1","score":2,"seq":3},{"choice":"选项内容2","score":2,"seq":1},{"choice":"选项内容3","score":2,"seq":2}]}
		System.out.println(json);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JSON.parseObject(
					json,new TypeReference<Map<String, Object>>(){} );
			JSONObject q = (JSONObject)map.get("question");
			Question qq = q.toJavaObject(Question.class);
			List<JSONObject> list = (List<JSONObject>)map.get("choices");
			List<Choice> choices = new ArrayList<Choice>();
			
			for(JSONObject jo : list){
				Choice choice = jo.toJavaObject(Choice.class);
				choices.add(choice);
			}
			
			manageService.createWholeQuestion(qq, choices);
			
			resultMap.put("code", 200);
			resultMap.put("message", "OK");
		} catch (Exception e) {
			resultMap.put("code", 500);
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
		}
		
		return resultMap;
	}
}
