package com.baconnumber.model;

public class Vertex implements Comparable<Vertex>{
	private String name;
	private String image;
	private Integer distance;
	
	public Vertex () {
		name = null;
		image = null;
		distance = null;
	}
	
	public Vertex (String vName) {
		name = vName;
	}
	
	public Vertex (String vName, String vImage) {
		name = vName;
		image = vImage;
	}
	
	public Vertex (String vName, Integer dist) {
		name = vName;
		distance = dist;
	}
	
	public Vertex (Vertex v) {
		this.name = v.getName();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;
		
		Vertex other = (Vertex) obj;

		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;

		return true;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	@Override
	public int compareTo(Vertex v) {
		return this.distance.compareTo(v.distance);
	}
	
}
