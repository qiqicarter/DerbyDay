package com.dt.derbyday.utils;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.dt.derbyday.constant.WechatData;
import com.dt.derbyday.model.UserInfo;

public class WeixinUtils {
	public static String getWechatOpenIdByCode(String code) {
		try {
			String appid_sj = WechatData.APP_ID;
			String appsecret_sj = WechatData.APP_SECRET;

			String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid_sj + "&secret="
					+ appsecret_sj.trim() + "&code=" + code + "&grant_type=authorization_code";

			String tempValue = HttpUtils.doGet(URL);
			JSONObject jsonObj = JSON.parseObject(tempValue);
			if (jsonObj.containsKey("errcode")) {
				return null;
			}
			return jsonObj.getString("openid");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Map<String, String> getOpenIdAndTokenByCode(String code) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			
			String appid_sj = WechatData.APP_ID;
			String appsecret_sj = WechatData.APP_SECRET;

			String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid_sj + "&secret="
					+ appsecret_sj.trim() + "&code=" + code + "&grant_type=authorization_code";

			String tempValue = HttpUtils.doGet(URL);
			JSONObject jsonObj = JSON.parseObject(tempValue);
			if (jsonObj.containsKey("errcode")) {
				return null;
			}
			params.put("openId", jsonObj.getString("openid"));
			params.put("token", jsonObj.getString("access_token"));
			
			return params;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static HashMap<String, String> getUserinfoByOpenId(String openId) throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();

		String accessToken = "";
		if (accessToken != null) {

			String url = WechatData.USER_INFO_URL + "?access_token=" + accessToken + "&openid=" + openId
					+ "&lang=zh_CN";
			System.out.println("url = " + url);
			String userText = HttpUtils.doGet(url);
			JSONObject jsonObj = JSON.parseObject(userText);
			if (!jsonObj.containsKey("errcode")) {
				params.put("nickname", jsonObj.getString("nickname")); // 昵称
				params.put("headimgurl", jsonObj.getString("headimgurl")); // 图像
				params.put("sex", jsonObj.getString("sex")); // 性别

				return params;
			}
		}
		return null;

	}
	
	public static UserInfo getUserinfoByOpenId(String openId,String accessToken) throws Exception {
		UserInfo user = new UserInfo();

		if (accessToken != null) {

			String url = WechatData.USER_INFO_URL + "?access_token=" + accessToken + "&openid=" + openId
					+ "&lang=zh_CN";
			System.out.println("url = " + url);
			String userText = HttpUtils.doGet(url);
			JSONObject jsonObj = JSON.parseObject(userText);
			if (!jsonObj.containsKey("errcode")) {
				user.setNick(jsonObj.getString("nickname"));
				user.setHead(jsonObj.getString("headimgurl"));
				user.setSex(jsonObj.getString("sex"));

				return user;
			}
		}
		return null;

	}

}
