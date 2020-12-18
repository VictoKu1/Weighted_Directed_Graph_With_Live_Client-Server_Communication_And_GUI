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

    public static CL_Pokemon init_from_json(String json) {
        CL_Pokemon ans = null;
        try {
            JSONObject p = new JSONObject(json);
            int id = p.getInt("id");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }

    public boolean isTarget() {
        return target;
    }

    public void setTarget(boolean target) {
        this.target = target;
    }

    public String toString() {
        return "F:{v=" + _value + ", t=" + _type + "}";
    }

    public edge_data get_edge() {
        return _edge;
    }

    public void set_edge(edge_data _edge) {
        this._edge = _edge;
    }

    public Point3D getLocation() {
        return _pos;
    }

    public int getType() {
        return _type;
    }

    //	public double getSpeed() {return _speed;}
    public double getValue() {
        return _value;
    }

    public double getMin_dist() {
        return min_dist;
    }

    public void setMin_dist(double mid_dist) {
        this.min_dist = mid_dist;
    }

    public int getMin_ro() {
        return min_ro;
    }

    public void setMin_ro(int min_ro) {
        this.min_ro = min_ro;
    }

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
