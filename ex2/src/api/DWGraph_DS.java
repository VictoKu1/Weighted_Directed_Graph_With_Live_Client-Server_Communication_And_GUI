package api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph {
    HashMap<Integer,node_data> nodes;
    int num_edge;
    int mc;
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

    /**
     * return true iff (if and only if) there is an edge between node1 and node2
     *
     * @param src
     * @param dest
     * @return
     */
    public boolean hasEdge(int src, int dest) {
        if (this.getNode(src) == null || this.getNode(dest) == null || src == dest) return false;
        Node n = (Node) this.getNode(src);
        return n.hasNi(dest);
    }

    /**
     * returns the data of the edge (src,dest), null if none.
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data getEdge(int src, int dest) {
        if (nodes.get(src) == null || nodes.get(dest) == null) return null;
        return ((Node) nodes.get(src)).neighbors.get(dest);
    }

    /**
     * adds a new node to the graph with the given node_data.
     * Note: this method should run in O(1) time.
     *
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        nodes.put(n.getKey(),n);
    }

    /**
     * add a new node to the graph with the given key.
     *
     * @param key
     */
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
            if (!((Node)nodes.get(src)).hasNi(dest)) num_edge++;
            ((Node)nodes.get(src)).addNi(dest, w,true);
            ((Node)nodes.get(dest)).addNi(src, w,false);
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
        Integer[] neighbors =  ((Node)nodes.get(node_id)).getNi();
        ArrayList<edge_data> edges = new ArrayList<>();
        for (Integer i:neighbors) {
            if (i >0)
                edges.add(((Node)nodes.get(node_id)).neighbors.get(i));
            else if (i == 0)
                if(((Node)nodes.get(node_id)).neighbors.get(i).getSrc() == node_id)
                    edges.add(((Node)nodes.get(node_id)).neighbors.get(i));
        }
        return edges;
    }

    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id
     *
     * @param node_id
     * @return Collection<src.node_info>
     */
    /*
    @Override
    public Collection<node_info> getV(int node_id) {
        if (getNode(node_id) == null) return null;
        Node m = (Node) edges.get(node_id);
        ArrayList<node_info> node_list = new ArrayList<>();
        int i;
        for (Object j : m.getNi()) {
            i = (Integer) j;
            node_list.add(edges.get(i));
        }
        return node_list;
    }

     */

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
            for (int j :n.getNi()) {
                this.removeEdge(key,j);
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
        if(src < 0){
            int temp = dest;
            dest = -1*src;
            src = temp;
        }
        Node n1 = (Node) nodes.get(src);
        Node n2 = (Node) nodes.get(dest);
        edge_data edge = null;
        if (this.hasEdge(src, dest)) {
            if (n1.hasNi(dest)) {
                edge = n1.removeNode(dest);
                n2.removeNode(-1 *src);
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

    private class Node implements node_data{
        int key;
        geo_location location;
        double weight;
        String info;
        int tag;
       HashMap<Integer,edge_data> neighbors;

        public Node(int key) {
            this.key = key;
            neighbors = new HashMap<>();
        }
        /**
         * this function check if the key is a neighbor of this node
         *
         * @param key
         * @return boolean
         */

        public boolean hasNi(int key) {
            if (key == 0)
                if(neighbors.get(0) != null)
                return neighbors.get(0).getSrc() == this.key;
            return (neighbors.get(key) != null) || this.key == key;
        }

        /**
         * this function adds the given node as a neighbor to the list of neighbors
         *
         * @param key
         * @param dis
         */
        public void addNi(int key, double dis,boolean b) {
            if (!hasNi(key)) {
                if (b)
                neighbors.put(key, new Edge(this.key,key,dis));
                else if(key ==0 && neighbors.get(0) ==null)
                    neighbors.put(key, ((Node)nodes.get(0)).neighbors.get(this.key));
                else
                    neighbors.put(-1*key, ((Node)nodes.get(key)).neighbors.get(this.key));
            }
        }

        /**
         * this function removes the given node from the neighbors list
         *
         * @param key
         */
        public edge_data removeNode(int key) {
            edge_data edge = neighbors.get(key);
            neighbors.remove(key);
            return edge;
        }

        /**
         * this function return the dist between the node to the
         * node represented by the given node_id
         * in case there are not neighbors return -1
         *
         * @param key
         * @return
         */
        public double getDist(int key) {
            if (this.key == key) return 0;
            if (key == 0)
                if (this.hasNi(key))
                    if(neighbors.get(key).getSrc() ==0)
                        return -1;
            if (this.hasNi(key)) return neighbors.get(key).getDest();
            return -1;
        }

        /**
         * return an array of all the node's neighbors node_ids
         *
         * @return
         */
        public Integer[] getNi() {
            return nodes.keySet().toArray(Integer[] :: new );
        }
        /**
         * Returns the key (id) associated with this node.
         *
         * @return
         */
        @Override
        public int getKey() {
            return key;
        }

        /**
         * Returns the location of this node, if
         * none return null.
         *
         * @return
         */
        @Override
        public geo_location getLocation() {
            return location;
        }

        /**
         * Allows changing this node's location.
         *
         * @param p - new new location  (position) of this node.
         */
        @Override
        public void setLocation(geo_location p) {
            this.location = p;

        }

        /**
         * Returns the weight associated with this node.
         *
         * @return
         */
        @Override
        public double getWeight() {
            return weight;
        }

        /**
         * Allows changing this node's weight.
         *
         * @param w - the new weight
         */
        @Override
        public void setWeight(double w) {
            this.weight = w;

        }

        /**
         * Returns the remark (meta data) associated with this node.
         *
         * @return
         */
        @Override
        public String getInfo() {
            return info;
        }

        /**
         * Allows changing the remark (meta data) associated with this node.
         *
         * @param s
         */
        @Override
        public void setInfo(String s) {
            this.info = s;

        }

        /**
         * Temporal data (aka color: e,g, white, gray, black)
         * which can be used be algorithms
         *
         * @return
         */
        @Override
        public int getTag() {
            return tag;
        }

        /**
         * Allows setting the "tag" value for temporal marking an node - common
         * practice for marking by algorithms.
         *
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(int t) {
            this.tag = t;

        }
    }
    private class Edge implements edge_data{
        Node src;
        Node Dest;
        String info;
        double weight;
        int tag;

        public Edge(int key_s, int key_d, double weight) {
            this.src = new Node(key_s);
            Dest = new Node(key_d);
            this.weight = weight;
        }

        /**
         * The id of the source node of this edge.
         *
         * @return
         */
        @Override
        public int getSrc() {
            return src.getKey();
        }

        /**
         * The id of the destination node of this edge
         *
         * @return
         */
        @Override
        public int getDest() {
            return src.getKey();
        }

        /**
         * @return the weight of this edge (positive value).
         */
        @Override
        public double getWeight() {
            return weight;
        }

        /**
         * Returns the remark (meta data) associated with this edge.
         *
         * @return
         */
        @Override
        public String getInfo() {
            return info;
        }

        /**
         * Allows changing the remark (meta data) associated with this edge.
         *
         * @param s
         */
        @Override
        public void setInfo(String s) {
            this.info = s;

        }

        /**
         * Temporal data (aka color: e,g, white, gray, black)
         * which can be used be algorithms
         *
         * @return
         */
        @Override
        public int getTag() {
            return tag;
        }

        /**
         * This method allows setting the "tag" value for temporal marking an edge - common
         * practice for marking by algorithms.
         *
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(int t) {
            this.tag = t;
        }
    }

}


