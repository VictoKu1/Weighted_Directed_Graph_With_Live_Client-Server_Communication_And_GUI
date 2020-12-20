package gameClient;

public class main {
    public static void main(String[] args) {
        int g_id;
        long id;
        if (args.length != 0){
            id = Long.parseLong(args[0]);
            g_id = Integer.parseInt(args[1]);
        }
        else  {
            Input_Frame frame = new Input_Frame("p");
            frame.start();
            g_id = frame.getGame_id();
            id = frame.getLogin();
        }
        System.out.println("game id " + g_id);
        System.out.println("id " + id);
        System.exit(0);
    }
}
