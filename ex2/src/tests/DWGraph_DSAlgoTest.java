package tests;

import api.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class DWGraph_DSAlgoTest {
    dw_graph_algorithms ag;
    DWGraph_DS graph_ds;

    @BeforeEach
    void setup() {
        graph_ds = new DWGraph_DS();
        for (int i = 0; i < 5; i++) {
            node_data n = new Node(i);
            graph_ds.addNode(n);
        }
        graph_ds.connect(0, 1, 1);
        graph_ds.connect(0, 2, 2);
        graph_ds.connect(1, 3, 3);
        graph_ds.connect(2, 4, 1);
        graph_ds.connect(4, 3, 1);
        graph_ds.connect(3, 0, 1);
    }

    @Test
    void getGraph() {
        assertEquals(null, ag.getGraph());
        ag.init(graph_ds);
        assertEquals(graph_ds, ag.getGraph());
    }

    @Test
    void copy() {
        ag.init(graph_ds);
        assertEquals(graph_ds, ag.copy());
    }

    @Test
    void isConnected() {
        ag.init(graph_ds);
        assertEquals(true, ag.isConnected());
        graph_ds.removeEdge(3, 0);
        assertEquals(false, ag.isConnected());
    }

    @Test
    void shortestPathDist() {
        ag.init(graph_ds);
        assertEquals(3, ag.shortestPathDist(0, 3));
        assertEquals(-1, ag.shortestPathDist(0, 100));
        assertEquals(-1, ag.shortestPathDist(100, 100));
        assertEquals(-1, ag.shortestPathDist(101, 100));
        assertEquals(-1, ag.shortestPathDist(101, 0));
    }

    @Test
    void shortestPath() {
        ag.init(graph_ds);
        //assertEquals(,ag.shortestPath(0,3));
        assertEquals(null, ag.shortestPath(0, 100));
        assertEquals(null, ag.shortestPath(100, 100));
        assertEquals(null, ag.shortestPath(101, 100));
        assertEquals(null, ag.shortestPath(101, 0));
    }

    @Test
    void save() {
        ag.init(graph_ds);
        assert ag.save("test.json");
    }

    @Test
    void load() {
        ag.init(graph_ds);
        assert ag.load("test.json");
        assertEquals(graph_ds, ag.getGraph());
    }
}
