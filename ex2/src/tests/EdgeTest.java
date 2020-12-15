
package tests;

import org.junit.jupiter.api.*;
import api.*;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {
    Edge e;
    node_data n1, n2;

    @BeforeEach
    void setup() {
        n1 = new Node(1);
        n2 = new Node(9);
        e = new Edge(n1, n2, 10);
    }

    @Test
    void getSrc() {
        assertEquals(1, e.getSrc());
        assertNotEquals(9, e.getSrc());
    }

    @Test
    void getDest() {
        assertEquals(9, e.getDest());
        assertNotEquals(1, e.getDest());
    }

    @Test
    void getWeight() {
        assertEquals(10, e.getWeight());
    }

    @Test
    void getInfo() {
        assertEquals("", e.getInfo());
        e.setInfo("s");
        assertEquals("s", e.getInfo());

    }

    @Test
    void getTag() {
        assertEquals(0, e.getTag());
        e.setTag(8);
        assertEquals(8, e.getTag());
    }

    @Test
    void equal() {
        Edge e2 = new Edge(n1, n2, 10);
        assert Objects.equals(e, e2);
        e2.setTag(1);
        assert !Objects.equals(e, e2);
        e2.setTag(0);
        e2.setInfo("o");
        assert !Objects.equals(e, e2);
        e2 = new Edge(n1, n2, 11);
        assert !Objects.equals(e, e2);
        e2 = new Edge(n1, new Node(9), 10);
        assert Objects.equals(e, e2);
    }
}

