package com.baconnumber.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.baconnumber.model.Vertex;

public class GraphImpl2 extends GraphImpl {
	//private Map<Vertex, Set<Vertex>> vertexMap;

	/**
	 * Create an empty graph with no vertices or edges.
	 */
	public GraphImpl2() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.baconnumber.core.Graph#Dijkstra(com.baconnumber.model.Vertex, com.baconnumber.model.Vertex)
	 */
	public boolean DijkstraQueue(Vertex source, Vertex target) {
		boolean success = false;
		Vertex foundTarget = null;
		
		Map<Vertex, Integer> DIST = new HashMap<>();
		Map<Vertex, Vertex> PREV = new HashMap<>();

		// create vertex set Q
		Map<Vertex, Set<Vertex>> vertexMap = getVertexMap();
		Queue<Vertex> Q = initVertexQueue(vertexMap.keySet(), source, DIST, PREV);
		
		try {
			while (Q.size() > 0) {
				Vertex u = Q.poll();

				if (u.equals(target)) {
					foundTarget = u;
					success = true;
				}
				//Q.remove(u);
	
				// neighbors of u
				Set<Vertex> neighboursU = getVertexMap().get(u);

				int alt = Integer.MAX_VALUE;

				// for each neighbor v of u: where v is still in Q.
				for (Vertex v : neighboursU) {
					// if v is still in Q
					if (Q.contains(v)) {
						alt = DIST.get(u) + 1;
						if (alt < DIST.get(v)) {
							DIST.put(v, alt);
							PREV.put(v, u);
							
							v.setDistance(alt);
							Q.remove(v);
							Q.offer(v);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Distance from "+ source + " to " + target + " is: " +  DIST.get(foundTarget));
		
		if (foundTarget != null) {
			displayConnectionPath(foundTarget, PREV);
		}
		
		return success;
	}
	
	public Queue<Vertex> initVertexQueue(Set<Vertex> vSet, Vertex source, Map<Vertex, Integer> distanceMap, Map<Vertex, Vertex> previousMap) {
		Queue<Vertex> Q = new PriorityQueue();

		for (Vertex v : vSet) {
			int dist = Integer.MAX_VALUE;
			
			if (v.equals(source)) {
				// Distance from source to source is 0
				dist = 0;
			}
			
			Vertex temp = new Vertex(v.getName(), dist);
			
			distanceMap.put(temp, dist);
			previousMap.put(temp, null);

			Q.offer(temp);
		}

		return Q;
	}
	
	/* (non-Javadoc)
	 * @see com.baconnumber.core.Graph#Dijkstra(com.baconnumber.model.Vertex, com.baconnumber.model.Vertex)
	 */
	@Override
	public boolean Dijkstra(Vertex source, Vertex target) {
		boolean success = false;
		Vertex foundTarget = null;
		
		Integer a = null;
		
		Map<Vertex, Integer> DIST = new HashMap<>();
		Map<Vertex, Vertex> PREV = new HashMap<>();

		Map<Vertex, Set<Vertex>> vertexMap = getVertexMap();
		initDistAndPrevious(vertexMap.keySet(), source, DIST, PREV);
		
		//create Q  - unvisited nodes - with source element
		Set<Vertex> frontier = new HashSet<>();
		frontier.add(source);
		
		Set<Vertex> explored = new HashSet<>();
		
		try {
			do {
				Vertex u = findVertexWithMinDist(frontier, DIST);

				if (u.equals(target)) {
					foundTarget = u;
					success = true;
				}
				
				frontier.remove(u);
				explored.add(u);
	
				// neighbors of u
				Set<Vertex> neighboursU = getVertexMap().get(u);

				int alt = Integer.MAX_VALUE;

				// for each neighbor v of u: where v is still in Q.
				for (Vertex v : neighboursU) {
					// if v is still in Q
					if (!explored.contains(v)){
//						if (frontier.contains(v)) 
						{
							alt = DIST.get(u) + 1;
							if (alt < DIST.get(v)) {
								DIST.put(v, alt);
								PREV.put(v, u);
							}
							frontier.add(v);
						}
					}
				}
			} while (!frontier.isEmpty());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Distance from "+ source + " to " + target + " is: " +  DIST.get(foundTarget));
		
		if (foundTarget != null) {
			displayConnectionPath(foundTarget, PREV);
		}
		
		return success;
	}
	
	public Vertex findVertexWithMinDist(Set<Vertex> Q, Map<Vertex, Integer> dist) {
		int minDist = Integer.MAX_VALUE; 
		Vertex vertex = null;
		
		for (Vertex u : Q) {
			int distanceU = dist.get(u);
			if(distanceU <= minDist) {
				minDist = distanceU;
				vertex = u;
			}
		}

		return vertex;
	}
	/**
	 * init vectors distance and previous
	 * init Q  - unvisited nodes - with source element
	 */
	public void initDistAndPrevious(Set<Vertex> vSet, Vertex source, Map<Vertex, Integer> distanceMap, Map<Vertex, Vertex> previousMap) {
		for (Vertex v : vSet) {
			int dist = Integer.MAX_VALUE;
			
			// Distance from source to source is 0
			if (v.equals(source)) {
				dist = 0;
			}
			
			Vertex temp = new Vertex(v.getName());
			
			distanceMap.put(temp, dist);
			previousMap.put(temp, null);
		}

	}
	
}
