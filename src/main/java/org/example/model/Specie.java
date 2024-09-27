package org.example.model;

import java.util.List;

public class Specie {
    public int id;
    String name;
    List<String> diet;

    public Specie() {
    }

    public Specie(int id, String name, List<String> diet) {
        this.id = id;
        this.name = name;
        this.diet = diet;
    }

    public String getName() {
        return name;
    }

    public List<String> getDiet() {
        return diet;
    }
}