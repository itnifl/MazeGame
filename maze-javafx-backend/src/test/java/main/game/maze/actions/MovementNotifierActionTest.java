package main.game.maze.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import main.game.maze.actions.MovementNotifierAction;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovementNotifierActionTest {

    @Test
    void notifyMovementAllowsSubscriberMutationWithoutConcurrentModification() {
        TestEntity entity = new TestEntity();
        Pane graphics = new Pane();
        graphics.resize(16, 16);

        AtomicBoolean invoked = new AtomicBoolean(false);

        ICanSubscribeAndNotifyPosition mutatingSubscriber = new ICanSubscribeAndNotifyPosition() {
            @Override
            public void doPositionEvaluation(Bounds nodeBounds, ICanSubscribeAndNotifyPosition mortalEntity) {
                invoked.set(true);
                mortalEntity.removePositionSubscriber(this);
            }

            @Override
            public void addPositionSubscriber(ICanSubscribeAndNotifyPosition entity) {
            }

            @Override
            public void removePositionSubscriber(ICanSubscribeAndNotifyPosition entity) {
            }

            @Override
            public List<ICanSubscribeAndNotifyPosition> getPositionSubscribers() {
                return List.of();
            }
        };

        entity.addPositionSubscriber(mutatingSubscriber);

        MovementNotifierAction action = new MovementNotifierAction(graphics, entity);

        assertDoesNotThrow(action::doNotifyCharacterMovement);
        assertTrue(invoked.get());
    }

    private static final class TestEntity implements ICanSubscribeAndNotifyPosition {
        private final List<ICanSubscribeAndNotifyPosition> subscribers = new ArrayList<>();

        @Override
        public void doPositionEvaluation(Bounds nodeBounds, ICanSubscribeAndNotifyPosition mortalEntity) {
        }

        @Override
        public void addPositionSubscriber(ICanSubscribeAndNotifyPosition entity) {
            subscribers.add(entity);
        }

        @Override
        public void removePositionSubscriber(ICanSubscribeAndNotifyPosition entity) {
            subscribers.remove(entity);
        }

        @Override
        public List<ICanSubscribeAndNotifyPosition> getPositionSubscribers() {
            return subscribers;
        }
    }
}


