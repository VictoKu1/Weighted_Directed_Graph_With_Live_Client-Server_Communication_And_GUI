package tests;

import api.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {
    DWGraph_DS graph;

    @BeforeEach
    void setup() {
        graph = new DWGraph_DS();
        node_data n;
        for (int i = 0; i < 10; i++) {
            n = new node_test(i);
            graph.addNode(n);
        }
    }

    @Test
    void getNode() {
        node_test n = new node_test(10);
        graph.addNode(n);
        assertEquals(n, graph.getNode(10));
        assertEquals(null, graph.getNode(11));
    }

    @Test
    void connect() {
    }

    @Test
    void getEdge() {
        graph.connect(0, 9, 1);
        edge_data e = graph.getEdge(0, 9);
        assertEquals(0, e.getSrc());
        assertEquals(9, e.getDest());
        assertEquals(1, e.getWeight());
        graph.connect(0, 9, 1);
        e = graph.getEdge(9, 0);
        assertEquals(null, e);
    }

    @Test
    void getV() {
        Collection<node_data> nodes = graph.getV();
        assertEquals(10, nodes.size());
        Integer[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<Integer> list = Arrays.asList(arr);
        HashSet<Integer> set = new HashSet<>(list);
        for (node_data n : nodes) {
            set.remove(n.getKey());
        }
        assertEquals(0, set.size());
    }

    @Test
    void getE() {
    }

    @Test
    void removeNode() {
        assertNotEquals(null, graph.getNode(9));
        graph.connect(0, 9, 10);
        graph.connect(9, 7, 8);
        graph.removeNode(9);
        assertEquals(null, graph.getNode(9));
        assertEquals(null, graph.getEdge(9, 7));
        assertEquals(null, graph.getEdge(0, 9));
    }

    @Test
    void removeEdge() {
        graph.connect(0, 9, 1);
        graph.connect(9, 0, 1);
        edge_data e = graph.getEdge(0, 9);
        assertNotEquals(null, e);
        graph.removeEdge(0, 9);
        e = graph.getEdge(0, 9);
        assertEquals(null, e);
        e = graph.getEdge(9, 0);
        assertNotEquals(null, e);
    }

    @Test
    void nodeSize() {
        assertEquals(10, graph.nodeSize());
        graph.removeNode(9);
        assertEquals(9, graph.nodeSize());

    }

    @Test
    void edgeSize() {
        assertEquals(0, graph.edgeSize());
        graph.connect(0, 1, 10);
        assertEquals(1, graph.edgeSize());

    }

    @Test
    void getMC() {
        assertEquals(10, graph.getMC());
    }

    private class node_test implements node_data {
        int key;
        geo_location location;
        double weight;
        String info;
        int tag;

        public node_test(int key) {
            this.key = key;
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
    }
}
