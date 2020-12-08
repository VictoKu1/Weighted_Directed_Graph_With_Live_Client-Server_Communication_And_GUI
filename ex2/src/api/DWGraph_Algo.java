package api;

import java.util.Iterator;
import java.util.List;

public class DWGraph_Algo implements dw_graph_algorithms {
    private directed_weighted_graph g;

    /*
     *   Constructor method which initializes as new WGraph_DS object the directed_weighted_graph g parameter .
     */
    public DWGraph_Algo() {
        g = new DWGraph_DS();
    }

    /**
     * Method which make the parameter this.g to point on the inputted graph g .
     */
    @Override
    public void init(directed_weighted_graph g) {
        this.g = g;
    }

    /**
     * Method which returns a pointer to the this.g graph this class is working on .
     */
    @Override
    public directed_weighted_graph getGraph() {
        return this.g;
    }

    /**
     * Compute a deep copy of this weighted graph.
     */
    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph g1 = new DWGraph_DS();
        copyNodes(g1);
        copyEdges(g1);
        return g1;
    }

    private void copyNodes(directed_weighted_graph target) {
        Iterator<node_data> itr = this.g.getV().iterator();
        while (itr.hasNext()) {
            node_data fromNode = itr.next();
            node_data copiedNode = copyNode(fromNode);
            target.addNode(copiedNode);
        }
    }

    private node_data copyNode(node_data fromNode) {
        node_data copiedNode = new Node(fromNode.getKey());
        copiedNode.setInfo(fromNode.getInfo());
        copiedNode.setLocation(fromNode.getLocation());
        copiedNode.setTag(fromNode.getTag());
        copiedNode.setWeight(fromNode.getWeight());
        return copiedNode;
    }

    private void copyEdges(directed_weighted_graph target) {
        Iterator<node_data> itr = this.g.getV().iterator();
        while (itr.hasNext()) {
            Iterator<edge_data> itr1 = this.g.getE(itr.next().getKey()).iterator();
            while (itr1.hasNext()) {
                edge_data fromEdge = itr1.next();
                target.connect(fromEdge.getSrc(), fromEdge.getDest(), fromEdge.getWeight());
                target.getEdge(fromEdge.getSrc(), fromEdge.getDest()).setInfo(fromEdge.getInfo());
                target.getEdge(fromEdge.getSrc(), fromEdge.getDest()).setTag(fromEdge.getTag());
            }
        }
    }

    /**
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
     *
     * @return
     */
    @Override
    public boolean isConnected() {
        return false;
    }

    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        return null;
    }

    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        return false;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name of JSON file
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        return false;
    }
}
