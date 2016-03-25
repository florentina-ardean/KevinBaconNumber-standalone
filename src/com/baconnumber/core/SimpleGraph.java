package com.baconnumber.core;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.baconnumber.model.Vertex;

public class SimpleGraph implements Graph{
	protected Map<Vertex, Set<Vertex>> vertexMap;
	
	Map<Vertex, Integer> DIST = new HashMap<>();
	Map<Vertex, Vertex> PREV = new HashMap<>();
	Set<Vertex> notVisited = new HashSet<>();
	
	/**
	 * Create an graph from given map/table.
	 */
	public void updateGraph(Map<Vertex, Set<Vertex>> connectionsMap) {
		vertexMap = connectionsMap;
	}
	
	@Override
	public boolean Dijkstra(Vertex source, Vertex target) {
		boolean success = false;
		
		// init not visited vertex set, distance and previous maps
		initMapsForDjikstra(source);
		
		try {
			while (notVisited.size() > 0) {
				//find Vertex from notVisited closest to the source Vertex
				Vertex u = findVertexWithMinDistToSource(DIST);
				
				//remove vertex from notVisited
				notVisited.remove(u);

				// update distance for each neighbor v of u: where v is still in notVisited set.
				updateDistancesForVertexNeighbours(u);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		displayPath(source, target);
		
		return success;
	}
	
	/**
	 * Initialization for Dijkstra 
	 * Populate unvisited vertex set with all vertexes
	 * Init distance for each existing vertex 
	 * Init previous for each existing vertex 
	 * 
	 * @param source
	 * @return
	 */
	public void initMapsForDjikstra(Vertex source) {
		for (Vertex v : getVertexMap().keySet()) {
			int dist = Integer.MAX_VALUE;
			
			// Distance from source to source is 0
			if (v.equals(source)) {
				dist = 0;
			}
			
			Vertex temp = new Vertex(v.getName());
			
			DIST.put(temp, dist);
			PREV.put(temp, null);

			notVisited.add(temp);
		}
	}
	
	/**
	 * Find Vertex from notVisited closest to the source Vertex
	 * @param dist
	 * @return
	 */
	public Vertex findVertexWithMinDistToSource(Map<Vertex, Integer> dist) {
		int minDist = Integer.MAX_VALUE; 
		Vertex vertex = null;
		
		for (Vertex u : notVisited) {
			int distanceU = dist.get(u);
			if(distanceU <= minDist) {
				minDist = distanceU;
				vertex = u;
			}
		}

		return vertex;
	}
	
	/**
	 * Update distance for each neighbor v of u, where v is still notVisited
	 * @param u
	 */
	private void updateDistancesForVertexNeighbours(Vertex u) {
		// neighbors of u
		Set<Vertex> neighboursU = getVertexMap().get(u);
		
		for (Vertex v : neighboursU) {
			int alt = Integer.MAX_VALUE;
			
			// if v is still notVisited
			if (notVisited.contains(v)) {
				alt = DIST.get(u) + 1;
				if (alt < DIST.get(v)) {
					DIST.put(v, alt);
					PREV.put(v, u);
				}
			}
		}
		
	}
	
	/**
	 * Display distance and connections path
	 * @param source
	 * @param target
	 */
	private void displayPath(Vertex source, Vertex target) {
		Integer distancetoTarget = DIST.get(target);
		
		if (distancetoTarget != null) {
			System.out.println("Distance from "+ source + " to " + target + " is: " +  distancetoTarget);
			
			displayConnectionPath(target, PREV);
			
		} else {
			System.out.println("Connection was not found between: " + source.getName() + " and " + target.getName());
		}
		
	}
	
	/**
	 * Display connections path
	 * @param target
	 * @param prev
	 */
	public void displayConnectionPath(Vertex target, Map<Vertex, Vertex> prev) {
		Stack<Vertex> actorStack = new Stack<Vertex>();
		//Stack<Vertex> linkStack = new Stack<Vertex>();
		
		Vertex tempV = target;
		actorStack.push(tempV);
		
		while(prev.get(tempV) != null) {
			tempV = prev.get(tempV);
			actorStack.push(tempV);
		}
		
		while (!actorStack.empty()) {
			System.out.print("-> " + actorStack.pop().getName());
		}
		
		System.out.println();
	}
	
	@Override
	public void updateGraphFromFile(File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGraphFromDirectory(String directoryName) {
		// TODO Auto-generated method stub
		
	}

	public Map<Vertex, Set<Vertex>> getVertexMap() {
		return vertexMap;
	}

	public void setVertexMap(Map<Vertex, Set<Vertex>> vertexMap) {
		this.vertexMap = vertexMap;
	}

}
