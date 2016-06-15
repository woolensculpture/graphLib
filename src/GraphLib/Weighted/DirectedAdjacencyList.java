package GraphLib.Weighted;

import java.util.*;

/**
 * Created by Benjamin Reynolds on 2/21/2016.
 */
public class DirectedAdjacencyList implements WeightedGraph {

    protected ArrayList<ArrayList<Integer[]>> nodeList = new ArrayList<>();

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
            addWeightedEdge(edge[0],edge[1],edge[2]);
    }

    @Override
    public int addNode() {
        nodeList.add(new ArrayList<>());
        return nodeList.size();
    }

    @Override
    public int[] addNode(int nodes) {
        int[] newNodes = new int[nodes];
        for(int i = 0; i < nodes; newNodes[i] = addNode(),i++);
        return newNodes;
    }

    @Override
    public int size() {
        return nodeList.size();
    }

    @Override
    public void addEdge(int start, int end) {
        ArrayList<Integer[]> x  = nodeList.get(start);
        if(!x.parallelStream().anyMatch(i->i[0] == end)) {
            for (int i = 0; i < x.size(); i++) {
                if(x.get(i)[0] > end) {
                    x.add(i, new Integer[]{end,1});
                    return;
                }
            }
            x.add(new Integer[]{end, 1});
        }
    }

    @Override
    public int getEdgeWeight(int startNode, int endNode) {
        return nodeList.get(startNode).parallelStream().filter(i -> i[0] == endNode).findFirst().get()[1];
    }

    @Override
    public void addWeightedEdge(int start, int end, int weight){
        ArrayList<Integer[]> x  = nodeList.get(start);
        if(!x.parallelStream().anyMatch(i->i[0] == end)) {
            for (int i = 0; i < x.size(); i++) {
                if(x.get(i)[0] > end) {
                    x.add(i, new Integer[]{end, weight});
                    return;
                }
            }
            x.add(new Integer[]{end, weight});
        }
    }

    //TODO
    @Override
    public int[] shortestPaths(int startNode) {
        return new int[0];
    }

    //TODO
    @Override
    public WeightedGraph augmentedPaths(int source, int sink) {
        return null;
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
                nodeList.get(head).stream().mapToInt(i -> i[0]).forEachOrdered(queue::add);
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
                nodeList.get(node).stream().mapToInt(i->i[0]).forEachOrdered(stack::push);
            }
        }
        return visited.stream().mapToInt(i->i).toArray();
    }

    @Override
    public boolean isConnected() {
        return this.DFS(0).length == nodeList.size();
    }

    @Override
    public boolean isStronglyConnected() {
        // Kosaraju's algorithm
        int[] dfs = DFS(0);
        return dfs.length == nodeList.size() && DFS(dfs[dfs.length - 1]).length == nodeList.size();
    }

    @Override
    public int[] getAdjacencyList(int node) {
        return nodeList.get(node).parallelStream().mapToInt(i->i[0]).toArray();
    }

    @Override
    public int[][] getEdges() {
        ArrayList<int[]> edgeList = new ArrayList<>();
        for (int i = 0; i < nodeList.size(); i++){
            for (Integer[] node :nodeList.get(i))
                edgeList.add(new int[]{i,node[0]});
        }
        return edgeList.toArray(new int[edgeList.size()][2]);
    }

    @Override
    public boolean edgeExists(int startNode, int endNode) {
        return nodeList.get(startNode).parallelStream().anyMatch(i -> i[0] == endNode);
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
            for (Integer[] n : nodeList.get(head)) {
                if(n[0] == endNode)
                    return visited[n[0]] = visited[head] + 1;
                else if (visited[n[0]] == -1) {
                    visited[n[0]] = visited[head] + 1;
                    queue.add(n[0]);
                }
            }
        }
        return visited[endNode];
    }

    @Override
    public double getDensity() {
        return nodeList.parallelStream().mapToInt(ArrayList::size).sum() / (float)Math.pow(nodeList.size(), 2);
    }

    @Override
    public void print() {
        for (int i = 0; i < nodeList.size(); i++) {
            System.out.printf("%d-> ", i);
            nodeList.get(i).forEach(n -> System.out.printf(" %d(%d)", n[0], n[1]));
            System.out.println();
        }
    }

    public DirectedAdjacencyMatrix toWeightedDirectedAdjacencyMatrix(){
        return new DirectedAdjacencyMatrix(nodeList.size(), getEdges());
    }

}
