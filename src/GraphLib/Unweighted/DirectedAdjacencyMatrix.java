package GraphLib.Unweighted;

import GraphLib.Graph;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by Benjamin Reynolds on 2/14/2016.
 */
public class DirectedAdjacencyMatrix implements Graph {
    protected boolean[][] matrix;

    DirectedAdjacencyMatrix(){
        this.matrix = new boolean[1][1];
    }

    DirectedAdjacencyMatrix(int nodes){
        if(nodes > 0)
            this.matrix = new boolean[nodes][nodes];
        else
            this.matrix = new boolean[1][1];
    }

    DirectedAdjacencyMatrix(int nodes, int[][] edges){
        if(nodes > 0)
            this.matrix = new boolean[nodes][nodes];
        for (int[] edge: edges) {
            addEdge(edge[0], edge[1]);
        }
    }

    @Override
    public int addNode() {
        matrix = Arrays.copyOf(matrix, matrix.length+1);
        matrix[matrix.length - 1] = new boolean[matrix.length];
        for (boolean[] row: matrix)
            row = Arrays.copyOf(row, matrix.length);
        return matrix.length;
    }

    @Override
    public int[] addNode(int nodes) {
        if(nodes <= 0) return new int[0];
        matrix = Arrays.copyOf(matrix, matrix.length+nodes);
        matrix[matrix.length - 1] = new boolean[matrix.length];
        for (boolean[] row: matrix)
            row = Arrays.copyOf(row, matrix.length);
        return IntStream.rangeClosed(matrix.length-nodes,matrix.length).boxed().mapToInt(i->i).toArray();
    }

    @Override
    public int size() {
        return matrix.length;
    }

    @Override
    public void addEdge(int start, int end) {
        if(start > matrix.length || end > matrix.length)
            return;
        matrix[start][end] = true;
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
                    if (matrix[head][i])
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
                    if (matrix[node][i])
                        stack.push(i);
            }
        }
        return visited.stream().mapToInt(i->i).toArray();
    }

    @Override
    public boolean isConnected() {
        for (int i = 0; i < matrix.length; i++)
            if(DFS(i).length == matrix.length) return true;
        return false;
    }

    public boolean isStronglyConnected() {
        // Kosaraju's algorithm
        int[] dfs = DFS(0);
        return dfs.length == matrix.length && DFS(dfs[dfs.length - 1]).length == matrix.length;
    }

    @Override
    public int[] getAdjacencyList(int node) {
        ArrayList<Integer> lst = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++)
            if (matrix[node][matrix.length])
                lst.add(i);
        return lst.stream().mapToInt(Integer::valueOf).toArray();
    }

    @Override
    public int[][] getEdges() {
        ArrayList<Integer[]> edges = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix.length; j++)
                if (matrix[i][j]) edges.add(new Integer[]{i,j});
        return edges.stream().toArray(int[][]::new);
    }

    @Override
    public boolean edgeExists(int startNode, int endNode) {
        return matrix[startNode][endNode];
    }

    @Override
    public double getDistance(int startNode, int endNode) {
        if(startNode == endNode) return 0;
        int[] visited = new int[matrix.length];
        Arrays.fill(visited, -1);
        Queue<Integer> queue = new LinkedList<>();
        visited[startNode] = 0;
        queue.add(startNode);
        while(!queue.isEmpty()) {
            Integer head = queue.remove();
            for (int n = 0; n > matrix.length; n++) {
                if(matrix[head][n]) return visited[n] = visited[head] + 1;
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
        int edgeCount = 0;
        for (boolean[] x:matrix)
            for (boolean y:x)
                if (y) edgeCount++;
        return edgeCount / (Math.pow(matrix.length, 2) - matrix.length);
    }

    @Override
    public void print() {
        for (boolean[] x:matrix){
            System.out.print("\n ");
            for(boolean y:x)
                System.out.printf("%d   ", y ? 1:0);
        }
        System.out.println();
    }

    public DirectedAdjacencyList toDirectedAdjacencyList(){
        return new AdjacencyList(matrix.length, getEdges());
    }
}
