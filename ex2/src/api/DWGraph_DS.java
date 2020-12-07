package api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph {
    HashMap<Integer, node_data> nodes;
    int num_edge;
    int mc;

    public DWGraph_DS() {
        nodes = new HashMap<>();
        num_edge = 0;
        mc = 0;
    }
    /**
     * resets the graph for the algorithms
     */
    /*
    public void reset() {
        Object[] o = edges.values().toArray();
        node_info n;
        for (Object j : o) {
            n = (node_info) j;
            double d = Integer.MAX_VALUE;
            n.setTag(d);
            n.setInfo("nv");
        }
    }

     */

    /**
     * return the node_data by the node_id,
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_data getNode(int key) {
        return nodes.get(key);
    }

    private boolean hasEdge(int src, int dest) {
        if (this.getNode(src) == null || this.getNode(dest) == null || src == dest) return false;
        Node n = (Node) this.getNode(src);
        return n.hasNi(dest);
    }

    /**
     * returns the data of the edge (src,dest), null if none.
     *
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data getEdge(int src, int dest) {
        if (nodes.get(src) == null || nodes.get(dest) == null) return null;
        return ((Node) nodes.get(src)).edges.get(dest);
    }

    /**
     * adds a new node to the graph with the given node_data.
     * Note: this method should run in O(1) time.
     *
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        nodes.put(n.getKey(), n);
        mc++;
    }
    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     *
     * @param src
     * @param dest
     * @param w
     */
    @Override
    public void connect(int src, int dest, double w) {
        if (this.getNode(src) != null && this.getNode(dest) != null) {
            if (!((Node) nodes.get(src)).hasNi(dest)) num_edge++;
            ((Node) nodes.get(src)).addNi(dest, w, true);
            ((Node) nodes.get(dest)).addNi(src, w, false);
            mc++;
        }
    }

    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     *
     * @return Collection<src.node_info>
     */
    @Override
    public Collection<node_data> getV() {
        return nodes.values();
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the edges getting out of
     * the given node (all the edges starting (source) at the given node).
     * Note: this method should run in O(k) time, k being the collection size.
     *
     * @param node_id
     * @return Collection<edge_data>
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        Node n = ((Node) nodes.get(node_id));
        Integer[] neighbors = n.getNi();
        ArrayList<edge_data> edges = new ArrayList<>();
        for (Integer i : neighbors) {
            edges.add(n.edges.get(i));
        }
        return edges;
    }
    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_data removeNode(int key) {
        if (this.getNode(key) != null) {
            Node n = (Node) nodes.get(key);
            for (int j : n.getNi()) {
                this.removeEdge(key,j);
            }
            for (int j:n.getnNi()) {
                this.removeEdge(j,key);
            }
            mc++;
            nodes.remove(key);
            return n;
        }
        return null;
    }

    /**
     * Delete the edge from the graph,
     *
     * @param src
     * @param dest
     */
    @Override
    public edge_data removeEdge(int src, int dest) {
        Node n1 = (Node) nodes.get(src);
        Node n2 = (Node) nodes.get(dest);
        edge_data edge = null;
        if (this.hasEdge(src, dest)) {
            if (n1.hasNi(dest)) {
                edge = n1.removeNode(dest);
                n2.removeNi(src);
                mc++;
                num_edge--;
            }
        }
        return edge;
    }

    /**
     * return the number of vertices (nodes) in the graph.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return nodes.size();
    }

    /**
     * return the number of edges (undirected graph).
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return num_edge;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     *
     * @return
     */
    @Override
    public int getMC() {
        return mc;
    }


}


