package GraphLib;

import GraphLib.Weighted.WeightedGraph;

import java.util.stream.IntStream;

/**
 * Created by Benjamin Reynolds on 5/30/2016.
 */
public abstract class Algorithms {

    public static int[][] MST(WeightedGraph graph){return MST(graph,0);}

    public static int[][] MST(WeightedGraph graph, int startNode) {
        //Prim's algorithm
        int N = graph.size()+1;
        int edges = 0;
        int[][] priorityQueue = new int[N][];
        int[][] MST = new int[N][];
        int[] qp = IntStream.range(0,graph.size()).toArray();
        IntStream.range(0,graph.size())
                .mapToObj(i -> new int[]{i,-1,Integer.MAX_VALUE})
                .forEach(i -> priorityQueue[i[0]+1] = i);

        priorityQueue[0][2] = 0;
        heapSort(priorityQueue, qp);

        while(N != 1){
            int node = priorityQueue[1][0];
            exch(1, N--, priorityQueue, qp);
            sink(1,N,priorityQueue,qp);
            priorityQueue[N+1] = null;
            qp[N+1] = -1;
            for (int v: graph.getAdjacencyList(node))
                if(qp[v] != -1 && graph.getEdgeWeight(node, v) < priorityQueue[qp[v]][2]){
                    priorityQueue[qp[v]][1] = node;
                    priorityQueue[qp[v]][2] = graph.getEdgeWeight(node, v);
                }
            heapSort(priorityQueue,qp);
        }
        return MST;
    }

    private static void exch(int i, int j, int[][] a, int[] lookup){
        lookup[i] = j;
        lookup[j] = i;
        int[] r = a[j];
        a[j] = a[i];
        a[i] = r;
    }
    private static void heapSort(int[][] a, int[] lookup){
        int N = a.length;
        for (int k = N/2; k >= 1; k--) sink(k, N, a, lookup);
        while (N > 1){
            lookup[1] = N;
            lookup[N] = 1;
            int[] r = a[N];
            // exchange 1, N
            a[N] = a[1];
            a[1] = r;
            sink(1, --N, a, lookup);
        }
    }

    private static void sink(int k, int N, int[][] a, int[] lookup){
        while (2*k <= N){
            int j = 2*k;
            if(j < N && a[j][2] < a[j+1][2]) j++;
            if(a[j][2] < a[k][2]) break;
            // exchange 1, N
            exch(k,j, a, lookup);
            k = j;
        }
    }

    //TODO
    public static int MaxFlow(WeightedGraph graph, int source, int sink){
        return 0;
    }

    //TODO
    public static int[][][] shortestpaths(WeightedGraph graph){
        return null;
    }
}
