package com.baconnumber.service;

import com.baconnumber.core.Graph;
import com.baconnumber.core.GraphImpl;
import com.baconnumber.core.GraphImpl2;
import com.baconnumber.model.Vertex;
import com.baconnumber.profiler.Profiler;

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
			
			Profiler.Start(1);
			foundConnection = graph.Dijkstra(new Vertex(firstActor), new Vertex(secondActor));
			Profiler.End(1, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return foundConnection;
	}

}
