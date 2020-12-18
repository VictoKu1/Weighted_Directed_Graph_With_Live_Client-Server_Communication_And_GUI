package gameClient;

import api.*;

import java.util.*;

public class Agent {
    public static HashMap<Integer, Agent> agents = new HashMap();
    private CL_Agent agent;
    private CL_Pokemon target;
    private SC_component scc;

    public Agent(CL_Agent agent) {
        this.agent = agent;
        agents.put(agent.getID(), this);
    }

    public CL_Agent getAgent() {
        return agent;
    }

    public void setAgent(CL_Agent agent) {
        this.agent = agent;
    }

    public SC_component getScc() {
        return scc;
    }

    public void setScc(SC_component scc) {
        this.scc = scc;
    }

    public synchronized Queue<node_data> setQ(List<CL_Pokemon> f) {
        HashMap<CL_Pokemon, Queue<node_data>> map = null;
        while (map == null) {
            map = scc.setQ(f, agent.getSrcNode());
            if (map == null) {
                agent.get_curr_fruit().setTarget(false);
            }
        }
        target = map.keySet().iterator().next();
        return map.get(target);
    }

    public CL_Pokemon getTarget() {
        return target;
    }
}
