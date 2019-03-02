package com.looser.enterprise.applying.advanced.concurrency.concurrentmap.model;

import java.util.HashSet;
import java.util.Set;

public class Movie {

	private String title;
	private int releaseYear;
	
	private Set<Actor> actors = new HashSet<>();
	
	public Movie(String title, int releaseYear) {
		super();
		this.title = title;
		this.releaseYear = releaseYear;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public Set<Actor> getActors() {
		return actors;
	}

	public void setActors(Set<Actor> actors) {
		this.actors = actors;
	}

	@Override
	public String toString() {
		return "Movie [title=" + title + ", releaseYear=" + releaseYear
				+ ", actors=" + actors + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	public void addActor(Actor actor) {
        this.actors.add(actor);
    }

    public Set<Actor> actors() {
        return this.actors;
    }
	
	
}
