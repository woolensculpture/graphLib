package GraphLib.Weighted;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by benjamin on 2/21/2016.
 */
public class DirectedAdjacencyMatrix implements WeightedGraph {
    protected int[][] matrix;

    DirectedAdjacencyMatrix(){
        this.matrix = new int[1][1];
    }

    DirectedAdjacencyMatrix(int nodes){
        this.matrix = new int[nodes][nodes];
    }

    DirectedAdjacencyMatrix(int nodes, int[][] edges){
        this.matrix = new int[nodes][nodes];
        for (int[] edge: edges) {
            if(edge.length == 3){
                addWeightedEdge(edge[0], edge[1], edge[2]);
            }
            addEdge(edge[0], edge[1]);
        }
    }

    @Override
    public int addNode() {
        matrix = Arrays.copyOf(matrix, matrix.length+1);
        matrix[matrix.length - 1] = new int[matrix.length];
        for (int[] row: matrix)
            row = Arrays.copyOf(row, matrix.length);
        return matrix.length;
    }

    @Override
    public int[] addNode(int nodes) {
        matrix = Arrays.copyOf(matrix, matrix.length+nodes);
        matrix[matrix.length - 1] = new int[matrix.length];
        for (int[] row: matrix)
            row = Arrays.copyOf(row, matrix.length);
        return IntStream.rangeClosed(matrix.length-nodes,matrix.length).boxed().mapToInt(i->i).toArray();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void addEdge(int start, int end) {
        if(start > matrix.length || end > matrix.length)
            return;
        matrix[start][end] = 1;
    }

    @Override
    public int getEdgeWeight(int startNode, int endNode) {
        return matrix[startNode][endNode];
    }

    public void addWeightedEdge(int start, int end, int weight){
        if(start > matrix.length || end > matrix.length)
            return;
        matrix[start][end] = weight;
    }

    @Override
    public int[] shortestPaths(int startNode) {
        return new int[0];
    }

    @Override
    public WeightedGraph augmentedPaths(int source, int sink) {
        return null;
    }

    @Override
    public int[] BFS(int start) {
        ArrayList<Integer> visited = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        while(!queue.isEmpty()) {
            Integer head = queue.remove();
            if(!visited.contains(head)) {
                visited.add(head);
                for (int i = matrix.length; i >= 0; i--)
                    if (matrix[head][i] != 0)
                        queue.add(i);
            }
        }
        return visited.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int[] DFS(int start) {
        ArrayList<Integer> visited = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        stack.push(start);
        while(!stack.empty()){
            Integer node = stack.pop();
            if(!visited.contains(node)){
                visited.add(node);
                for (int i = matrix.length; i >= 0; i--)
                    if (matrix[node][i] != 0)
                        stack.push(i);
            }
        }
        return visited.stream().mapToInt(i->i).toArray();
    }

    @Override
    public boolean isConnected() {
        return DFS(0).length == matrix.length;
    }

    @Override
    public boolean isStronglyConnected() {
        // Kosaraju's algorithm
        int[] dfs = DFS(0);
        if(dfs.length != matrix.length)
            return false;
        return DFS(dfs[dfs.length-1]).length == matrix.length;

    }

    @Override
    public int[] getAdjacencyList(int node) {
        ArrayList<Integer> lst = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++)
            if (matrix[node][matrix.length] != 0)
                lst.add(i);
        return lst.stream().mapToInt(Integer::valueOf).toArray();
    }

    @Override
    public int[][] getEdges() {
        LinkedList<int[]> edges = new LinkedList<>();
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix.length; j++)
                if (matrix[i][j] != 0)
                    edges.add(new int[]{i,j});
        return edges.toArray(new int[edges.size()][]);
    }

    @Override
    public boolean edgeExists(int startNode, int endNode) {
        return 0 != matrix[startNode][endNode];
    }

    @Override
    public double getDistance(int startNode, int endNode) {
        if(startNode == endNode)
            return 0;
        int[] visited = new int[matrix.length];
        Arrays.fill(visited, -1);
        Queue<Integer> queue = new LinkedList<>();
        visited[startNode] = 0;
        queue.add(startNode);
        while(!queue.isEmpty()) {
            Integer head = queue.remove();
            for (int n=0; n > matrix.length;n++) {
                if(matrix[head][n] != 0)
                    return visited[n] = visited[head] + matrix[head][n];
                else if (visited[n] == -1) {
                    visited[n] = visited[head] + matrix[head][n];
                    queue.add(n);
                }
            }
        }
        return visited[endNode];
    }

    @Override
    public double getDensity() {
        return Arrays.stream(matrix).parallel().flatMapToInt(Arrays::stream).count() /
                (Math.pow(matrix.length,2) - matrix.length);
    }

    @Override
    public void print() {
        for (int[] x:matrix){
            System.out.print("\n ");
            for(int y:x)
                System.out.printf("%d   ", y);
        }
        System.out.println();
    }

    public DirectedAdjacencyList toWeightedDirectedAdjacencyList(){
        return new DirectedAdjacencyList(matrix.length, getEdges());
    }

}
