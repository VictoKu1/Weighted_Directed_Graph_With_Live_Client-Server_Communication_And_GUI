package gameClient;

import Server.Game_Server_Ex2;
import api.DWGraph_Algo;
import api.directed_weighted_graph;
import api.game_service;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ex2 implements Runnable {
    private static MyFrame _win;
    private static Arena _ar;
    private static HashMap<CL_Agent,Double> cost_matrix = new HashMap<>();


    public static void main(String[] args) {
        Thread client = new Thread(new Ex2_Client());
        client.start();
    }

    @Override
    public void run() {
        Input_Frame input_frame = new Input_Frame("Start the game:");
        input_frame.start();
        int scenario_num = input_frame.getGame_id();
        //long id = input_frame.getLogin();
        //	game.login(id);
        game_service game = Game_Server_Ex2.getServer(scenario_num);
        init(game);
        game.startGame();
        _win.setTitle("level number : " + scenario_num);
        int ind = 0;
        long dt = 1000;
        DWGraph_Algo ag = new DWGraph_Algo();
        ag.load_graph(game.getGraph());
        directed_weighted_graph gg = ag.getGraph();
        while (game.isRunning()) {
            //moveAgants(game, gg);
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
    // Todo first assignment of a  pokemon to every agent
    // Todo update speed fo agent
    // ToDo greedy
   // (sub) todo loop : running through all the the pokemons and finding the best one
   // (sub) todo add a pokemon flag for visited
   // (sub) todo UnassignedCustomerExists (below)
    // Nodes is a list of all the nodes in are situation node is a pokemon
    // CostMatrix is a 2d matrix that hold the dest between one pokemon the all the others
    // for more look at the code below
   /*
   public boolean UnassignedCustomerExists(Node[] Nodes) {
        for (int i = 1; i < Nodes.length; i++) {
            if (!Nodes[i].IsRouted)
                return true;
        }
        return false;
    }

    public void GreedySolution(Node[] Nodes, double[][] CostMatrix) {

        double CandCost, EndCost;
        int VehIndex = 0;

        while (UnassignedCustomerExists(Nodes)) {

            int CustIndex = 0;
            Node Candidate = null;
            double minCost = (float) Double.MAX_VALUE;

            if (Vehicles[VehIndex].Route.isEmpty()) {
                Vehicles[VehIndex].AddNode(Nodes[0]);
            }

            for (int i = 1; i <= NoOfCustomers; i++) {
                if (Nodes[i].IsRouted == false) {
                    if (Vehicles[VehIndex].CheckIfFits(Nodes[i].demand)) {
                        CandCost = CostMatrix[Vehicles[VehIndex].CurLoc][i];
                        if (minCost > CandCost) {
                            minCost = CandCost;
                            CustIndex = i;
                            Candidate = Nodes[i];
                        }
                    }
                }
            }

            if (Candidate == null) {
                //Not a single Customer Fits
                if (VehIndex + 1 < Vehicles.length) //We have more vehicles to assign
                {
                    if (Vehicles[VehIndex].CurLoc != 0) {//End this route
                        EndCost = CostMatrix[Vehicles[VehIndex].CurLoc][0];
                        Vehicles[VehIndex].AddNode(Nodes[0]);
                        this.Cost += EndCost;
                    }
                    VehIndex = VehIndex + 1; //Go to next Vehicle
                } else //We DO NOT have any more vehicle to assign. The problem is unsolved under these parameters
                {
                    System.out.println("\nThe rest customers do not fit in any Vehicle\n" +
                            "The problem cannot be resolved under these constrains");
                    System.exit(0);
                }
            } else {
                Vehicles[VehIndex].AddNode(Candidate);//If a fitting Customer is Found
                Nodes[CustIndex].IsRouted = true;
                this.Cost += minCost;
            }
        }

        EndCost = CostMatrix[Vehicles[VehIndex].CurLoc][0];
        Vehicles[VehIndex].AddNode(Nodes[0]);
        this.Cost += EndCost;

    }
    */
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
            System.out.println(game.getPokemons());
            int src_node = 0;  // arbitrary node, you should start at one of the pokemon
            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
            for (int a = 0; a < cl_fs.size(); a++) {
                Arena.updateEdge(cl_fs.get(a), gg);
            }
            for (int a = 0; a < rs; a++) {
                int ind = a % cl_fs.size();
                CL_Pokemon c = cl_fs.get(ind);
                int nn = c.get_edge().getDest();
                if (c.getType() < 0) {
                    nn = c.get_edge().getSrc();
                }

                game.addAgent(nn);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
