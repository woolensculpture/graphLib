package GraphLib.Weighted;


/**
 * Created by Benjamin Reynolds on 2/21/2016.
 */
//TODO
public class AdjacencyList extends DirectedAdjacencyList {

    AdjacencyList(){super();}

    AdjacencyList(int nodes){
        super(nodes);
    }

    AdjacencyList(int nodes, int[][] edges){
        super(nodes, edges);
    }

    @Override
    public void addWeightedEdge(int startNode, int endNode, int weight){
        super.addWeightedEdge(startNode, endNode, weight);
        super.addWeightedEdge(endNode,startNode,weight);
    }

    @Override
    public boolean isStronglyConnected(){return super.DFS(0).length == nodeList.size();}

}
