package com.dt.derbyday.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author xiezhiwei
 *	读取运行时动态读取properties文件的工具类
 */
@Repository("configUtil")
public class ConfigUtil {

	private static final Logger logger = Logger.getLogger(ConfigUtil.class);
	
	private static final String FILE_NAME = "config.properties";
	
	private static Map<String,Properties> CONFIG = new HashMap<String,Properties>();
	
	/**
	 * 获取value
	 * @param key key值
	 * @return value值
	 */
	public String getProperty(String key) {
		
		return this.getProperty(key, FILE_NAME);
	}
	
	/**
	 * 从指定文件中获取value值
	 * @param key key值
	 * @param file 文件路径
	 * @return value值
	 */
	public String getProperty(String key,String file) {
		
		Properties prop = null;
		
		synchronized(CONFIG) {
		
			prop = CONFIG.get(file);
			
			if(prop == null) {
				
				prop = this.getProperties(file);
				CONFIG.put(file, prop);
			}
		}
		
		if(prop == null) {
			
			return null;
		}
		
		return prop.getProperty(key);
	}
	
	/**
	 * 获取指定文件的key-value集合
	 * @param file 文件路径
	 * @return value值
	 */
    public Properties getProperties(String file) {
		
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
		
		Properties props = new Properties();
		
		try {
		
			props.load(input);
			
			input.close();
		} catch (Exception e) {
			
			logger.error("file="+ file +" read fail!");
			
			logger.error(e.getMessage(), e);
		} finally {
			
			if(input != null) {
				
				try {
					input.close();
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
			}
		}
		
		return props;
	}
}

