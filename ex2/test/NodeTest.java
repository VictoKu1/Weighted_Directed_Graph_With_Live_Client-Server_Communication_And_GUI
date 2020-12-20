
import org.junit.jupiter.api.*;
import api.*;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
    Node n, m;

    @BeforeEach
    void setup() {
        n = new Node(0);
        m = new Node(1);
    }

    @Test
    void getKey() {
        assertEquals(0, n.getKey());
    }

    @Test
    void getLocation() {
        assertEquals(null, n.getLocation());
        geo_location g = new Location();
        n.setLocation(g);
        assertEquals(g, n.getLocation());
    }

    @Test
    void getWeight() {
        n.setWeight(10);
        assertEquals(10, n.getWeight());
    }

    @Test
    void getInfo() {
        n.setInfo("p");
        assertEquals("p", n.getInfo());
    }

    @Test
    void getTag() {
        n.setTag(0);
        assertEquals(0, n.getTag());
    }

    @Test
    void equal() {
        Node parent = new Node(1);
        m = new Node(0);
        n.setInfo("p");
        n.setWeight(10);
        n.setTag(5);
        n.setLocation(new Location(1, 1, 1));
        m.setParent(parent);
        m.setInfo("p");
        m.setWeight(10);
        m.setTag(5);
        m.setLocation(new Location(1, 1, 1));
        m.setParent(parent);
        assertEquals(m, n);
    }

    @Test
    void not_equal() {
        Node parent = new Node(1);
        n.setInfo("p");
        n.setWeight(10);
        n.setTag(5);
        n.setLocation(new Location(1, 1, 1));
        m.setParent(parent);
        m.setInfo("p");
        m.setWeight(10);
        m.setTag(5);
        m.setLocation(new Location(1, 1, 1));
        m.setParent(parent);
        assertNotEquals(m, n);
    }

}

