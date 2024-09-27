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
                // Adicionar aresta apenas se as espécies tiverem relação de predação
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

        // Adicionar aresta bidirecional - grafo não direcionado
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
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
