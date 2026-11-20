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
        write(dir, "laser-fire.wav", laserFire());
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
