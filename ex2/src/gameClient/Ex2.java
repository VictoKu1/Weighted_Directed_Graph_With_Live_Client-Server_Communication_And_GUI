package gameClient;

import Server.Game_Server_Ex2;
import api.directed_weighted_graph;
import api.game_service;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Ex2 implements Runnable{
    private static MyFrame _win;
    private static Arena _ar;


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
    // todo algorithm for moving agents
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
