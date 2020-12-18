package gameClient;

import java.util.*;

public class Pokemon {
    public static HashMap<Integer, Pokemon> pokemon_map = new HashMap<>();
    private CL_Pokemon pokemon;
    private SC_component scc;

    public Pokemon(CL_Pokemon pokemon) {
        this.pokemon = pokemon;
        pokemon_map.put(pokemon.getID(), this);
    }

    public static void resetargets() {
        for (Pokemon p:pokemon_map.values()){
            boolean b = false;
            for (Agent a:Agent.agents.values()) {
                b |= a.getAgent().get_curr_fruit().equals(p.getPokemon());
            }
            if (!b)
                p.getPokemon().setTarget(false);
        }

    }

    public CL_Pokemon getPokemon() {
        return pokemon;
    }

    public SC_component getScc() {
        return scc;
    }

    public void setScc(SC_component scc) {
        this.scc = scc;
    }
}








