package com.nami.test;

import java.util.HashSet;
import java.util.Set;

public class Student {

	private String name;
	
	private Set<String> book = new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getBook() {
		return book;
	}

	public void setBook(Set<String> book) {
		this.book = book;
	}
	
	public void addBook(String k) {
		book.add(k);
	}
	
	
}
