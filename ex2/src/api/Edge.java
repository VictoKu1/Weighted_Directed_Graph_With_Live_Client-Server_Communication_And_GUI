package api;

import java.util.Objects;

public class Edge implements edge_data {
    Node src;
    Node dest;
    String info;
    double weight;
    int tag;

    public Edge(node_data n1, node_data n2, double weight) {
        this.src = (Node) n1;
        dest = (Node) n2;
        this.weight = weight;
        info = "";
        tag = 0;
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
        return dest.getKey();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Double.compare(edge.weight, weight) == 0 &&
                tag == edge.tag &&
                src.equals(edge.src) &&
                dest.equals(edge.dest) &&
                info.equals(edge.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(src, dest, info, weight, tag);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "src=" + src.getKey() +
                ", dest=" + dest.getKey() +
                ", info='" + info + '\'' +
                ", weight=" + weight +
                ", tag=" + tag +
                '}';
    }
}

