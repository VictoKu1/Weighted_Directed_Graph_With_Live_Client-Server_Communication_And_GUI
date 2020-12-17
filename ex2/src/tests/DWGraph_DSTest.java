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
            n = new Node(i);
            n.setLocation(new Location());
            graph.addNode(n);
        }
    }

    @Test
    void getNode() {
        Node n = new Node(10);
        graph.addNode(n);
        assertEquals(n, graph.getNode(10));
        assertEquals(null, graph.getNode(11));
    }

    @Test
    void connect() {
        assert 0 == graph.edgeSize();
        graph.connect(0, 100, 100);
        assert 0 == graph.edgeSize();
        graph.connect(100, 101, 100);
        assert 0 == graph.edgeSize();
        graph.connect(100, 0, 100);
        assert 0 == graph.edgeSize();
        graph.connect(0, 0, 100);
        assert 0 == graph.edgeSize();
        graph.connect(0, 1, 100);
        assert 1 == graph.edgeSize();
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
        graph.connect(0, 1, 1);
        graph.connect(0, 2, 2);
        graph.connect(0, 3, 3);
        graph.connect(0, 4, 4);
        Collection<edge_data> edges = graph.getE(0);
        assert edges.size() == graph.edgeSize();
        Edge[] ac_edges = new Edge[4];
        Edge edge;
        for (int i = 0; i < ac_edges.length; i++) {
            edge = new Edge(graph.getNode(0), graph.getNode(i + 1), i + 1);
            ac_edges[i] = edge;
        }
        for (edge_data edgedata : edges) {
            assert Objects.equals(ac_edges[edgedata.getDest() - 1], edgedata);
        }
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

    @Test
    void equals() {
        DWGraph_DS graph2 = new DWGraph_DS();
        node_data n;
        for (int i = 0; i < 10; i++) {
            n = new Node(i);
            n.setLocation(new Location());
            graph2.addNode(n);
        }
        graph.connect(0, 1, 10);
        graph.connect(0, 3, 20);
        graph.connect(7, 5, 9);
        graph.connect(4, 3, 6);
        graph2.connect(0, 1, 10);
        graph2.connect(0, 3, 20);
        graph2.connect(7, 5, 9);
        graph2.connect(4, 3, 6);
        assert graph.equals(graph2);
    }

}

