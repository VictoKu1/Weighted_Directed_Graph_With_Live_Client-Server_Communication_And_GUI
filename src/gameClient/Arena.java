package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a multi Agents Arena which move on a graph - grabs targets.
 *
 * @author boaz.benmoshe
 */
public class Arena {
    public static final double EPS1 = 0.001, EPS2 = EPS1 * EPS1;
    private directed_weighted_graph _gg;
    private game_service game;
    private List<CL_Agent> _agents;
    private List<CL_Pokemon> _pokemons;
    private long time_left;

    public void setPokemons(List<CL_Pokemon> f) {
        this._pokemons = f;
    }

    public void setAgents(List<CL_Agent> f) {
        this._agents = f;
    }

    public void setGraph(directed_weighted_graph g) {
        this._gg = g;
    }

    public long getTime_left() {
        return time_left;
    }

    public void setTime_left(long time_left) {
        this.time_left = time_left;
    }

    public game_service getGame() {
        return game;
    }

    public void setGame(game_service game) {
        this.game = game;
    }

    public List<CL_Agent> getAgents() {
        return _agents;
    }

    public List<CL_Pokemon> getPokemons() {
        return _pokemons;
    }

    public directed_weighted_graph getGraph() {
        return _gg;
    }

    /**
     * create all agents given the data from the game
     * @param aa
     * @param gg
     * @return
     */
    public static List<CL_Agent> getAgents(String aa, directed_weighted_graph gg) {
        ArrayList<CL_Agent> ans = new ArrayList<CL_Agent>();
        try {
            JSONObject ttt = new JSONObject(aa);
            JSONArray ags = ttt.getJSONArray("Agents");
            for (int i = 0; i < ags.length(); i++) {
                CL_Agent c = new CL_Agent(gg, 0);
                c.update(ags.get(i).toString());
                ans.add(c);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ans;
    }
    /**
     * update the data of all agents given the
     * @param aa
     * @return
     */
    public static List<CL_Agent> getAgents(String aa) {
        ArrayList<CL_Agent> ans = new ArrayList<CL_Agent>();
        try {
            JSONObject ttt = new JSONObject(aa);
            JSONArray ags = ttt.getJSONArray("Agents");
            for (int i = 0; i < ags.length(); i++) {
                JSONObject line = new JSONObject(ags.get(i).toString());
                JSONObject _ag = line.getJSONObject("Agent");
                int id = _ag.getInt("id");
                CL_Agent c = Agent.agents.get(id).getAgent();
                c.update(ags.get(i).toString());
                ans.add(c);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ans;
    }
    /**
     * create all targets given the data from the game
     * @param fs
     * @param b
     * @param gg
     * @return
     */
    public static ArrayList<CL_Pokemon> json2Pokemons(String fs, boolean b, directed_weighted_graph gg) {
        ArrayList<CL_Pokemon> ans = new ArrayList<CL_Pokemon>();
        try {
            JSONObject ttt = new JSONObject(fs);
            JSONArray ags = ttt.getJSONArray("Pokemons");
            for (int i = 0; i < ags.length(); i++) {
                JSONObject pp = ags.getJSONObject(i);
                JSONObject pk = pp.getJSONObject("Pokemon");
                int t = pk.getInt("type");
                double v = pk.getDouble("value");
                String p = pk.getString("pos");
                CL_Pokemon f = new CL_Pokemon(new Point3D(p), t, v, null);
                Arena.updateEdge(f, gg);
                for (Pokemon pokemon : Pokemon.pokemon_map.values()) {
                    if (pokemon.getPokemon().equals(f)) {
                        f = pokemon.getPokemon();
                        CL_Pokemon.ids--;
                        break;
                    }
                }
                ans.add(f);
            }
            ArrayList<Integer> removable = new ArrayList<>();
            boolean t;
            if (b)
                for (int pokemon : Pokemon.pokemon_map.keySet()) {
                    t = false;
                    for (CL_Pokemon an : ans) {
                        t |= Pokemon.pokemon_map.get(pokemon).getPokemon().equals(an);
                    }
                    if (!t) {
                        removable.add(pokemon);
                    }
                }
            for (int i : removable) {
                Pokemon.pokemon_map.remove(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ans;
    }

    private static void updateEdge(CL_Pokemon fr, directed_weighted_graph g) {
        for (node_data v : g.getV()) {
            Iterator<edge_data> iter = g.getE(v.getKey()).iterator();
            while (iter.hasNext()) {
                edge_data e = iter.next();
                boolean f = isOnEdge(fr.getLocation(), e, fr.getType(), g);
                if (f) {
                    fr.set_edge(e);
                }
            }
        }
    }

    private static boolean isOnEdge(geo_location p, geo_location src, geo_location dest) {

        boolean ans = false;
        double dist = src.distance(dest);
        double d1 = src.distance(p) + p.distance(dest);
        if (dist > d1 - EPS2) {
            ans = true;
        }
        return ans;
    }

    private static boolean isOnEdge(geo_location p, int s, int d, directed_weighted_graph g) {
        geo_location src = g.getNode(s).getLocation();
        geo_location dest = g.getNode(d).getLocation();
        return isOnEdge(p, src, dest);
    }

    private static boolean isOnEdge(geo_location p, edge_data e, int type, directed_weighted_graph g) {
        int src = g.getNode(e.getSrc()).getKey();
        int dest = g.getNode(e.getDest()).getKey();
        if (type < 0 && dest > src) {
            return false;
        }
        if (type > 0 && src > dest) {
            return false;
        }
        return isOnEdge(p, src, dest, g);
    }

    private static Range2D GraphRange(directed_weighted_graph g) {
        Iterator<node_data> itr = g.getV().iterator();
        double x0 = 0, x1 = 0, y0 = 0, y1 = 0;
        boolean first = true;
        while (itr.hasNext()) {
            geo_location p = itr.next().getLocation();
            if (first) {
                x0 = p.x();
                x1 = x0;
                y0 = p.y();
                y1 = y0;
                first = false;
            } else {
                if (p.x() < x0) {
                    x0 = p.x();
                }
                if (p.x() > x1) {
                    x1 = p.x();
                }
                if (p.y() < y0) {
                    y0 = p.y();
                }
                if (p.y() > y1) {
                    y1 = p.y();
                }
            }
        }
        Range xr = new Range(x0, x1);
        Range yr = new Range(y0, y1);
        return new Range2D(xr, yr);
    }

    public static Range2Range w2f(directed_weighted_graph g, Range2D frame) {
        Range2D world = GraphRange(g);
        Range2Range ans = new Range2Range(world, frame);
        return ans;
    }

}
