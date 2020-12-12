package tests;

import api.*;
import org.junit.jupiter.api.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class DWGraph_DSAlgoTest {
    dw_graph_algorithms ag;
    DWGraph_DS graph_ds;

    @BeforeEach
    void setup() {
        ag = new DWGraph_Algo();
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
        assertEquals(new DWGraph_Algo().getGraph(), ag.getGraph());
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
        assertEquals(4, ag.shortestPathDist(0, 3));
        assertEquals(3, ag.shortestPathDist(1, 3));
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


    /*
     * Visualization of the graph that is being built in the following method: https://ibb.co/j5nLD42 .
     */
    directed_weighted_graph pathTestGraphCreator() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new Node(0));
        g.addNode(new Node(7));
        g.addNode(new Node(1));
        g.addNode(new Node(48));
        g.addNode(new Node(4));
        g.addNode(new Node(5));
        g.addNode(new Node(14));
        g.addNode(new Node(179));
        g.addNode(new Node(11));
        g.addNode(new Node(25));
        g.addNode(new Node(75));
        g.connect(0, 7, 10.5);
        g.connect(0, 1, 7.2);
        g.connect(7, 48, 2);
        g.connect(1, 48, 1);
        g.connect(0, 4, 4);
        g.connect(1, 4, 15.9);
        g.connect(1, 5, 57.1);
        g.connect(4, 25, 1.07);
        g.connect(48, 11, 7);
        g.connect(25, 5, 0.1);
        g.connect(11, 14, 5);
        g.connect(179, 11, 0);
        g.connect(14, 179, 1);
        g.connect(5, 14, 8);
        g.connect(25, 75, 1.05);
        return g;
    }

    @Test
    void copyTest() {
        directed_weighted_graph g = pathTestGraphCreator();
        directed_weighted_graph ge1 = new DWGraph_DS();
        dw_graph_algorithms wga = new DWGraph_Algo();
        assertNotEquals(ge1, null);
        wga.init(ge1);
        assertEquals(ge1, wga.copy());
        wga.init(g);
        DWGraph_DS gt = (DWGraph_DS) g;
        DWGraph_DS gs = (DWGraph_DS) wga.copy();
        assert gt.equals(gs);
    }

    @Test
    void isConnectedTest() {
        directed_weighted_graph g = pathTestGraphCreator();
        dw_graph_algorithms wga1 = new DWGraph_Algo();
        assertTrue(wga1.isConnected());
        dw_graph_algorithms wga = new DWGraph_Algo();
        wga.init(g);
        assertTrue(!wga.isConnected());
        wga.getGraph().addNode(new Node(801));
        assertFalse(wga.isConnected());
        wga.getGraph().removeEdge(25, 75);
        assertFalse(wga.isConnected());
    }

    @Test
    void shortestPathDistTest() {
        directed_weighted_graph g = pathTestGraphCreator();
        dw_graph_algorithms wga = new DWGraph_Algo();
        wga.init(g);
        assertEquals(wga.shortestPathDist(1, 48), 1);
        assertEquals(wga.shortestPathDist(7, 7), 0);
        assertEquals(wga.shortestPathDist(0, 7), 10.5);
        assertEquals(wga.shortestPathDist(0, 11), 14.17);
        assertEquals(wga.shortestPathDist(7, 75), -1);
        assertEquals(wga.shortestPathDist(179, 11), 0);
        assertEquals(wga.shortestPathDist(14, 179), 1);
    }

    @Test
    void shortestPathTest() {
        directed_weighted_graph g = pathTestGraphCreator();
        dw_graph_algorithms wga = new DWGraph_Algo();
        wga.init(g);
        List<node_data> path = new LinkedList<node_data>();
        assertEquals(null, wga.shortestPath(795, 401));
        path.add(g.getNode(0));
        path.add(g.getNode(4));
        path.add(g.getNode(25));
        path.add(g.getNode(5));
        path.add(g.getNode(14));
        path.add(g.getNode(179));
        path.add(g.getNode(11));
        assertEquals(path, wga.shortestPath(0, 11));
    }

    @Test
    void saveLoadTest() {
        directed_weighted_graph g = pathTestGraphCreator();
        dw_graph_algorithms wga = new DWGraph_Algo();
        wga.init(g);
        String str = "graph.json";
        wga.save(str);
        DWGraph_DS g1 = (DWGraph_DS) wga.copy();
        dw_graph_algorithms wga1 = new DWGraph_Algo();
        wga1.load(str);
        assertEquals(g1, wga1.getGraph());
        g1.removeNode(0);
        assertNotEquals(g1, wga1.getGraph());
    }
}

