package com.baconnumber.service;

import com.baconnumber.core.Graph;
import com.baconnumber.core.GraphImpl;
import com.baconnumber.core.GraphImpl2;
import com.baconnumber.model.Vertex;
import com.baconnumber.profiler.Profiler;

public class BaconNumberServiceImpl implements BaconNumberService {
	Graph graph = new GraphImpl();
	
	@Override
	public void loadData(String directoryName) {
		graph.updateGraphFromDirectory(directoryName);
		
	}
	
	@Override
	public boolean findConnectionToKevinBacon(String actor) {
		return findConnection("Kevin Bacon", actor);
	}
	
	@Override
	public boolean findConnection(String firstActor, String secondActor) {
		boolean foundConnection = false;
		try {
			
			System.out.println("\nComputing...");
			
			
			foundConnection = graph.Dijkstra(new Vertex(firstActor), new Vertex(secondActor));
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return foundConnection;
	}

}
