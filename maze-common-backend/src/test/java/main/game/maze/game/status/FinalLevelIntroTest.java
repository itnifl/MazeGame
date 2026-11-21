package main.game.maze.game.status;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.game.maze.game.items.PlayerArsenal;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.LevelProgress;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Final level and game won: the title card, the dark voice, the winning words")
class FinalLevelIntroTest {

    private static final String SOUNDS = "/main/game/maze/sounds/";

    @Test
    @DisplayName("the intro applies to the last level, whichever it is, and to no other")
    void onlyTheLastLevel() {
        LevelProgress progress = new LevelProgress(LevelCatalogLoader.loadDefault());
        assertFalse(FinalLevelIntro.applies(progress), "level 1");
        while (progress.hasNext()) {
            assertFalse(FinalLevelIntro.applies(progress));
            progress.advance(new PlayerArsenal(1));
        }
        assertTrue(FinalLevelIntro.applies(progress), "the last level");
        assertFalse(FinalLevelIntro.applies(null));
    }

    @Test
    @DisplayName("the title says FINAL LEVEL; winning the last level says GAME WON instead of YOU WIN")
    void words() {
        assertEquals("FINAL LEVEL", FinalLevelIntro.TEXT);
        assertEquals("GAME WON", FinalLevelIntro.winText(true));
        assertEquals("YOU WIN", FinalLevelIntro.winText(false));
    }

    @Test
    @DisplayName("both dark voices exist")
    void voicesExist() {
        assertTrue(FinalLevelIntro.VOICE.startsWith(SOUNDS));
        assertTrue(FinalLevelIntro.WIN_VOICE.startsWith(SOUNDS));
        assertTrue(FinalLevelIntroTest.class.getResource(FinalLevelIntro.VOICE) != null, "final-level.wav");
        assertTrue(FinalLevelIntroTest.class.getResource(FinalLevelIntro.WIN_VOICE) != null, "ultimate-victory.wav");
    }

    @Test
    @DisplayName("the title card slams in too big, settles, shakes, holds, then fades out")
    void titleCardMoves() {
        float total = FinalLevelIntro.SECONDS;
        TitleCard.Frame start = TitleCard.frame(0.01f, total);
        TitleCard.Frame landed = TitleCard.frame(TitleCard.SLAM_SECONDS, total);
        TitleCard.Frame held = TitleCard.frame(total * 0.7f, total);
        TitleCard.Frame leaving = TitleCard.frame(total - 0.1f, total);
        assertTrue(start.scale() > 3f, "it starts far too big");
        assertEquals(1f, landed.scale(), 0.01f, "it lands on its size");
        assertTrue(held.scale() >= 1f && held.scale() < 1.1f, "then grows a little");
        assertEquals(1f, held.alpha(), 1e-6);
        assertTrue(leaving.alpha() < 0.3f, "and fades out at the end");
        TitleCard.Frame shaking = TitleCard.frame(TitleCard.SLAM_SECONDS + 0.05f, total);
        assertTrue(Math.abs(shaking.shakeX()) + Math.abs(shaking.shakeY()) > 0f, "the landing shakes the title");
        TitleCard.Frame calm = TitleCard.frame(total * 0.7f, total);
        assertEquals(0f, calm.shakeX(), 1e-9);
        assertEquals(0f, TitleCard.frame(-1f, total).alpha());
        assertEquals(0f, TitleCard.frame(total, total).alpha());
        assertEquals(0f, TitleCard.frame(0.5f, 0f).alpha());
    }

    @Test
    @DisplayName("the status bus shows a title card as a banner that moves like one")
    void busTitleCard() {
        StatusMessageBus bus = new StatusMessageBus();
        bus.showTitleCard("FINAL LEVEL", 2.8f);
        assertTrue(bus.isTitleCard());
        assertEquals("FINAL LEVEL", bus.bannerText());
        assertTrue(bus.titleFrame().scale() > 1f);
        bus.showBanner("Teleported", 1f);
        assertFalse(bus.isTitleCard(), "an ordinary banner is not a title card");
        bus.showTitleCard("X", 1f);
        bus.clearBanner();
        assertFalse(bus.isTitleCard());
    }
}
