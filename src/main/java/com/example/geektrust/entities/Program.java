package com.example.geektrust.entities;

public class Program {

	private String name;

	private Double price;

	public Program(String name, Double price) {
		super();
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}

}
