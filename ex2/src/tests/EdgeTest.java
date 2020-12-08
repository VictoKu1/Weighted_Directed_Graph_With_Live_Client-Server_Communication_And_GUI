
package tests;

import org.junit.jupiter.api.*;
import api.*;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {
    Edge e;

    @BeforeEach
    void setup() {
        node_data n1 =new Node(1);
        node_data n2 =new Node(9);

        e = new Edge(n1,n2,10);
    }

    @Test
    void getSrc() {
        assertEquals(1,e.getSrc());
        assertNotEquals(9,e.getSrc());
    }

    @Test
    void getDest() {
        assertEquals(9,e.getDest());
        assertNotEquals(1,e.getDest());
    }

    @Test
    void getWeight() {
        assertEquals(10,e.getWeight());
    }

    @Test
    void getInfo() {
        assertEquals(null,e.getInfo());
        e.setInfo("s");
        assertEquals("s",e.getInfo());

    }
    @Test
    void getTag() {
        assertEquals(0,e.getTag());
        e.setTag(8);
        assertEquals(8,e.getTag());
    }
}

