package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.graph.Graph;
import org.example.model.Specie;
import org.example.model.Tank;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        List<Specie> speciesList = readSpeciesFromJson("src/main/resources/species.json");
        Graph graph = new Graph(speciesList);

        graph.buildGraph();

        List<Tank> tanks = graph.colorGraph();

        System.out.println("\n Matriz de Adjacência:\n");
        graph.printAdjacencyMatrix();
        System.out.println("\n");

        for (int i = 0; i < tanks.size(); i++) {
            System.out.println("Tank " + (i + 1) + ": " + tanks.get(i));
        }

        System.out.println("\nTotal de tanques: " + tanks.size() + "\n");
    }

    private static List<Specie> readSpeciesFromJson(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(filePath), new TypeReference<>() {
            });
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading species from JSON", e);
            return new ArrayList<>();
        }
    }
}