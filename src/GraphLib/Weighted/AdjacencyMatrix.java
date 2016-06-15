package GraphLib.Weighted;

/**
 * Created by Benjamin Reynolds on 2/21/2016.
 */
//TODO
public class AdjacencyMatrix extends DirectedAdjacencyMatrix {
    AdjacencyMatrix(){super();}
    AdjacencyMatrix(int nodes){super(nodes);}
    AdjacencyMatrix(int nodes, int[][] edges){super(nodes,edges);}

    @Override
    public void addWeightedEdge(int start, int end, int weight) {
        super.addWeightedEdge(start, end, weight);
        super.addWeightedEdge(end, start, weight);
    }

    @Override
    public boolean isStronglyConnected() {
        return DFS(0).length == matrix.length;
    }
}
