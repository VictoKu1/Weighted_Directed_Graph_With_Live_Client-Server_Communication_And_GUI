package gameClient;

import api.edge_data;
import gameClient.util.Point3D;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

public class CL_Pokemon {
    public static int ids = 0;
    private edge_data _edge;
    private double _value;
    private int _type;
    private Point3D _pos;
    private double min_dist;
    private int min_ro;
    private boolean target;
    private int id;

    public CL_Pokemon(Point3D p, int t, double v, edge_data e) {
        _type = t;
        _value = v;
        set_edge(e);
        _pos = p;
        min_dist = -1;
        min_ro = -1;
        this.id = ids;
        ids++;
    }

    /**
     * return if is a target or not
     * @return
     */
    public boolean isTarget() {
        return target;
    }

    /**
     * sets if his a target or not
     * @param target
     */
    public void setTarget(boolean target) {
        this.target = target;
    }

    public String toString() {
        return "F:{v=" + _value + ", t=" + _type + "}";
    }

    /**
     * return the edge which he is on
     * @return
     */
    public edge_data get_edge() {
        return _edge;
    }

    /**
     * sets the edge which he is on
     * @param _edge
     */

    public void set_edge(edge_data _edge) {
        this._edge = _edge;
    }

    /**
     * return the location
     * @return
     */
    public Point3D getLocation() {
        return _pos;
    }

    /**
     * return the type
     * @return
     */

    public int getType() {
        return _type;
    }

    /**
     * return the value
     * @return
     */

    public double getValue() {
        return _value;
    }

    /**
     * return the id
     * @return
     */

    public int getID() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CL_Pokemon pokemon = (CL_Pokemon) o;
        return Double.compare(pokemon._value, _value) == 0 &&
                _type == pokemon._type &&
                Double.compare(pokemon.min_dist, min_dist) == 0 &&
                min_ro == pokemon.min_ro &&
                _edge.equals(pokemon._edge) &&
                _pos.equals(pokemon._pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_edge, _value, _type, _pos, min_dist, min_ro);
    }
}
