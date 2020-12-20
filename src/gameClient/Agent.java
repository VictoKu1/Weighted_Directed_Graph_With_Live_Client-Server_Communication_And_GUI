package gameClient;

import api.*;

import java.util.*;

/**
 * this class adds function to the CL_Agent class
 */
public class Agent {
    public static HashMap<Integer, Agent> agents = new HashMap();
    private CL_Agent agent;
    private CL_Pokemon target;
    private SC_component scc;

    public Agent(CL_Agent agent) {
        this.agent = agent;
        agents.put(agent.getID(), this);
    }

    /**
     * return the agent
     *
     * @return
     */
    public CL_Agent getAgent() {
        return agent;
    }

    /**
     * return the agent strongly connected component
     *
     * @return
     */

    public SC_component getScc() {
        return scc;
    }

    /**
     * sets the strongly connected component
     */
    public void setScc(SC_component scc) {
        this.scc = scc;
    }

    /**
     * this function return the best path to the best target for the agent
     * @param f
     * @return
     */

    public synchronized Queue<node_data> setQ(List<CL_Pokemon> f) {
        HashMap<CL_Pokemon, Queue<node_data>> map = null;
        while (map == null) {
            map = scc.setQ(f, agent.getSrcNode());
            if (map == null) {
                agent.get_curr_fruit().setTarget(false);
                Pokemon.resetargets();
                f = new ArrayList<>();
                for (Pokemon p : Pokemon.pokemon_map.values()) {
                    f.add(p.getPokemon());
                }
            }
        }
        target = map.keySet().iterator().next();
        return map.get(target);
    }

    /**
     * return the target of the agent
     * @return
     */
    public CL_Pokemon getTarget() {
        return target;
    }
}
