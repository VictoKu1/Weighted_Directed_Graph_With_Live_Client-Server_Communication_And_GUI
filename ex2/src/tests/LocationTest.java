package tests;

import api.Location;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    Location l, m, g;

    @BeforeEach
    void setup() {
        l = new Location();
        m = new Location(1, 2, 3);
        g = new Location(9, 7, 67);
    }

    @Test
    void x() {
        assertEquals(0, l.x());
        assertEquals(1, m.x());
        assertEquals(9, g.x());
    }

    @Test
    void y() {
        assertEquals(0, l.y());
        assertEquals(2, m.y());
        assertEquals(7, g.y());
    }

    @Test
    void z() {
        assertEquals(0, l.z());
        assertEquals(3, m.z());
        assertEquals(67, g.z());
    }

    @Test
    void distance() {
        assertEquals(0, l.distance(l));
        assertEquals(0, m.distance(m));
        assertEquals(0, g.distance(g));
        assertEquals(3.74, l.distance(m), 0.01);
        assertEquals(68, l.distance(g), 0.1);
        assertEquals(64.7, g.distance(m), 0.01);

    }
}