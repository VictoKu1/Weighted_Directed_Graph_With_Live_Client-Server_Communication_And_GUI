package api;

import java.util.ArrayList;
import java.util.HashMap;

 public class Node implements node_data{
    int key;
    geo_location location;
    double weight;
    String info;
    int tag;
    HashMap<Integer,edge_data> edges;
    ArrayList<Integer> neighbors;

    public Node(int key) {
        this.key = key;
        edges = new HashMap<>();
        neighbors = new ArrayList<>();
    }
    /**
     * this function check if the key is a neighbor of this node
     *
     * @param key
     * @return boolean
     */

    public boolean hasNi(int key) {
        return (edges.get(key) != null) || this.key == key;
    }

    /**
     * this function adds the given node as a neighbor to the list of neighbors
     *
     * @param key
     * @param dis
     */
    public void addNi(int key, double dis,boolean b) {
        if (b) {
                edges.put(key, new Edge(this.key,key,dis));
        }
        else
            neighbors.add(key);

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
    public void removeNi(int key){
        neighbors.remove(key);
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
        if (this.hasNi(key)) return edges.get(key).getDest();
        return -1;
    }

    /**
     * return an array of all the node's neighbors node_ids
     *
     * @return
     */
    public Integer[] getNi() {
        return edges.keySet().toArray(Integer[] :: new );
    }
     public Integer[] getnNi() {
         return neighbors.toArray(Integer[] :: new );
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