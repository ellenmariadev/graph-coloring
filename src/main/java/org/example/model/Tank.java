package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Tank {
    List<Specie> species;

    public Tank() {
        this.species = new ArrayList<>();
    }

    public void addSpecie(Specie specie) {
        species.add(specie);
    }

    public List<Specie> getSpecies() {
        return species;
    }

    @Override
    public String toString() {
        return "" + species.stream().map(Specie::getName).toList();
    }
}