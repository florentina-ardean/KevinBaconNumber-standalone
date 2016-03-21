package com.baconnumber.core;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.baconnumber.model.Movie;
import com.baconnumber.model.Vertex;

public class GraphImpl implements Graph {
	private Map<Vertex, Set<Vertex>> vertexMap;

	/**
	 * Create an empty graph with no vertices or edges.
	 */
	public GraphImpl() {
		vertexMap = new HashMap<Vertex, Set<Vertex>>();
	}

	/**
	 * Create an graph from given map/table.
	 */
	public void init(Map<Vertex, Set<Vertex>> connectionsMap) {
		vertexMap = connectionsMap;
	}

	/**
	 * Update vertex Map - connections for every vertex
	 */
	public void updateGraph(Vertex vertex, List<Vertex> currentConnections) {
		// for every vertex add all connections
		Set<Vertex> toBeSaved = getAllNewConnections(vertex, currentConnections);

		if (toBeSaved != null) {
			// look for the vertex in the map
			Set<Vertex> savedActors = vertexMap.get(vertex);

			// if null create set
			if (savedActors == null) {
				savedActors = new HashSet<Vertex>();
			}

			// add new connections
			savedActors.addAll(toBeSaved);

			// update map
			vertexMap.put(vertex, savedActors);
		}
	}
	
	public void updateGraphFromMovie(Movie movie) {
		if (movie != null) {
			List<Vertex> cast = movie.getCast();
			for (Vertex vertex : cast) {
				updateGraph(vertex, cast);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.baconnumber.core.Graph#updateGraphFromFile(java.io.File)
	 */
	@Override
	public void updateGraphFromFile(File file) {
		updateGraphFromMovie(Movie.loadMovieFromFile(file));
	}
	
	/* (non-Javadoc)
	 * @see com.baconnumber.core.Graph#updateGraphFromDirectory(java.lang.String)
	 */
	@Override
	public void updateGraphFromDirectory(String directoryName) {
		File folder = new File("./" + directoryName);
		
		File[] listOfFiles = folder.listFiles();
		
		int count = 0;
		for (File file : listOfFiles) {
			if (file.isFile()) {
				System.out.println("Loaded: " + file.getName());
				updateGraphFromFile(file);
				count++;
			}
		}
		System.out.println("Files loaded: " + count);
	}

	/**
	 * Get all only new connections
	 * 
	 * @param vertex
	 * @param currentNeighbours
	 * @return
	 */
	public Set<Vertex> getAllNewConnections(Vertex vertex, List<Vertex> currentNeighbours) {
		Set<Vertex> toBeSaved = new HashSet<Vertex>();

		for (Vertex vertex2 : currentNeighbours) {
			if (vertex2.getName().equals(vertex.getName())) {
				continue;
			} else {
				// create all connections set
				toBeSaved.add(vertex2);
			}
		}

		return toBeSaved;
	}

	/* (non-Javadoc)
	 * @see com.baconnumber.core.Graph#Dijkstra(com.baconnumber.model.Vertex, com.baconnumber.model.Vertex)
	 */
	@Override
	public boolean Dijkstra(Vertex source, Vertex target) {
		boolean success = false;
		Vertex foundTarget = null;
		
		Map<Vertex, Integer> DIST = new HashMap<>();
		Map<Vertex, Vertex> PREV = new HashMap<>();

		// create vertex set Q
		Map<Vertex, Set<Vertex>> vertexMap = getVertexMap();
		Set<Vertex> Q = initVertexSet(vertexMap.keySet(), source, DIST, PREV);
		
		try {
			while (Q.size() > 0) {
				Vertex u = findVertexWithMinDist(Q, DIST);

				if (u.getName().equals(target.getName())) {
					foundTarget = u;
					success = true;
				}
				Q.remove(u);
	
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
	
	public void displayConnectionPath(Vertex target, Map<Vertex, Vertex> prev) {
		Stack<Vertex> S = new Stack<Vertex>();
		
		Vertex tempV = target;
		S.push(tempV);
		
		while(prev.get(tempV) != null) {
			tempV = prev.get(tempV);
			S.push(tempV);
		}
		
		while (!S.empty()) {
			System.out.print("-> " + S.pop().getName());
		}
		
		System.out.println();
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
	
	public Set<Vertex> initVertexSet(Set<Vertex> vSet, Vertex source, Map<Vertex, Integer> distanceMap, Map<Vertex, Vertex> previousMap) {
		Set<Vertex> Q = new HashSet<>();

		for (Vertex v : vSet) {
			int dist = Integer.MAX_VALUE;
			
			if (v.getName().equals(source.getName())) {
				// Distance from source to source is 0
				dist = 0;
			}
			
			Vertex temp = new Vertex(v.getName());
			
			distanceMap.put(temp, dist);
			previousMap.put(temp, null);

			Q.add(temp);
		}

		return Q;
	}

	/**
	 * Return the set of neighbors of vertex v as an Iterable.
	 */
	public Iterable<Vertex> adjacentTo(Vertex vertex) {
		for (Vertex v : vertexMap.keySet()) {
			if (v.getName().equals(vertex.getName())) {
				return vertexMap.get(v);
			}
		}

		return null;
	}

	public Map<Vertex, Set<Vertex>> getVertexMap() {
		return vertexMap;
	}

	public void setVertexMap(Map<Vertex, Set<Vertex>> vMap) {
		this.vertexMap = vMap;
	}

	/**
	 * Return a string representation of the graph.
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();

		for (Vertex v : vertexMap.keySet()) {
			s.append(v.getName() + ": ");

			for (Vertex w : vertexMap.get(v)) {
				s.append(w.getName() + ", ");
			}

			s.append("\n");
		}

		return s.toString();
	}

}
