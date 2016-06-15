package GraphLib.Unweighted;

import GraphLib.Graph;

import java.util.*;

/**
 * Created by Benjamin Reynolds on 2/14/2016.
 */
public class DirectedAdjacencyList implements Graph {

    protected ArrayList<ArrayList<Integer>> nodeList = new ArrayList<>();

    DirectedAdjacencyList(){
        nodeList.add(new ArrayList<>());
    }

    DirectedAdjacencyList(int nodes){
        for (int i = 0; i < nodes; i++)
            nodeList.add(new ArrayList<>());
    }

    DirectedAdjacencyList(int nodes, int[][] edges){
        for (int i = 0; i < nodes; i++)
            nodeList.add(new ArrayList<>());
        for (int[] edge:edges)
            addEdge(edge[0],edge[1]);
    }


    @Override
    public int addNode() {
        nodeList.add(new ArrayList<>());
        return nodeList.size();
    }


    @Override
    public int[] addNode(int nodes) {
        int[] newNodes = new int[nodes];
        for(int i = 0; i < nodes; i++)
            newNodes[i] = addNode();
        return newNodes;
    }

    @Override
    public int size() {
        return nodeList.size();
    }

    @Override
    public void addEdge(int start, int end) {
        ArrayList<Integer> x  = nodeList.get(start);
        if(!x.contains(end)) {
            for (int i = 0; i < x.size(); i++) {
                if(x.get(i) > end) {
                    x.add(i, end);
                    return;
                }
            }
            x.add(end);
        }
    }

    @Override
    public int[] BFS(int startNode) {
        ArrayList<Integer> visited = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startNode);
        while(!queue.isEmpty()) {
            Integer head = queue.remove();
            if(!visited.contains(head)) {
                visited.add(head);
                queue.addAll(nodeList.get(head));
            }
        }
        return visited.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int[] DFS(int startNode) {
        ArrayList<Integer> visited = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        stack.push(startNode);
        while(!stack.empty()){
            Integer node = stack.pop();
            if(!visited.contains(node)){
                visited.add(node);
                nodeList.get(node).stream().forEach(stack::push);
            }
        }
        return visited.stream().mapToInt(i->i).toArray();
    }

    @Override
    public boolean isConnected() {
        for (int i = 0; i < nodeList.size(); i++)
            if(DFS(i).length == nodeList.size()) return true;
        return false;
    }

    @Override
    public boolean isStronglyConnected() {
        // Kosaraju's algorithm
        int[] dfs = DFS(0);
        if(dfs.length != nodeList.size())
            return false;
        return DFS(dfs[dfs.length-1]).length == nodeList.size();
    }

    @Override
    public int[] getAdjacencyList(int node) {
        return nodeList.get(node).parallelStream().mapToInt(i->i).toArray();
    }

    @Override
    public int[][] getEdges() {
        ArrayList<int[]> edgeList = new ArrayList<>();
        for (int i = 0; i < nodeList.size(); i++){
            for (int node :nodeList.get(i))
                edgeList.add(new int[]{i,node});
        }
        return edgeList.toArray(new int[edgeList.size()][2]);
    }

    @Override
    public boolean edgeExists(int startNode, int endNode) {
        return nodeList.get(startNode).contains(endNode);
    }

    @Override
    public double getDistance(int startNode, int endNode) {
        if(startNode == endNode)
            return 0;
        int[] visited = new int[nodeList.size()];
        Arrays.fill(visited, -1);
        Queue<Integer> queue = new LinkedList<>();
        visited[startNode] = 0;
        queue.add(startNode);
        while(!queue.isEmpty()) {
            Integer head = queue.remove();
            for (int n : nodeList.get(head)) {
                if(n == endNode)
                    return visited[n] = visited[head] + 1;
                else if (visited[n] == -1) {
                    visited[n] = visited[head] + 1;
                    queue.add(n);
                }
            }
        }
        return visited[endNode];
    }

    @Override
    public double getDensity() {
        return nodeList.stream().parallel().flatMapToInt( i -> i.stream().mapToInt(r->r)).count()
                / (Math.pow(nodeList.size(),2) - nodeList.size());
    }

    @Override
    public void print() {
        for (int i = 0; i < nodeList.size(); i++){
            System.out.printf("%d-> ",i);
            for(Integer y:nodeList.get(i))
                System.out.printf(" %d", y);
            System.out.println();
        }
    }

    public DirectedAdjacencyMatrix toAdjacencyMatrix(){
        return new DirectedAdjacencyMatrix(nodeList.size(), getEdges());
    }

}
