package GraphLib;

/**
 * Created by Benjamin Reynolds on 2/14/2016.
 */
public interface Graph {

    /**
     * adds a node to the graph
     * @return integer representing the id of the node
     */
    int addNode();

    /**
     * adds a number of the nodes to the graph
     * @param nodes - integer representing the number of nodes to be added
     * @return int[] with the ids' of the newly created nodes
     */
    int[] addNode(int nodes);

    /**
     * gets the number of nodes int the graph
     * @return the number of nodes
     */
    int size();

    /**
     * adds a Edge between 2 nodes, throws error if either node does not exist
     * Unless specified otherwise this edge is directional
     * @param start - the start node
     * @param end - the end node
     */
    void addEdge(int start, int end);

    /**
     * performs a Breadth First Search on the graph and returns the nodes in order of their
     * discovery. Nodes with Larger id's will be visited first
     * @param start - the starting node
     * @return an integer array that represents the order of the graph
     */
    int[] BFS(int start);

    /**
     * performs a Depth first Search on the graph and returns the nodes in order of their
     * discovery.
     * @param start - the start node
     * @return an integer array that represents the order of the graph
     */
    int[] DFS(int start);

    /**
     * checks if all the nodes are connected to each other, but does not check if the
     * graph is strongly connected.
     * @return boolean of whether the graph is connected
     */
    boolean isConnected();

    /**
     * checks if graph is strongly connected, this will always be true for undirected graphs if they are connected
     * @return true if strongly connected, false otherwise
     */
    boolean isStronglyConnected();

    /**
     * returns the adjacency list of the node
     * @param node - the node which adjacency list will be returned
     * @return integer array of the nodes adjacent to the list
     */
    int[] getAdjacencyList(int node);

    /**
     * gets a array of the edges, which are 2 element integer arrays
     * @return an array of the edges
     */
    int[][] getEdges();

    /**
     * checks if an edge exists between start and end node
     * @param startNode - the start node
     * @param endNode - the end node
     * @return true if edge exists from teh start node to the end node false if otherwise
     */
    boolean edgeExists(int startNode, int endNode);

    /**
     * gets the shortest distance between 2 nodes
     * @param startNode - the starting node id
     * @param endNode - the ending node id
     * @return returns the number of edge steps if the graph is unweighted,
     *         the sum weight of the traversed edges for the weighted graph.
     *         if the end node is not reachable then the function returns -1;
     */
    double getDistance(int startNode, int endNode);

    /**
     * gets the density of the graph
     * @return a float of the density of the graph
     */
    double getDensity();

    /**
     * prints the graph's current state to the main out stream
     */
    void print();
}
