import java.util.*;
import java.util.stream.Collectors;

public class Cetvrti {


}


class Player {

    private String name;

    private int points;

    public Player(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return name + ": " + points;
    }
}

class PointCompetitionTable {

    private List<Player> players = new ArrayList<>();

    public void addPlayer(Player player) {
        players.add(player);
    }

    public List<Player> getCurrentTable() {
        return players.stream().sorted(Comparator.comparingInt(Player::getPoints).reversed()).collect(Collectors.toList());
    }

    public List<Player> getByName() {
        return players.stream().sorted(Comparator.comparing(Player::getName)).collect(Collectors.toList());
    }

}


