package gameClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.awt.*;

public class output_frame extends JFrame {
    private Game_Server gameServer;
    private int time;

    public output_frame(String title,String info) {
        super("level : " + title);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Game_Info g =  gson.fromJson(info,Game_Info.class);
        gameServer = g.GameServer;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setTime(long time) {
        this.time = ((int)(time/1000));
    }

    public void start(){
        JLabel l1 = new JLabel();

        l1.setFont(new Font("Ariel", Font.PLAIN, 15));
        l1.setForeground(Color.BLACK);
        Dimension size = l1.getPreferredSize();
        l1.setBounds(10, 10, size.width, size.height);

        this.add(l1);
        this.setVisible(true);

        l1.setText("grade : " + gameServer.grade+", \n moves : " + gameServer.moves+", \n agents : " + gameServer.agents+", \n targets : " + gameServer.pokemons+", \n time : " + time + " s");

        while(true){

        }
    }
    private class Game_Info{
        private Game_Server GameServer;
    }
    private class Game_Server{
        int pokemons;
        boolean is_logged_in;
        int moves;
        int grade;
        int game_level;
        int max_user_level;
        int id;
        String graph;
        int agents;
    }
}
