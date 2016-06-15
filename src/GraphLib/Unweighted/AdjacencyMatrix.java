package GraphLib.Unweighted;

/**
 * Created by Benjamin Reynolds on 2/19/2016.
 */
public class AdjacencyMatrix extends DirectedAdjacencyMatrix {

    AdjacencyMatrix(){super();}
    AdjacencyMatrix(int nodes){super(nodes);}
    AdjacencyMatrix(int nodes, int[][] edges){super(nodes,edges);}

    @Override
    public void addEdge(int start, int end){
        matrix[start][end] = true;
        matrix[end][start] = true;
    }

    public boolean isConnected() {
        return DFS(0).length == matrix.length;
    }

}
