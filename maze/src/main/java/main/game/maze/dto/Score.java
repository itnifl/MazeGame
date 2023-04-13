package main.game.maze.dto;

public class Score implements Comparable<Score> {
    private String name;
    private int score;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Score other) {
        // compare scores in descending order
        return Integer.compare(this.score, other.getScore());
    }

    @Override
    public String toString() {
        return name + ": " + score;
    }
}
