package gameClient;

import java.util.HashMap;

public class Agent {
    public static HashMap<Integer,Agent> agents = new HashMap();
    private CL_Agent agent;

    public Agent(CL_Agent agent) {
        this.agent = agent;
        agents.put(agent.getID(),this);
    }
    public CL_Agent getAgent() {
        return agent;
    }

    public void setAgent(CL_Agent agent) {
        this.agent = agent;
    }
}
