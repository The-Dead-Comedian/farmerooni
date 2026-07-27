package com.dead_comedian.farmerooni.entities.ai.data_stuff;

import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;

/*
    Generic tree adapted from https://www.geeksforgeeks.org/dsa/introduction-to-tree-data-structure/
*/

class Lumber {
    BlockPos data;
    List<Lumber> neighbours;

    Lumber(BlockPos x) {
        data = x;
        neighbours = new ArrayList<>();
    }

    public void addNeighbour(Lumber neighbourinquestion) {
        this.neighbours.add(neighbourinquestion);
    }

    public void removeNeighbour(Lumber neighbourinquestion) {
        this.neighbours.remove(neighbourinquestion);
    }

    // Function to print degrees of each node
    static void printDegrees(Lumber node, Lumber parent) {
        int degree = node.neighbours.size();
        if (parent != null)
            degree++;
        System.out.println(node.data + " -> " + degree);

        for (Lumber child : node.neighbours)
            printDegrees(child, node);
    }


}

