package gameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Input_Frame extends JFrame {
    private long login;
    private int game_id;

    public Input_Frame(String title) {
        super(title);

    }

    public synchronized boolean start() {

        this.getContentPane().setLayout(new FlowLayout());
        JButton b = new JButton();
        JLabel l = new JLabel();
        JTextField tf1, tf2;
        tf1 = new JTextField("Login", 10);
        tf2 = new JTextField("Game ID", 10);
        this.add(b);
        this.add(tf1);
        this.add(tf2);
        this.add(l);
        this.pack();
        this.setVisible(true);
        final boolean[] bool = new boolean[1];
        bool[0] = true;
        while (bool[0])
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        login = Long.parseLong(tf1.getText());
                        game_id = Integer.parseInt(tf2.getText());
                        bool[0] = false;
                    } catch (IllegalArgumentException exception) {
                        l.setFont(new Font("Ariel", Font.PLAIN, 20));
                        l.setForeground(Color.white);
                        l.setText("the input are not numbers");
                    }
                }
            });
        this.setVisible(false);
        return bool[0];
    }

    public synchronized long getLogin() {
        return login;
    }

    public synchronized int getGame_id() {
        return game_id;
    }
}
