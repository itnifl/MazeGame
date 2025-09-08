package main.game.maze.dto;

public class Score implements Comparable<Score> {
    private String name;
    private int theScore;

    public Score(String name, int score) {
        this.name = name;
        this.theScore = score;
    }

    public String getName() {
        return name;
    }

    public int getTheScore() {
        return theScore;
    }

    @Override
    public int compareTo(Score other) {
        // compare scores in descending order
        return Integer.compare(this.theScore, other.getTheScore());
    }

    @Override
    public String toString() {
        return name + ": " + theScore;
    }
}
