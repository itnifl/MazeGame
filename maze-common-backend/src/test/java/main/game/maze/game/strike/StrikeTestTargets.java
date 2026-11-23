package main.game.maze.game.strike;

import main.game.maze.mazeworld.flame.FlameTarget;

/** A simple enemy for the strike tests: a point with hit points. */
final class StrikeTestTargets {

    private StrikeTestTargets() {
    }

    static final class Dummy implements FlameTarget {
        final double x;
        final double y;
        int hp;
        int taken;

        Dummy(double x, double y, int hp) {
            this.x = x;
            this.y = y;
            this.hp = hp;
        }

        @Override public double centerX() { return x; }
        @Override public double centerY() { return y; }
        @Override public int hitPoints() { return hp; }
        @Override public void applyDamage(int amount) {
            hp -= amount;
            taken += amount;
        }
        @Override public boolean isPassThrough() { return false; }
    }
}
