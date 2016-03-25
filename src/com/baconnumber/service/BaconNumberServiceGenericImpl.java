package com.baconnumber.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import com.baconnumber.core.Graph;
import com.baconnumber.core.SimpleGraph;
import com.baconnumber.model.Vertex;
import com.baconnumber.profiler.Profiler;;

public class BaconNumberServiceGenericImpl implements BaconNumberService {
	Graph graph = new SimpleGraph();
	Map<Vertex, Set<Vertex>> actorsConnections = new HashMap<>();
	
	@Override
	public void loadData(String directoryName) {
		
		loadInfoFromDirectory(directoryName);
		
		graph.updateGraph(actorsConnections);
		
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
	
	private void loadInfoFromDirectory(String directoryName) {
		System.out.println("Loading files ... ");
		
		int count = 0;
		
		Path dir = Paths.get("./" + directoryName);

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (Path path : stream) {
				File file = path.toFile();
				
//				System.out.println("Loaded: " + file.getName());
				
				AbstractMap.SimpleEntry<Vertex,Set<Vertex>> movieEntry = loadInfoFromFile(file);
				
				processInfo(movieEntry, actorsConnections);
				
				count++;
			}
			
			System.out.println("Actors number: " + actorsConnections.keySet().size());
			
		} catch (IOException | DirectoryIteratorException x) {
			// IOException can never be thrown by the iteration.
			// In this snippet, it can only be thrown by newDirectoryStream.
			System.err.println(x);
		}
		
		System.out.println("Files loaded: " + count);
	}
	
	/**
	 * Update map with actors and their direct connections
	 * Two actors are directly connected if they played in the same Movie
	 * @param movieEntry
	 * @param actorsConnections
	 */
	private void processInfo(SimpleEntry<Vertex, Set<Vertex>> movieEntry, Map<Vertex, Set<Vertex>> actorsConnections) {
		if (movieEntry != null) {
			Set<Vertex> actors = movieEntry.getValue();
			
			if (actors != null && actors.size() > 0) {
				//for every actor in the movie update map of connections
				for (Vertex actor : actors) {
					//get current connections
					Set<Vertex> connections = actorsConnections.get(actor);
					
					//if actor has not connections create new set
					if (connections == null) {
						connections = new HashSet<>();
					}
					
					//update current connections 
					connections.addAll(actors);
					
					//update map of connections
					actorsConnections.put(actor, connections);
				}
			}
		}
		
	}

	private AbstractMap.SimpleEntry<Vertex,Set<Vertex>> loadInfoFromFile(File file) {
		AbstractMap.SimpleEntry<Vertex,Set<Vertex>> mapEntry = null;
		
		try (InputStream is = Files.newInputStream(file.toPath());
			JsonReader jR = Json.createReader(is)){
			
			//read data from file
			JsonObject jsonObj = jR.readObject();
			JsonObject dataObj = jsonObj.getJsonObject("film");
			JsonArray castArray = jsonObj.getJsonArray("cast");
			
			//convert json objects to vertex objects
			String image = (dataObj.containsKey("image") ? dataObj.getString("image") : null);
			Vertex movie =  new Vertex(dataObj.getString("name"), image);
			
			Set<Vertex> cast = new HashSet<>();
			
			for (JsonValue jsonVal : castArray) {
				JsonObject obj = (JsonObject) jsonVal;
				String actorImage = (obj.containsKey("image") ? obj.getString("image") : null);
				Vertex vertex = new Vertex(obj.getString("name"), actorImage);
				cast.add(vertex);
			}
			
			mapEntry = new SimpleEntry<Vertex, Set<Vertex>>(movie, cast);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			
		}
		
		return mapEntry;
	}

	public Graph getGraph() {
		return graph;
	}
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	public Map<Vertex, Set<Vertex>> getActorsConnections() {
		return actorsConnections;
	}
	public void setActorsConnections(Map<Vertex, Set<Vertex>> actorsConnections) {
		this.actorsConnections = actorsConnections;
	}
}
