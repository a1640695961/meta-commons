package com.meta.commons.lang;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author basten
 *
 * jackson工具
 */
public final class JacksonUtil {
	
	public static String obj2String(Object obj) {
		try {
	        ObjectMapper objectMapper = new ObjectMapper();
//	        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	        return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static <T>T String2Obj(String jsonString, Class<T> clazz) {
		try {
	        ObjectMapper objectMapper = new ObjectMapper();
//	        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	        return objectMapper.readValue(jsonString, clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static <T>List<T> string2List(String jsonString, Class<T[]> clazz) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
//	        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	        List<T> tList = Arrays.asList(objectMapper.readValue(jsonString, clazz));
	        return tList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
//	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
//		List<User> userList = JacksonUtil.<User>string2List("", User[].class);
//		System.out.println(userList);
//	}

}
//
//class User {
//	private String name;
//	private String age;
//	
//	
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public String getAge() {
//		return age;
//	}
//	public void setAge(String age) {
//		this.age = age;
//	}
//	@Override
//	public String toString() {
//		return "User [name=" + name + ", age=" + age + "]";
//	}
//	
	
//}
