import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

/**
 * Synthesises the weapon and pickup sound effects referenced by {@code xmi/items/items.xmi} into
 * {@code main/game/maze/sounds/} as 16-bit mono 22 050 Hz WAV files. Every pickup has its own,
 * clearly different sound:
 * <ul>
 *   <li>{@code pickup-bomb.wav}: low thunk with a short fuse hiss</li>
 *   <li>{@code pickup-laser.wav}: rising electronic chirp</li>
 *   <li>{@code pickup-sledgehammer.wav}: metallic clank</li>
 *   <li>{@code pickup-shield.wav}: shimmering rising chord</li>
 *   <li>{@code pickup-extra-life.wav}: bell fanfare ending on a shimmering chord</li>
 *   <li>{@code pickup-heart.wav}: 8-bit "extra life" jingle, a rising six-note square-wave arpeggio</li>
 * </ul>
 * plus {@code laser-fire.wav} ("pew"), {@code sledgehammer-swing.wav} (whoosh ending in a heavy
 * thud) and {@code weapon-switch.wav} (short click). Deterministic (seeded noise).
 *
 * <pre>java tools/sound-gen/SoundEffectGenerator.java [resourceDir]</pre>
 */
public final class SoundEffectGenerator {

    static final int RATE = 22_050;

    private SoundEffectGenerator() {
    }

    public static void main(String[] args) throws IOException {
        Path dir = Path.of(args.length > 0 ? args[0]
                : "maze-common-frontend/src/main/resources/main/game/maze").resolve("sounds");
        Files.createDirectories(dir);
        write(dir, "pickup-bomb.wav", pickupBomb());
        write(dir, "pickup-laser.wav", pickupLaser());
        write(dir, "pickup-sledgehammer.wav", pickupSledgehammer());
        write(dir, "pickup-shield.wav", pickupShield());
        write(dir, "pickup-heart.wav", pickupHeart());
        write(dir, "pickup-extra-life.wav", pickupExtraLife());
        write(dir, "pickup-gatling.wav", pickupGatling());
        write(dir, "bomb-explosion.wav", bombExplosion());
        write(dir, "teleport.wav", teleport());
        write(dir, "wall-build.wav", wallBuild());
        write(dir, "rpg-launch.wav", rpgLaunch());
        write(dir, "rpg-explosion.wav", rpgExplosion());
        write(dir, "tower-hit.wav", towerHit());
        write(dir, "tower-destroyed.wav", towerDestroyed());
        write(dir, "laser-fire.wav", laserFire());
        write(dir, "gatling-burst.wav", gatlingBurst());
        write(dir, "sledgehammer-swing.wav", sledgehammerSwing());
        write(dir, "weapon-switch.wav", weaponSwitch());
    }

    static double[] pickupBomb() {
        double[] out = buffer(0.32);
        Random noise = new Random(1);
        for (int i = 0; i < out.length; i++) {
            double t = i / (double) RATE;
            double thunk = Math.sin(2 * Math.PI * (120 - 60 * t) * t) * Math.exp(-t * 14);
            double hiss = t > 0.08 ? (noise.nextDouble() * 2 - 1) * 0.25 * Math.exp(-(t - 0.08) * 18) : 0;
            out[i] = 0.9 * thunk + hiss;
        }
        return out;
    }

    static double[] pickupLaser() {
        double[] out = buffer(0.28);
        double phase = 0;
        for (int i = 0; i < out.length; i++) {
            double t = i / (double) RATE;
            double freq = 420 * Math.pow(4.0, t / 0.28);
            phase += 2 * Math.PI * freq / RATE;
            double square = Math.signum(Math.sin(phase)) * 0.35 + Math.sin(phase * 2) * 0.2;
            out[i] = square * envelope(t, 0.005, 0.28);
        }
        return out;
    }

    static double[] pickupSledgehammer() {
        double[] out = buffer(0.45);
        double[] partials = {523, 1312, 2389, 3731};
        Random noise = new Random(2);
        for (int i = 0; i < out.length; i++) {
            double t = i / (double) RATE;
            double ring = 0;
            for (int p = 0; p < partials.length; p++) {
                ring += Math.sin(2 * Math.PI * partials[p] * t) * Math.exp(-t * (6 + p * 5)) / (p + 1);
            }
            double click = t < 0.012 ? (noise.nextDouble() * 2 - 1) * (1 - t / 0.012) : 0;
            out[i] = 0.7 * ring + 0.5 * click;
        }
        return out;
    }

    static double[] pickupShield() {
        double[] out = buffer(0.6);
        double[] chord = {659.25, 830.61, 987.77};
        for (int i = 0; i < out.length; i++) {
            double t = i / (double) RATE;
            double rise = 1 + 0.12 * t;
            double tremolo = 0.75 + 0.25 * Math.sin(2 * Math.PI * 18 * t);
            double value = 0;
            for (double f : chord) {
                value += Math.sin(2 * Math.PI * f * rise * t);
            }
            out[i] = value / chord.length * tremolo * envelope(t, 0.04, 0.6);
        }
        return out;
    }

    /** Notes of the extra-life jingle: a rising G major arpeggio over two octaves (original, not a copy of any game's). */
    static final double[] EXTRA_LIFE_NOTES = {784.0, 987.8, 1174.7, 1568.0, 1975.5, 2349.3};
    static final double EXTRA_LIFE_NOTE_SECONDS = 0.075;

    /**
     * Heart pickup: an 8-bit style "extra life" jingle, six quick square-wave notes rising over two
     * octaves, the last one ringing out with a little vibrato.
     */
    static double[] pickupHeart() {
        double step = EXTRA_LIFE_NOTE_SECONDS;
        double lastLength = 0.28;
        double[] out = buffer(step * (EXTRA_LIFE_NOTES.length - 1) + lastLength);
        for (int n = 0; n < EXTRA_LIFE_NOTES.length; n++) {
            boolean last = n == EXTRA_LIFE_NOTES.length - 1;
            double length = last ? lastLength : step;
            int start = (int) Math.round(n * step * RATE);
            double phase = 0;
            for (int k = 0; start + k < out.length && k < length * RATE; k++) {
                double t = k / (double) RATE;
                double freq = EXTRA_LIFE_NOTES[n] * (last ? 1 + 0.012 * Math.sin(2 * Math.PI * 7 * t) : 1);
                phase += freq / RATE;
                double pulse = (phase % 1.0) < 0.25 ? 1 : -1;            // 25 % duty square, the chiptune timbre
                double gate = last ? envelope(t, 0.003, length) : (t < length * 0.85 ? 1 : 0);
                out[start + k] = 0.32 * pulse * gate;
            }
        }
        return out;
    }

    /**
     * Extra-life pickup: a bright bell fanfare, a quick C-E-G-C climb in a triangle wave that ends on a
     * shimmering major chord, clearly different from the heart's square-wave jingle.
     */
    static double[] pickupExtraLife() {
        double[] climb = {523.3, 659.3, 784.0, 1046.5};
        double step = 0.09;
        double chordLength = 0.6;
        double[] out = buffer(step * climb.length + chordLength);
        for (int n = 0; n < climb.length; n++) {
            int start = (int) Math.round(n * step * RATE);
            for (int k = 0; start + k < out.length && k < step * RATE; k++) {
                double t = k / (double) RATE;
                out[start + k] += 0.4 * triangle(climb[n] * t) * envelope(t, 0.004, step);
            }
        }
        int chordStart = (int) Math.round(step * climb.length * RATE);
        double[] chord = {1046.5, 1318.5, 1568.0};
        for (int k = 0; chordStart + k < out.length; k++) {
            double t = k / (double) RATE;
            double shimmer = 1 + 0.15 * Math.sin(2 * Math.PI * 9 * t);
            double sum = 0;
            for (double f : chord) {
                sum += triangle(f * t);
            }
            out[chordStart + k] = 0.18 * sum * shimmer * envelope(t, 0.01, chordLength);
        }
        return out;
    }

    /** Triangle wave of the given phase in cycles (-1..1). */
    private static double triangle(double cycles) {
        double p = cycles - Math.floor(cycles);
        return 4 * Math.abs(p - 0.5) - 1;
    }

    /** Two quick metallic clacks: a magazine slapped into the gun. */
    static double[] pickupGatling() {
        double[] out = buffer(0.3);
        Random noise = new Random(11);
        for (int i = 0; i < out.length; i++) {
            double t = i / (double) RATE;
            double second = t - 0.12;
            double clack = Math.exp(-t * 60) * (noise.nextDouble() * 2 - 1) * 0.6 + Math.sin(2 * Math.PI * 900 * t) * Math.exp(-t * 45) * 0.5;
            double clack2 = second > 0
                    ? Math.exp(-second * 50) * (noise.nextDouble() * 2 - 1) * 0.6 + Math.sin(2 * Math.PI * 1300 * second) * Math.exp(-second * 40) * 0.5
                    : 0;
            out[i] = clack + clack2;
        }
        return out;
    }

    /**
     * A gatling gun: a motor that spins up under a fast stream of sharp shots (20 a second), with a low thump
     * on each and a short spin-down at the end.
     */
    static double[] gatlingBurst() {
        double fire = 0.55;
        double[] out = buffer(fire + 0.25);
        Random noise = new Random(5);
        double motorPhase = 0;
        for (int i = 0; i < out.length; i++) {
            double t = i / (double) RATE;
            // motor: spins up for 0.12 s, keeps its speed while firing, winds down afterwards
            double speed = t < 0.12 ? t / 0.12 : t < fire ? 1.0 : Math.max(0, 1 - (t - fire) / 0.25);
            motorPhase += 2 * Math.PI * (40 + 90 * speed) / RATE;
            double motor = (Math.signum(Math.sin(motorPhase)) * 0.12 + Math.sin(motorPhase * 3) * 0.06) * Math.min(1, speed * 2)
                    * (t < fire ? 1.0 : Math.max(0, 1 - (t - fire) / 0.25));
            out[i] = motor;
        }
        // the shots: a sharp crack and a thump each, every 50 ms from the first spin-up
        for (double at = 0.06; at < fire; at += 0.05) {
            int start = (int) (at * RATE);
            for (int i = 0; i < (int) (0.045 * RATE) && start + i < out.length; i++) {
                double t = i / (double) RATE;
                out[start + i] += (noise.nextDouble() * 2 - 1) * Math.exp(-t * 130) * 0.9
                        + Math.sin(2 * Math.PI * (110 - 600 * t) * t) * Math.exp(-t * 70) * 0.6;
            }
        }
        return out;
    }

    /**
     * A deep bomb: a sharp crack, a sub-bass boom sliding down from 90 to 28 Hz, and a long low rumble of
     * filtered noise that dies away over a second and a half.
     */
    static double[] bombExplosion() {
        double[] out = buffer(1.6);
        Random noise = new Random(21);
        double boomPhase = 0;
        double rumble = 0;
        for (int i = 0; i < out.length; i++) {
            double t = i / (double) RATE;
            double freq = 28 + 62 * Math.exp(-t * 3.2);
            boomPhase += 2 * Math.PI * freq / RATE;
            double boom = Math.sin(boomPhase) * Math.exp(-t * 1.9) * 1.0 + Math.sin(boomPhase * 2) * Math.exp(-t * 3.5) * 0.35;
            double white = noise.nextDouble() * 2 - 1;
            rumble += 0.035 * (white - rumble);   // low-pass: only the deep part of the noise
            double rumbleLevel = rumble * 9.0 * Math.exp(-t * 2.3) * Math.min(1, t / 0.03);
            double crack = white * Math.exp(-t * 70) * 0.55;
            out[i] = boom + rumbleLevel + crack;
        }
        return out;
    }

    /** A piece of wall going up: two wooden hammer knocks, the second a little softer, with a short rattle of nails. */
    static double[] wallBuild() {
        double[] out = buffer(0.5);
        Random noise = new Random(61);
        for (int knock = 0; knock < 2; knock++) {
            int start = (int) ((0.02 + knock * 0.2) * RATE);
            double level = knock == 0 ? 1.0 : 0.7;
            for (int i = 0; i < (int) (0.16 * RATE) && start + i < out.length; i++) {
                double t = i / (double) RATE;
                double wood = Math.sin(2 * Math.PI * (210 - 90 * t) * t) * Math.exp(-t * 38)
                        + 0.5 * Math.sin(2 * Math.PI * 560 * t) * Math.exp(-t * 55);
                double tap = (noise.nextDouble() * 2 - 1) * Math.exp(-t * 160) * 0.7;
                out[start + i] += level * (wood + tap);
            }
        }
        return out;
    }

    /**
     * An rpg leaving its tube: a hard hollow thump, then a rocket motor that ignites with a roaring hiss and
     * fades as it flies away.
     */
    static double[] rpgLaunch() {
        double[] out = buffer(1.1);
        Random noise = new Random(41);
        double hiss = 0;
        double phase = 0;
        for (int i = 0; i < out.length; i++) {
            double t = i / (double) RATE;
            double thump = Math.sin(2 * Math.PI * (95 - 60 * t) * t) * Math.exp(-t * 16) * 1.0
                    + (noise.nextDouble() * 2 - 1) * Math.exp(-t * 60) * 0.5;
            double white = noise.nextDouble() * 2 - 1;
            hiss += (0.12 + 0.2 * Math.min(1, t / 0.3)) * (white - hiss);
            double motor = t > 0.04 ? hiss * 1.1 * Math.min(1, (t - 0.04) / 0.08) * Math.exp(-(t - 0.04) * 2.6) : 0;
            phase += 2 * Math.PI * (70 + 40 * Math.exp(-t * 4)) / RATE;
            double roar = t > 0.04 ? Math.signum(Math.sin(phase)) * 0.1 * Math.exp(-(t - 0.04) * 3.2) : 0;
            out[i] = thump + motor + roar;
        }
        return out;
    }

    /** A rocket going off: a sharp crack, a short punchy boom and a fast-dying rumble (quicker than a bomb). */
    static double[] rpgExplosion() {
        double[] out = buffer(0.9);
        Random noise = new Random(43);
        double boomPhase = 0;
        double rumble = 0;
        for (int i = 0; i < out.length; i++) {
            double t = i / (double) RATE;
            boomPhase += 2 * Math.PI * (45 + 85 * Math.exp(-t * 9)) / RATE;
            double boom = Math.sin(boomPhase) * Math.exp(-t * 5.5);
            double white = noise.nextDouble() * 2 - 1;
            rumble += 0.08 * (white - rumble);
            double crack = white * Math.exp(-t * 55) * 0.9;
            out[i] = boom + rumble * 4.5 * Math.exp(-t * 5.0) * Math.min(1, t / 0.01) + crack;
        }
        return out;
    }

    /** A hit on the tower: a dull stone knock with a ring of metal and a few chips. */
    static double[] towerHit() {
        double[] out = buffer(0.28);
        Random noise = new Random(47);
        for (int i = 0; i < out.length; i++) {
            double t = i / (double) RATE;
            double knock = Math.sin(2 * Math.PI * (160 - 80 * t) * t) * Math.exp(-t * 28);
            double ring = (Math.sin(2 * Math.PI * 820 * t) + 0.6 * Math.sin(2 * Math.PI * 1370 * t)) * Math.exp(-t * 22) * 0.25;
            double chips = (noise.nextDouble() * 2 - 1) * Math.exp(-t * 90) * 0.5;
            out[i] = knock + ring + chips;
        }
        return out;
    }

    /**
     * A tower collapsing: a huge boom, a long low rumble of falling masonry and a clatter of rubble landing after it.
     */
    static double[] towerDestroyed() {
        double[] out = buffer(2.4);
        Random noise = new Random(53);
        double boomPhase = 0;
        double rumble = 0;
        for (int i = 0; i < out.length; i++) {
            double t = i / (double) RATE;
            boomPhase += 2 * Math.PI * (24 + 70 * Math.exp(-t * 2.6)) / RATE;
            double boom = (Math.sin(boomPhase) + 0.4 * Math.sin(boomPhase * 2)) * Math.exp(-t * 1.5);
            double white = noise.nextDouble() * 2 - 1;
            rumble += 0.04 * (white - rumble);
            double crumble = rumble * 8.5 * Math.exp(-t * 1.2) * Math.min(1, t / 0.03);
            double crack = white * Math.exp(-t * 45) * 0.6;
            // rubble clatter: random short ticks that thin out
            double clatter = noise.nextDouble() < 0.012 * Math.exp(-t * 0.9) && t > 0.3
                    ? (noise.nextDouble() * 2 - 1) * 0.8 : 0;
            out[i] = boom + crumble + crack + clatter;
        }
        return out;
    }

    /** A magical zap: a tone that sweeps up and then drops away, shimmering, over a short airy whoosh. */
    static double[] teleport() {
        double[] out = buffer(0.7);
        Random noise = new Random(33);
        double phase = 0;
        double air = 0;
        for (int i = 0; i < out.length; i++) {
            double t = i / (double) RATE;
            // up for the first 0.3 s (300 -> 1800 Hz), then down (1800 -> 200 Hz)
            double freq = t < 0.3 ? 300 + 1500 * (t / 0.3) : 1800 - 1600 * ((t - 0.3) / 0.4);
            phase += 2 * Math.PI * freq / RATE;
            double shimmer = 0.75 + 0.25 * Math.sin(2 * Math.PI * 28 * t);
            double tone = (Math.sin(phase) * 0.5 + Math.sin(phase * 2.01) * 0.2) * shimmer;
            air += 0.25 * ((noise.nextDouble() * 2 - 1) - air);
            double whoosh = air * 0.5 * Math.sin(Math.PI * Math.min(1, t / 0.7));
            out[i] = (tone + whoosh) * envelope(t, 0.01, 0.7);
        }
        return out;
    }

    static double[] laserFire() {
        double[] out = buffer(0.2);
        double phase = 0;
        for (int i = 0; i < out.length; i++) {
            double t = i / (double) RATE;
            double freq = 1900 * Math.pow(0.15, t / 0.2);
            phase += 2 * Math.PI * freq / RATE;
            out[i] = (Math.signum(Math.sin(phase)) * 0.4 + Math.sin(phase) * 0.3) * envelope(t, 0.002, 0.2);
        }
        return out;
    }

    static double[] sledgehammerSwing() {
        double[] out = buffer(0.5);
        Random noise = new Random(3);
        double low = 0;
        for (int i = 0; i < out.length; i++) {
            double t = i / (double) RATE;
            // whoosh: band-limited noise swelling then cut by the hit
            double white = noise.nextDouble() * 2 - 1;
            double cutoff = 0.05 + 0.25 * Math.sin(Math.PI * Math.min(1, t / 0.32));
            low += cutoff * (white - low);
            double whoosh = t < 0.32 ? low * 1.4 * Math.sin(Math.PI * t / 0.32) : 0;
            // heavy thud at the end of the swing
            double th = t - 0.3;
            double thud = th > 0 ? (Math.sin(2 * Math.PI * (70 - 30 * th) * th) * 0.9
                    + white * 0.35 * Math.exp(-th * 40)) * Math.exp(-th * 11) : 0;
            out[i] = whoosh + thud;
        }
        return out;
    }

    static double[] weaponSwitch() {
        double[] out = buffer(0.07);
        for (int i = 0; i < out.length; i++) {
            double t = i / (double) RATE;
            out[i] = (t < 0.03 ? Math.sin(2 * Math.PI * 1400 * t) : Math.sin(2 * Math.PI * 2100 * t))
                    * 0.5 * envelope(t, 0.002, 0.07);
        }
        return out;
    }

    /** Quick attack, then exponential-ish fade to zero at {@code length}. */
    private static double envelope(double t, double attack, double length) {
        if (t < 0 || t > length) {
            return 0;
        }
        double up = Math.min(1, t / attack);
        double down = Math.pow(1 - t / length, 1.6);
        return up * down;
    }

    private static double[] buffer(double seconds) {
        return new double[(int) (seconds * RATE)];
    }

    private static void write(Path dir, String name, double[] samples) throws IOException {
        double peak = 1e-9;
        for (double s : samples) {
            peak = Math.max(peak, Math.abs(s));
        }
        double gain = 0.85 / peak;   // normalise every effect to the same loudness
        ByteBuffer pcm = ByteBuffer.allocate(samples.length * 2).order(ByteOrder.LITTLE_ENDIAN);
        for (double s : samples) {
            pcm.putShort((short) Math.round(Math.max(-1, Math.min(1, s * gain)) * Short.MAX_VALUE));
        }
        byte[] data = pcm.array();
        ByteBuffer header = ByteBuffer.allocate(44).order(ByteOrder.LITTLE_ENDIAN);
        header.put("RIFF".getBytes()).putInt(36 + data.length).put("WAVE".getBytes());
        header.put("fmt ".getBytes()).putInt(16).putShort((short) 1).putShort((short) 1)
                .putInt(RATE).putInt(RATE * 2).putShort((short) 2).putShort((short) 16);
        header.put("data".getBytes()).putInt(data.length);
        ByteArrayOutputStream file = new ByteArrayOutputStream();
        file.write(header.array());
        file.write(data);
        Files.write(dir.resolve(name), file.toByteArray());
        System.out.println("wrote sounds/" + name + " (" + data.length / 2 + " samples)");
    }
}
