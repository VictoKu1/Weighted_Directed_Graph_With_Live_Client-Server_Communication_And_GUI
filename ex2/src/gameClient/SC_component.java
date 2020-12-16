package gameClient;

import api.*;

import java.util.*;

public class SC_component {
    public static ArrayList<SC_component> list = new ArrayList<>();
    private DWGraph_Algo dwGraphAlgo;

    public SC_component(DWGraph_Algo ag) {
        this.dwGraphAlgo = ag;
        list.add(this);
    }

    public DWGraph_Algo getDwGraphAlgo() {
        return dwGraphAlgo;
    }

    public static void set_SCC(dw_graph_algorithms ag) {
        HashMap<Integer, ArrayList<node_data>> scc = ((DWGraph_Algo) (ag)).findSCC();
        directed_weighted_graph g;
        DWGraph_Algo _ag;
        for (ArrayList<node_data> nodes : scc.values()) {
            g = ag.copy();
            for (node_data node : g.getV()) {
                if (!nodes.contains(node))
                    g.removeNode(node.getKey());
            }
            _ag = new DWGraph_Algo();
            _ag.init(g);
            new SC_component(_ag);
        }
    }

    public HashMap<CL_Pokemon,Queue<node_data>>setQ(List<CL_Pokemon> f, int start) {
        ArrayList<CL_Pokemon> fl = new ArrayList<>(f);
        directed_weighted_graph graph = dwGraphAlgo.getGraph();
        directed_weighted_graph dwg = dwGraphAlgo.dijkstraForAll(start);
        double dist , weight;
        ArrayList<pokemon_value> list= new ArrayList<>();
        for (CL_Pokemon pok : f) {
            if (dwg.getNode(pok.get_edge().getSrc()) == null || dwg.getNode(pok.get_edge().getDest()) == null || pok.isTarget())
                continue;
            dist = dwg.getNode(pok.get_edge().getSrc()).getWeight();
            weight = pok.getValue();
            //weight = 1;
            list.add(new pokemon_value(dist,weight,pok));
        }
        list.sort(Comparator.comparing(pokemon_value::getValue));
        CL_Pokemon pokemon = list.get(0).getPokemon();
        pokemon.setTarget(true);
        List<node_data> path = dwGraphAlgo.shortestPath(start,pokemon.get_edge().getSrc());
        Queue<node_data> sp = new ArrayDeque<>(path);
        sp.poll();
        sp.add(graph.getNode(pokemon.get_edge().getDest()));
        HashMap<CL_Pokemon,Queue<node_data>> map = new HashMap<>();
        map.put(pokemon,sp);
        return map;
    }

    private class pokemon_value{
        CL_Pokemon pokemon;
        double value;

        public pokemon_value(double dest,double weight, CL_Pokemon pokemon1 ) {
            this.value = dest/weight;
            this.pokemon = pokemon1;
        }

        public CL_Pokemon getPokemon() {
            return pokemon;
        }

        public double getValue() {
            return value;
        }
    }
}
