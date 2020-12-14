package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Ex2_Client implements Runnable {
    private static MyFrame _win;
    private static Arena _ar;
    private long id;
    private int game_id;

    public static void main(String[] a) {
        Thread client = new Thread(new Ex2_Client());
        client.start();
        int scenario_num = 11;
        game_service game = Game_Server_Ex2.getServer(scenario_num);
        String g = game.getGraph();
        System.out.println(g);
        DWGraph_Algo ag = new DWGraph_Algo();
        ag.load_graph(g);
    }

    @Override
    public void run() {
        //Input_Frame input_frame = new Input_Frame("Start the game:");
        //input_frame.start();


        int scenario_num = 20;
        game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
        //	int id = 999;
        //	game.login(id);
        String g = game.getGraph();
        System.out.println(g);
        String pks = game.getPokemons();
        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
        init(game);

        game.startGame();
        _win.setTitle("Ex2 - OOP: (NONE trivial Solution) " + game.toString());
        int ind = 0;
        long dt = 1000;

        while (game.isRunning()) {
            moveAgants(game, gg);
            try {
                if (ind % 1 == 0) {
                    _ar.setTime_left(game.timeToEnd());
                    _win.repaint();
                }
                Thread.sleep(dt);
                ind++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String res = game.toString();

        System.out.println(res);
        System.exit(0);
    }

    /**
     * Moves each of the agents along the edge,
     * in case the agent is on a node the next destination (next edge) is chosen (randomly).
     *
     * @param game
     * @param gg
     * @param
     */
    private static void moveAgants(game_service game, directed_weighted_graph gg) {
        String lg = game.move();
        System.out.println(lg);
        List<CL_Agent> log = Arena.getAgents(lg, gg);
        _ar.setAgents(log);
        //ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
        String fs = game.getPokemons();
        List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
        _ar.setPokemons(ffs);
        for (int i = 0; i < log.size(); i++) {
            CL_Agent ag = log.get(i);
            int id = ag.getID();
            int dest = ag.getNextNode();
            int src = ag.getSrcNode();
            double v = ag.getValue();
            if (dest == -1) {
                dest = nextNode(gg, src);
                game.chooseNextEdge(ag.getID(), dest);
                System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);
            }
        }
    }

    /**
     * a very simple random walk implementation!
     *
     * @param g
     * @param src
     * @return
     */
    private static int nextNode(directed_weighted_graph g, int src) {
        int ans = -1;
        Collection<edge_data> ee = g.getE(src);
        Iterator<edge_data> itr = ee.iterator();
        int s = ee.size();
        int r = (int) (Math.random() * s);
        int i = 0;
        while (i < r) {
            itr.next();
            i++;
        }
        ans = itr.next().getDest();
        return ans;
    }

    private void init(game_service game) {
        String g = game.getGraph();
        String fs = game.getPokemons();
        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
        //gg.init(g);
        _ar = new Arena();
        _ar.setGraph(gg);
        _ar.setPokemons(Arena.json2Pokemons(fs));
        _win = new MyFrame("test Ex2");
        _win.setSize(1000, 700);
        _win.update(_ar);
        _win.show();
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("agents");
            System.out.println(info);
            System.out.println(game.getAgents());
            System.out.println(game.getPokemons());
            System.out.println(game.getAgents());
            int src_node = 0;  // arbitrary node, you should start at one of the pokemon
            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
            for (int a = 0; a < cl_fs.size(); a++) {
                Arena.updateEdge(cl_fs.get(a), gg);
            }
            Stack<CL_Pokemon> stk = locate(cl_fs);
            for (int a = 0; a < rs; a++) {
                CL_Pokemon c = stk.pop();
                int nn = c.get_edge().getSrc();
                if (c.getType() < 0) {
                    nn = c.get_edge().getDest();
                }

                game.addAgent(nn);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Stack<CL_Pokemon> locate(ArrayList<CL_Pokemon> cl_fs) {
        cl_fs = sortByWeight(cl_fs);
        Stack<CL_Pokemon> stk = new Stack<CL_Pokemon>();
        for (CL_Pokemon pokemon : cl_fs) {
            stk.push(pokemon);
        }
        return stk;

    }

    private ArrayList<CL_Pokemon> sortByWeight(ArrayList<CL_Pokemon> cl_fs) {
        double[] pokemonListWeight = new double[cl_fs.size()];
        HashMap<Double, CL_Pokemon> weightToPokemon = new HashMap<Double, CL_Pokemon>();
        for (int i = 0; i < pokemonListWeight.length; i++) {
            pokemonListWeight[i] = cl_fs.get(i).getValue();
            weightToPokemon.put(pokemonListWeight[i], cl_fs.get(i));
        }
        quickSort(pokemonListWeight, 0, pokemonListWeight.length - 1);
        ArrayList<CL_Pokemon> cl_fs1 = new ArrayList<CL_Pokemon>(pokemonListWeight.length);
        for (int i = 0; i < pokemonListWeight.length; i++) {
            cl_fs1.add(weightToPokemon.get(pokemonListWeight[i]));
        }
        return cl_fs1;
    }

    public void quickSort(double[] array, int low, int high) {
        int i = low, j = high;
        double pivot = array[low + (high - low) / 2];
        double exchange;
        while (i <= j) {
            while (array[i] < pivot) {
                i++;
            }
            while (array[j] > pivot) {
                j--;
            }
            if (i <= j) {
                exchange = array[i];
                array[i] = array[j];
                array[j] = exchange;
                i++;
                j--;
            }
        }
        if (low < j)
            quickSort(array, low, j);
        if (i < high)
            quickSort(array, i, high);
    }

}
