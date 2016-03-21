package com.baconnumber.service;

import java.io.File;

import com.baconnumber.core.Graph;
import com.baconnumber.core.GraphImpl;
import com.baconnumber.model.Vertex;

public class BaconNumberServiceImpl implements BaconNumberService {
	Graph graph = new GraphImpl();
	
	@Override
	public boolean findConnectionToKevinBacon(String actor) {
		return findConnection("data", "Kevin Bacon", actor);
	}
	
	@Override
	public boolean findConnection(String directoryName, String firstActor, String secondActor) {
		boolean foundConnection = false;
		try {
			graph.updateGraphFromDirectory(directoryName);
			System.out.println("\nComputing...");
			foundConnection = graph.Dijkstra(new Vertex(firstActor), new Vertex(secondActor));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return foundConnection;
	}

}
