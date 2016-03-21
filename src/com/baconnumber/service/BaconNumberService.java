package com.baconnumber.service;

public interface BaconNumberService {

	boolean findConnectionToKevinBacon(String actor);
	
	boolean findConnection(String directoryName, String start, String end);
}