package com.baconnumber.core;

import java.io.File;

import com.baconnumber.model.Vertex;

public interface Graph {

	void updateGraphFromFile(File file);

	void updateGraphFromDirectory(String directoryName);

	boolean Dijkstra(Vertex source, Vertex target);

}