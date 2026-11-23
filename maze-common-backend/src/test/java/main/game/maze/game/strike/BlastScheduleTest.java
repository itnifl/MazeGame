package main.game.maze.game.strike;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Blast schedule: fuses burn down and blasts go off once")
class BlastScheduleTest {

    private static ScheduledBlast blast(double seconds, String label) {
        return new ScheduledBlast(seconds, seconds, 1, 2, 100, 50, true, true, label);
    }

    @Test
    @DisplayName("each blast goes off once, when its fuse has burnt")
    void goesOffOnce() {
        BlastSchedule schedule = new BlastSchedule();
        schedule.add(blast(1.0, "a"));
        schedule.add(blast(0.5, "b"));
        assertTrue(schedule.tick(0.4).isEmpty());
        assertEquals(0.6 / 1.0, schedule.pending().get(0).fuseLeft(), 1e-9);
        List<ScheduledBlast> due = schedule.tick(0.2);
        assertEquals(1, due.size());
        assertEquals("b", due.get(0).label());
        assertEquals("a", schedule.tick(1.0).get(0).label());
        assertTrue(schedule.isEmpty());
        assertTrue(schedule.tick(5).isEmpty());
    }

    @Test
    @DisplayName("a zero or negative frame burns nothing; clear removes everything")
    void edges() {
        BlastSchedule schedule = new BlastSchedule();
        schedule.addAll(List.of(blast(0.3, "a"), blast(0.3, "b")));
        schedule.add(null);
        assertTrue(schedule.tick(-1).isEmpty());
        assertEquals(2, schedule.pending().size());
        schedule.clear();
        assertTrue(schedule.isEmpty());
    }
}
