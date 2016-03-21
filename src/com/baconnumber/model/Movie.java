package com.baconnumber.model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Movie {
	private Film film;
	private List<Vertex> cast;

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public List<Vertex> getCast() {
		return cast;
	}

	public void setCast(List<Vertex> cast) {
		this.cast = cast;
	}
	
	@Override
	public String toString() {
		return "Movie:" + film.toString() + " cast " + cast.toString();
	}
	
	public static Movie loadMovieFromFile(File file) {
		Movie movie = null;
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			movie = mapper.readValue(file, Movie.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return movie;
	}
}
