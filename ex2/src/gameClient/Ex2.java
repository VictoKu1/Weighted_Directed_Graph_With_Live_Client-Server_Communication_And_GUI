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


    public static void main(String[] args) {
        Thread client = new Thread(new Ex2());
        client.start();
    }

    @Override
    public void run() {
        //Input_Frame input_frame = new Input_Frame("Start the game:");
        //input_frame.start();
        //int scenario_num = input_frame.getGame_id();
        int scenario_num = 3;
        //long id = input_frame.getLogin();
        //	game.login(id);
        game_service game = Game_Server_Ex2.getServer(scenario_num);
        init(game);
        game.startGame();
        long time = game.timeToEnd();
        _win.setTitle("level number : " + scenario_num);
        int ind = 0;
        long dt = 100;
        DWGraph_Algo ag = new DWGraph_Algo();
        ag.load_graph(game.getGraph());
        directed_weighted_graph gg = ag.getGraph();
        while (game.isRunning()) {
            greedy(game, gg);
            try {
                if (ind % 1 == 0) {
                    _ar.setTime_left(game.timeToEnd());
                    _win.repaint();
                }
                Thread.sleep(1);
                ind++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String res = game.toString();

        System.out.println(res);
        System.out.println(time);
        System.exit(0);
    }

    public void greedy(game_service game, directed_weighted_graph gg) {
        game.move();
        String lg = game.getAgents();
        List<CL_Agent> agents = _ar.getAgents(lg);
        lg = game.getPokemons();
        ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
        for (int a = 0; a < cl_fs.size(); a++) {
            Arena.updateEdge(cl_fs.get(a), gg);
            new Pokemon(cl_fs.get(a));
        }
        _ar.setPokemons(cl_fs);
        Agent agent;
        set_pokemons_scc();
        for (CL_Agent a : agents) {
            agent = Agent.agents.get(a.getID());
            if (a.isMoving())
                continue;
            CL_Pokemon pokemon = agent.getTarget();
            System.out.println("agent number : " + a.getID() + " is going to :" + a.get_curr_fruit().getID() + " " + a.get_curr_fruit());
            if (a.getSrcNode() == pokemon.get_edge().getDest()) {
                agents_paths.put(a.getID(), agent.setQ(cl_fs));
                a.set_curr_fruit(agent.getTarget());
            }
            node_data node = agents_paths.get(a.getID()).poll();
            a.setNextNode(node.getKey());
            game.chooseNextEdge(a.getID(), node.getKey());
        }
    }

    //TODO change the start of all agent to be as far as possible ( inside the same component)
    private void init(game_service game) {
        String g = game.getGraph();
        String fs = game.getPokemons();
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
            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
            for (int a = 0; a < cl_fs.size(); a++) {
                Arena.updateEdge(cl_fs.get(a), gg);
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
        int i = 0;
        ArrayList<Integer> keys = new ArrayList<>(scc_map.keySet());
        Collections.sort(keys);
        int key, nn;
        for (int a = 0; a < rs; a++) {
            if (i == SC_component.list.size())
                i = 0;
            key = keys.get(i);
            graph = scc_map.get(key).getDwGraphAlgo().getGraph();
            nn = graph.getV().iterator().next().getKey();
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
