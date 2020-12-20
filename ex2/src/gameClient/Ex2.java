package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Ex2 implements Runnable {
    private static MyFrame _win;
    private static Arena _ar;
    private static HashMap<Integer, Queue<node_data>> agents_paths = new HashMap<>();
    private static String[] input;
    private long dt = 200;


    public static void main(String[] args) {
        input = args;
        Thread client = new Thread(new Ex2());
        client.start();
    }

    @Override
    public void run() {
        int scenario_num;
        long id;
        if (input.length != 0){
            id = Long.parseLong(input[0]);
            scenario_num = Integer.parseInt(input[1]);
        }
        else{
        Input_Frame input_frame = new Input_Frame("Start the game:");
        input_frame.start();
        scenario_num = input_frame.getGame_id();
        id = input_frame.getLogin();
        }
        game_service game = Game_Server_Ex2.getServer(scenario_num);
        //game.login(id);
        init(game);
        game.startGame();
        long time = game.timeToEnd();
        _win.setTitle("level number : " + scenario_num);
        DWGraph_Algo ag = new DWGraph_Algo();
        ag.load_graph(game.getGraph());
        directed_weighted_graph gg = ag.getGraph();
        while (game.isRunning()) {
            greedy(game, gg);
            try {
                {
                    _ar.setTime_left(game.timeToEnd());
                    _win.repaint();
                }
                Thread.sleep(dt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String res = game.toString();

        System.out.println(res);
        System.out.println(time);
        System.exit(0);
    }

    /**
     * this function uses the greedy mindset for computing the path each agent need to go.
     * it gates information from the sever and start computing the best way each for each agent .
     * the way its computing : if the agents isn't moving checks the state of the agent
     * state 1 : the agent is right next to is target , compute a method for insuring that the agent will get is target.
     * state 2 : the agent just took is target there for need a new one :
     * finding is new target by finding the best target from is location (see SC_component setQ function)
     * state 3 : the agent is on is way toward is target.
     *
     * @param game
     * @param gg
     */
    public void greedy(game_service game, directed_weighted_graph gg) {
        game.move();
        String lg = game.getAgents();
        List<CL_Agent> agents = _ar.getAgents(lg);
        ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons(), true, gg);
        for (int a = 0; a < cl_fs.size(); a++) {
            new Pokemon(cl_fs.get(a));
        }
        _ar.setPokemons(cl_fs);
        Agent agent;
        set_pokemons_scc();
        for (CL_Agent a : agents) {
            agent = Agent.agents.get(a.getID());
            if (a.isMoving())
                continue;
            edge_data edgeData = a.get_curr_fruit().get_edge();
            if (a.getSrcNode() == edgeData.getSrc() && a.getSpeed() == 5) {
                dt = 100;
            }
            CL_Pokemon pokemon = agent.getTarget();
            if (a.getSrcNode() == pokemon.get_edge().getDest() && a.get_prev_node() == pokemon.get_edge().getSrc()) {
                agents_paths.put(a.getID(), agent.setQ(cl_fs));
                a.set_curr_fruit(agent.getTarget());
            }
            node_data node = agents_paths.get(a.getID()).poll();
            a.setNextNode(node.getKey());
            game.chooseNextEdge(a.getID(), node.getKey());
        }
        Pokemon.resetargets();
    }

    private void init(game_service game) {
        String g = game.getGraph();
        dw_graph_algorithms ag = new DWGraph_Algo();
        ((DWGraph_Algo) (ag)).load_graph(g);
        directed_weighted_graph gg = ag.getGraph();
        _ar = new Arena();
        _ar.setGraph(gg);
        SC_component.set_SCC(ag);
        _win = new MyFrame("game graph");
        _win.setSize(1000, 700);
        _win.update(_ar);
        _win.show();
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("agents");
            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons(), false, gg);
            for (int a = 0; a < cl_fs.size(); a++) {
                new Pokemon(cl_fs.get(a));
            }
            _ar.setPokemons(cl_fs);
            init_move(game, ag, rs);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void init_move(game_service game, dw_graph_algorithms ag, int rs) {
        HashMap<Integer, SC_component> scc_map = new HashMap<>();
        int size;
        directed_weighted_graph graph;
        for (SC_component scc : SC_component.list) {
            graph = scc.getDwGraphAlgo().getGraph();
            size = graph.nodeSize();
            scc_map.put(size, scc);
        }
        set_pokemons_scc();
        HashMap<Double, Pokemon> pokemons_value = new HashMap<>();
        CL_Pokemon p;
        for (Pokemon pokemon : Pokemon.pokemon_map.values()) {
            p = pokemon.getPokemon();
            pokemons_value.put(p.getValue(), pokemon);
        }

        for (Pokemon pok : pokemons_value.values()) {
            pok.getScc().addpok(pok.getPokemon());
        }
        int i = 0;
        ArrayList<Integer> keys = new ArrayList<>(scc_map.keySet());
        Collections.sort(keys);
        int key, nn;
        for (int a = 0; a < rs; a++) {
            if (i == SC_component.list.size())
                i = 0;
            key = keys.get(i);
            scc_map.get(key).sort();
            nn = scc_map.get(key).getnext().get_edge().getSrc();
            game.addAgent(nn);
        }
        String s = game.getAgents();
        List<CL_Agent> agents = Arena.getAgents(s, ag.getGraph());
        _ar.setAgents(agents);
        Agent _agent;
        List<CL_Pokemon> f = _ar.getPokemons();
        for (CL_Agent agent : agents) {
            _agent = new Agent(agent);
            if (i == SC_component.list.size())
                i = 0;
            key = keys.get(i);
            _agent.setScc(scc_map.get(key));
            agents_paths.put(agent.getID(), _agent.setQ(f));
            agent.set_curr_fruit(_agent.getTarget());
        }
    }

    private void set_pokemons_scc() {
        List<Pokemon> pokemons = new ArrayList<>(Pokemon.pokemon_map.values());
        int src, dest;
        directed_weighted_graph graph;
        for (SC_component scc : SC_component.list) {
            graph = scc.getDwGraphAlgo().getGraph();
            for (Pokemon pokemon : pokemons) {
                src = pokemon.getPokemon().get_edge().getSrc();
                dest = pokemon.getPokemon().get_edge().getDest();
                if (graph.getNode(src) != null && graph.getNode(dest) != null)
                    pokemon.setScc(scc);
            }
        }
    }
}
