package api;


import java.util.Objects;

public class Node implements node_data {
    int key;
    geo_location location;
    double weight;
    String info;
    int tag;
    private node_data parent;

    public Node(int key) {
        this.key = key;
        this.tag = -1;
        this.info = "";
    }

    /*
     *  Returns this node's parent parameter .
     */
    public node_data getParent() {
        return this.parent;
    }

    /*
     *  Sets this node's parent parameter to the given node_info type value .
     */
    public void setParent(node_data newUpdatedParnet) {
        this.parent = newUpdatedParnet;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return key == node.key &&
                Double.compare(node.weight, weight) == 0 &&
                tag == node.tag &&
                location.equals(node.location) &&
                info.equals(node.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, location, weight, info, tag);
    }

    @Override
    public String toString() {
        return "Node{" +
                "key=" + key +
                ", location=" + location.toString() +
                ", weight=" + weight +
                ", info='" + info + '\'' +
                ", tag=" + tag +
                '}';
    }
}