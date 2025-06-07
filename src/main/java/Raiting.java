import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Raiting implements Serializable {
    List<Game> games = new ArrayList<>();
    public void addGame(Game game) {
        games.add(game);
    }
}

class Game implements Serializable {
    String gamer;
    Integer raiting;
    Date gameDate;

    public Game(String gamer, Integer raiting, Date gameDate) {
        this.gamer = gamer;
        this.raiting = raiting;
        this.gameDate = gameDate;
    }
}
