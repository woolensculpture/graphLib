package GraphLib.Weighted;

import GraphLib.Graph;

/**
 * Created by Benjamin Reynolds on 2/22/2016.
 */
public interface WeightedGraph extends Graph{
    int getEdgeWeight(int startNode, int endNode);
    void addWeightedEdge(int startNode, int EndNode, int weight);

    int[] shortestPaths(int startNode);

    WeightedGraph augmentedPaths(int source, int sink);
}
