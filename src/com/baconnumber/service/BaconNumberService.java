package com.baconnumber.service;

public interface BaconNumberService {

	void loadData(String directoryName);
	
	boolean findConnectionToKevinBacon(String actor);
	
	boolean findConnection(String start, String end);

}