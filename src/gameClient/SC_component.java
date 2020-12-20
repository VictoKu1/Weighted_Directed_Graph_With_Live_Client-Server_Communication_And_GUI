package gameClient;

import api.*;

import java.util.*;

/** this class represent a strongly connected component of the graph
 */
public class SC_component {
    public static ArrayList<SC_component> list = new ArrayList<>();
    private DWGraph_Algo dwGraphAlgo;
    private Stack<CL_Pokemon> stk ;

    public SC_component(DWGraph_Algo ag) {
        this.dwGraphAlgo = ag;
        list.add(this);
        stk = new Stack<>();
    }

    /**
     * return the part of the graph this class represent
     * @return
     */
    public DWGraph_Algo getDwGraphAlgo() {
        return dwGraphAlgo;
    }

    /**
     * return the best target from the stack of targets that in this scc
     * @return
     */
    public CL_Pokemon getnext() {
        return stk.pop();
    }

    /**
     * adds a target to the stack of targets
     * @param p
     */
    public void addpok(CL_Pokemon p){
        stk.push(p);
    }

    /**
     * sorts the stack for best targets
     */
    public void sort(){
        ArrayList<CL_Pokemon> temp = new ArrayList<>(stk);
        temp.sort(Comparator.comparing(CL_Pokemon::getValue));
        stk = new Stack<>();
        for (CL_Pokemon p:temp) {
            stk.push(p);
        }
    }

    /**
     * this function return the best path for the best pokemon given the starting position
     * the best pokemon is weighted by the distance and his value
     * @param f
     * @param start
     * @return
     */
    public HashMap<CL_Pokemon,Queue<node_data>>setQ(List<CL_Pokemon> f, int start) {
        ArrayList<CL_Pokemon> fl = new ArrayList<>(f);
        directed_weighted_graph graph = dwGraphAlgo.getGraph();
        directed_weighted_graph dwg = dwGraphAlgo.dijkstraForAll(start);
        double dist , weight , length;
        ArrayList<pokemon_value> list= new ArrayList<>();
        for (CL_Pokemon pok : f) {
            if (dwg.getNode(pok.get_edge().getSrc()) == null || dwg.getNode(pok.get_edge().getDest()) == null || pok.isTarget())
                continue;
            dist = dwg.getNode(pok.get_edge().getSrc()).getWeight();
            length = pok.get_edge().getWeight();
            weight = pok.getValue();
            list.add(new pokemon_value(dist,weight,length,pok));
        }
        if (list.size() == 0)
            return null;
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

        public pokemon_value(double dest,double weight, double length,CL_Pokemon pokemon1 ) {
            this.value = (dest + length)/weight;
            this.pokemon = pokemon1;
        }

        public CL_Pokemon getPokemon() {
            return pokemon;
        }

        public double getValue() {
            return value;
        }
    }
    /**
     * this function sets all the strongly connected components
     * @param ag
     */
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
}

