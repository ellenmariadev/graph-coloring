package org.example.graph;

import org.example.model.Specie;
import org.example.model.Tank;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final List<Specie> speciesList;
    private final boolean[][] adjacencyMatrix;

    public Graph(List<Specie> species) {
        this.speciesList = species;
        adjacencyMatrix = new boolean[species.size()][species.size()];
    }

    public void buildGraph() {
        for (int i = 0; i < speciesList.size(); i++) {
            for (int j = i + 1; j < speciesList.size(); j++) {
                // Add edge only if species have a predation relationship
                if (speciesList.get(i).getDiet().contains(speciesList.get(j).getName()) ||
                        speciesList.get(j).getDiet().contains(speciesList.get(i).getName())) {
                    addEdge(speciesList.get(i), speciesList.get(j));
                }
            }
        }
    }

    public void addEdge(Specie specie1, Specie specie2) {
        int specieIndex1 = speciesList.indexOf(specie1);
        int specieIndex2 = speciesList.indexOf(specie2);

        // Add bidirectional edge - undirected graph
        adjacencyMatrix[specieIndex1][specieIndex2] = true;
        adjacencyMatrix[specieIndex2][specieIndex1] = true;
    }

    public List<Tank> colorGraph() {
        List<Tank> tanks = new ArrayList<>();

        for (Specie specie : speciesList) {
            boolean added = false;

            for (Tank tank : tanks) {
                if (canAddSpecieToTank(specie, tank)) {
                    tank.addSpecie(specie);
                    added = true;
                    break;
                }
            }

            if (!added) {
                Tank newTank = new Tank();
                newTank.addSpecie(specie);
                tanks.add(newTank);
            }
        }

        return tanks;
    }

    private boolean canAddSpecieToTank(Specie specie, Tank tank) {
        for (Specie otherSpecie : tank.getSpecies()) {
            if (adjacencyMatrix[speciesList.indexOf(specie)][speciesList.indexOf(otherSpecie)] ||
                    adjacencyMatrix[speciesList.indexOf(otherSpecie)][speciesList.indexOf(specie)]) {
                return false;
            }
        }

        return true;
    }

    public void printAdjacencyMatrix() {
        System.out.print("   ");
        for (Specie specie : speciesList) {
            System.out.printf("%-4s", specie.getName().substring(0, Math.min(specie.getName().length(), 3)));
        }
        System.out.println();

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            System.out.printf("%-4s", speciesList.get(i).getName().substring(0, Math.min(speciesList.get(i).getName().length(), 3)));
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                System.out.printf("%-4s", adjacencyMatrix[i][j] ? "T" : "F");
            }
            System.out.println();
        }
    }
}