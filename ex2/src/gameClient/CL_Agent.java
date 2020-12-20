package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import com.google.gson.*;
import gameClient.util.Point3D;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.*;

public class CL_Agent {
    private int _id;
    private geo_location _pos;
    private double _speed;
    private edge_data _curr_edge;
    private node_data _curr_node;
    private node_data _prev_node;
    private directed_weighted_graph _gg;
    private CL_Pokemon _curr_fruit;
    private double _value;


    public CL_Agent(directed_weighted_graph g, int start_node) {
        _gg = g;
        setMoney(0);
        this._curr_node = _gg.getNode(start_node);
        _pos = _curr_node.getLocation();
        _id = -1;
        setSpeed(0);
    }

    public void update(String json) {
        JSONObject line;
        try {
            line = new JSONObject(json);
            JSONObject ttt = line.getJSONObject("Agent");
            int id = ttt.getInt("id");
            if (id == this.getID() || this.getID() == -1) {
                if (this.getID() == -1) {
                    _id = id;
                }
                double speed = ttt.getDouble("speed");
                String p = ttt.getString("pos");
                Point3D pp = new Point3D(p);
                int src = ttt.getInt("src");
                int dest = ttt.getInt("dest");
                double value = ttt.getDouble("value");
                this._pos = pp;
                this.setCurrNode(src);
                this.setSpeed(speed);
                this.setNextNode(dest);
                this.setMoney(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getSrcNode() {
        return this._curr_node.getKey();
    }

    public int get_prev_node() {
        return _prev_node.getKey();
    }

    public String toJSON() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(CL_Agent.class, new agent_serializer());
        Gson gson = gsonBuilder.create();
        String ans = gson.toJson(this);
        ans = "{\"Agent\":" + ans + "}";
        return ans;

    }

    private class agent_serializer implements JsonSerializer<CL_Agent> {

        @Override
        public JsonElement serialize(CL_Agent cl_agent, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject json = new JsonObject();
            json.addProperty("id", cl_agent._id);
            json.addProperty("value", cl_agent._value);
            json.addProperty("src", cl_agent._curr_node.getKey());
            json.addProperty("dest", cl_agent.getNextNode());
            json.addProperty("speed", cl_agent._speed);
            json.addProperty("pos", cl_agent._pos.toString());
            return json;
        }
    }

    private void setMoney(double v) {
        _value = v;
    }

    public boolean setNextNode(int dest) {
        boolean ans = false;
        int src = this._curr_node.getKey();
        this._curr_edge = _gg.getEdge(src, dest);
        if (_curr_edge != null) {
            ans = true;
        } else {
            _curr_edge = null;
        }
        return ans;
    }

    public void setCurrNode(int src) {
        this._prev_node = this._curr_node;
        this._curr_node = _gg.getNode(src);
    }

    public boolean isMoving() {
        return this._curr_edge != null;
    }

    public String toString() {
        return toJSON();
    }

    public int getID() {
        return this._id;
    }

    public geo_location getLocation() {
        return _pos;
    }

    public double getValue() {
        return this._value;
    }

    public int getNextNode() {
        int ans = -2;
        if (this._curr_edge == null) {
            ans = -1;
        } else {
            ans = this._curr_edge.getDest();
        }
        return ans;
    }

    public double getSpeed() {
        return this._speed;
    }

    public void setSpeed(double v) {
        this._speed = v;
    }

    public CL_Pokemon get_curr_fruit() {
        return _curr_fruit;
    }

    public void set_curr_fruit(CL_Pokemon curr_fruit) {
        this._curr_fruit = curr_fruit;
    }
}
