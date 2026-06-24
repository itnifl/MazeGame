package main.game.maze;

final class TriggerFlameAttackCommand implements JavaFxGameCommand {
    @Override
    public void execute(JavaFxInputCommandContext context) {
        context.triggerPlayerFlameAttack();
    }
}