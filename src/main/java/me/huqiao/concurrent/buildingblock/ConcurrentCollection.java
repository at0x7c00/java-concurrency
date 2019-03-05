package me.huqiao.concurrent.buildingblock;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentCollection {

	public static void main(String[] args) {
		
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
		map.putIfAbsent("hello", "hello");
		
	}
}
