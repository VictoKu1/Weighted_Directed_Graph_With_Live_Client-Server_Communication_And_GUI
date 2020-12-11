package api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;


public class DWGraph_DS implements directed_weighted_graph {
    private HashMap<Integer, node_data> nodes;
    private HashMap<Integer, Node_buffer> nb_list;
    private boolean transpose;
    private int num_edge;
    private int mc;

    public DWGraph_DS() {
        nodes = new HashMap<>();
        nb_list = new HashMap<>();
        num_edge = 0;
        mc = 0;
        transpose = false;
    }

    /**
     * transpose the graph
     */
    public void Transpose() {
        transpose = !transpose;
    }

    public boolean getTranspose() {
        return this.transpose;
    }
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
        Node_buffer n = nb_list.get(src);
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
        return nb_list.get(src).edges.get(dest);
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
        Node_buffer nb = new Node_buffer(n);
        nb_list.put(n.getKey(), nb);
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
        if (w < 0) return;
        if (this.getNode(src) != null && this.getNode(dest) != null) {
            if (!(nb_list.get(src).hasNi(dest))) num_edge++;
            nb_list.get(src).addNi(nodes.get(dest), w, true);
            nb_list.get(dest).addNi(nodes.get(src), w, false);
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
        Node_buffer n = nb_list.get(node_id);
        ArrayList<edge_data> edges = new ArrayList<>();
        Integer[] neighbors;
        if (!transpose) {
            neighbors = n.getNi();
            for (Integer i : neighbors) {
                edges.add(n.edges.get(i));
            }
        } else {
            neighbors = n.getnNi();
            for (Integer i : neighbors) {
                edges.add(nb_list.get(i).edges.get(n.node.getKey()));
            }
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
            Node_buffer n = nb_list.get(key);
            for (int j : n.getNi()) {
                this.removeEdge(key, j);
            }
            for (int j : n.getnNi()) {
                this.removeEdge(j, key);
            }
            mc++;
            nodes.remove(key);
            nb_list.remove(key);
            return n.node;
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
        Node_buffer n1 = nb_list.get(src);
        Node_buffer n2 = nb_list.get(src);
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

    @Override
    public String toString() {
        return "DWGraph_DS{" +
                //"nodes=" + Node_buffer.nodes.values() +
                ", num_edge=" + num_edge +
                ", mc=" + mc +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWGraph_DS that = (DWGraph_DS) o;
        boolean b = nodes_equals(that.nodes, that.nb_list);
        return num_edge == that.num_edge &&
                mc == that.mc &&
                b;
    }

    private boolean nodes_equals(HashMap<Integer, node_data> other, HashMap<Integer, Node_buffer> nb) {
        boolean b = true;
        for (int key : nodes.keySet()) {
            b &= (other.containsKey(key));
            if (!b)
                return false;
            b &= (nb_list.get(key).equals(nb.get(key)));
        }
        return b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes, transpose, num_edge, mc);
    }

    private static class Node_buffer {
        public Node node;
        public HashMap<Integer, Edge> edges;
        public ArrayList<Integer> neighbors;

        public Node_buffer(node_data node) {
            this.node = (Node) node;
            this.edges = new HashMap<>();
            this.neighbors = new ArrayList<>();
        }

        /**
         * this function check if the key is a neighbor of this node
         *
         * @param key
         * @return boolean
         */

        public boolean hasNi(int key) {
            return (edges.get(key) != null) || node.getKey() == key;
        }

        /**
         * this function adds the given node as a neighbor to the list of neighbors
         *
         * @param n2
         * @param dis
         */
        public void addNi(node_data n2, double dis, boolean b) {
            if (b) {
                edges.put(n2.getKey(), new Edge(node, n2, dis));
            } else
                neighbors.add(n2.getKey());
        }

        /**
         * this function removes the given node from the neighbors list
         *
         * @param key
         */
        public edge_data removeNode(int key) {
            edge_data edge = edges.get(key);
            edges.remove(key);
            return edge;
        }

        public void removeNi(int key) {
            neighbors.remove((Object) key);
        }

        /**
         * return an array of all the node's neighbors node_ids
         *
         * @return
         */
        public Integer[] getNi() {
            return edges.keySet().toArray(Integer[]::new);
        }

        public Integer[] getnNi() {
            return neighbors.toArray(Integer[]::new);
        }

        /**
         * Returns the key (id) associated with this node.
         *
         * @return
         */
        public int getKey() {
            return node.getKey();
        }

        @Override
        public String toString() {
            return "Node_buffer{" +
                    "node=" + node.getKey() +
                    ", edges=" + edges +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node_buffer that = (Node_buffer) o;
            return node.equals(that.node) &&
                    edges_equals(that.edges) &&
                    neighbors.equals(that.neighbors);
        }

        private boolean edges_equals(HashMap<Integer, Edge> other) {
            boolean b = true;
            for (int key : edges.keySet()) {
                b &= (other.containsKey(key));
                if (!b)
                    return false;
                b &= (edges.get(key).equals(other.get(key)));
            }
            return b;
        }

        @Override
        public int hashCode() {
            return Objects.hash(node, edges, neighbors);
        }
    }

}


