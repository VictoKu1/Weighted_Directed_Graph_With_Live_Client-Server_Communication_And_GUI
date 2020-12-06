package tests;
import api.*;
import org.junit.jupiter.api.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {
    DWGraph_DS graph;
    @BeforeEach
    void setup(){
         graph= new DWGraph_DS();
         Node n;
        for (int i = 0; i < 10; i++) {
            n = new Node(i);
            graph.addNode(n);
        }
    }

    @Test
    void getNode() {
        Node n = new Node(10);
        graph.addNode(n);
        assertEquals(n,graph.getNode(10));
        assertEquals(null,graph.getNode(11));
    }
    @Test
    void connect() {
    }
    @Test
    void getEdge() {
        graph.connect(0,9,1);
        Edge e = (Edge) graph.getEdge(0,9);
        assertEquals(0,e.getSrc());
        assertEquals(9,e.getDest());
        assertEquals(1,e.getWeight());
        graph.connect(0,9,1);
        e = (Edge) graph.getEdge(9,0);
        assertEquals(null,e);
    }
    @Test
    void getV() {
        Collection<node_data> nodes = graph.getV();
        assertEquals(10,nodes.size());
        Integer[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<Integer> list =  Arrays.asList(arr);
        HashSet<Integer> set = new HashSet<>(list);
        for (node_data n:nodes) {
            set.remove(n.getKey());
        }
        assertEquals(0,set.size());
    }

    @Test
    void getE() {
    }

    @Test
    void removeNode() {
        assertNotEquals(null,graph.getNode(9));
        graph.connect(0,9,10);
        graph.connect(9,7,8);
        graph.removeNode(9);
        assertEquals(null,graph.getNode(9));
        assertEquals(null,graph.getEdge(9,7));
        assertEquals(null,graph.getEdge(0,9));
    }

    @Test
    void removeEdge() {
        graph.connect(0,9,1);
        graph.connect(9,0,1);
        Edge e = (Edge) graph.getEdge(0,9);
        assertNotEquals(null,e);
        graph.removeEdge(0,9);
        e = (Edge) graph.getEdge(0,9);
        assertEquals(null,e);
        e = (Edge) graph.getEdge(9,0);
        assertNotEquals(null,e);
    }

    @Test
    void nodeSize() {
        assertEquals(10,graph.nodeSize());
        graph.removeNode(9);
        assertEquals(9,graph.nodeSize());

    }

    @Test
    void edgeSize() {
        assertEquals(0,graph.edgeSize());
        graph.connect(0,1,10);
        assertEquals(1,graph.edgeSize());

    }

    @Test
    void getMC() {
        assertEquals(10,graph.getMC());
    }
}