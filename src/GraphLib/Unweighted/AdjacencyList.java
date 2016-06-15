package GraphLib.Unweighted;

import java.util.ArrayList;


/**
 * Created by Benjamin Reynolds on 2/19/2016.
 */
public class AdjacencyList extends DirectedAdjacencyList {

    AdjacencyList() {super();}
    AdjacencyList(int nodes) {super(nodes);}

    AdjacencyList(int nodes, int[][] edges) {super(nodes, edges);}

    @Override
    public void addEdge(int start, int end) {
        if(start > nodeList.size() && end > nodeList.size()) {
            ArrayList<Integer> x = nodeList.get(start);
            ArrayList<Integer> y = nodeList.get(end);
            if (!x.contains(end))
                x.add(end);
            if(!y.contains(start)){
                y.add(start);
            }
        }
    }

    public boolean isConnected() {
           return DFS(0).length == nodeList.size();
    }
}
