package tests;

import org.junit.jupiter.api.*;
import api.*;
import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
    Node n,m;
    @BeforeEach
    void setup(){
        n = new Node(0);
        m = new Node(1);
    }

    @Test
    void hasNi() {
        assertEquals(false,n.hasNi(1));
    }
    @Test
    void removeNode() {
        n.addNi(1,10,true);
        assertNotEquals(null,n.removeNode(1));
        assertEquals(null,m.removeNode(0));
    }
    @Test
    void getNi() {
        n.addNi(1,10,true);
        Integer[] ni = n.getNi();
        assertEquals(1,ni[0]);
        assertEquals(1,ni.length);
    }
    @Test
    void getnNi() {
        n.addNi(1,10,false);
        Integer[] ni = n.getnNi();
        assertEquals(1,ni[0]);
        assertEquals(1,ni.length);
    }
    @Test
    void getKey() {
        assertEquals(0,n.getKey());
    }
    @Test
    void getLocation() {
        assertEquals(null,n.getLocation());
        geo_location g = new Location();
        n.setLocation(g);
        assertEquals(g,n.getLocation());
    }
    @Test
    void getWeight() {
        n.setWeight(10);
        assertEquals(10,n.getWeight());
    }
    @Test
    void getInfo() {
        n.setInfo("p");
        assertEquals("p",n.getInfo());
    }
    @Test
    void getTag() {
        n.setTag(0);
        assertEquals(0,n.getTag());
    }
}