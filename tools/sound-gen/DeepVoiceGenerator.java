import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Turns a recording of a spoken phrase (see {@code RecordVoice.ps1}) into a deep, booming voice: the silence
 * round the word is cut, the pitch drops by a quarter (so the word also comes out slower and heavier), a sub-harmonic
 * layer an octave lower adds weight, the top is rolled off and a short room echo is added. Deterministic.
 *
 * <pre>java tools/sound-gen/DeepVoiceGenerator.java raw-teleported.wav [soundDir]</pre>
 *
 * writes {@code teleported.wav} into {@code soundDir} (default: the game's sounds folder).
 */
public final class DeepVoiceGenerator {

    static final double PITCH = 0.75;
    private static final double SUB_OCTAVE_MIX = 0.7;
    private static final double LOW_PASS_HZ = 3600;
    private static final double PEAK = 0.9;

    private DeepVoiceGenerator() {
    }

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException {
        if (args.length < 1) {
            System.err.println("usage: DeepVoiceGenerator <raw.wav> [soundDir]");
            System.exit(2);
        }
        Path outDir = Path.of(args.length > 1 ? args[1] : "maze-common-frontend/src/main/resources/main/game/maze/sounds");
        Files.createDirectories(outDir);
        AudioInputStream in = AudioSystem.getAudioInputStream(new File(args[0]));
        float rate = in.getFormat().getSampleRate();
        double[] raw = toSamples(in);
        double[] deep = deepen(trim(raw, rate), rate);
        write(outDir.resolve("teleported.wav"), deep, (int) rate);
        System.out.println("wrote teleported.wav (" + deep.length + " samples at " + (int) rate + " Hz)");
    }

    static double[] deepen(double[] voice, double rate) {
        double[] low = resample(voice, PITCH);
        double[] sub = subOctave(low, rate);
        double[] mixed = new double[low.length + (int) (0.3 * rate)];
        for (int i = 0; i < low.length; i++) {
            mixed[i] += low[i] + sub[i] * SUB_OCTAVE_MIX;
        }
        // roll off the top: one-pole low pass
        mixed = lowPass(mixed, LOW_PASS_HZ, rate);
        // a short room echo
        double[] echoed = mixed.clone();
        int[] delays = {(int) (0.045 * rate), (int) (0.083 * rate), (int) (0.12 * rate)};
        double[] gains = {0.30, 0.20, 0.12};
        for (int d = 0; d < delays.length; d++) {
            for (int i = delays[d]; i < echoed.length; i++) {
                echoed[i] += mixed[i - delays[d]] * gains[d];
            }
        }
        double peak = 1e-9;
        for (double s : echoed) {
            peak = Math.max(peak, Math.abs(s));
        }
        for (int i = 0; i < echoed.length; i++) {
            echoed[i] *= PEAK / peak;
        }
        // fade the very end so the echo never clicks
        int fade = (int) (0.05 * rate);
        for (int i = 0; i < fade && i < echoed.length; i++) {
            echoed[echoed.length - 1 - i] *= i / (double) fade;
        }
        return echoed;
    }

    /**
     * A layer an octave below the voice, of the same length: the voice is low-passed to its body, then every
     * other half cycle is flipped (a sub-harmonic generator), and the result is low-passed again.
     */
    static double[] subOctave(double[] voice, double rate) {
        double[] body = lowPass(voice, 420, rate);
        double[] sub = new double[body.length];
        double sign = 1;
        for (int i = 1; i < body.length; i++) {
            if (body[i - 1] < 0 && body[i] >= 0) {
                sign = -sign;   // every second rising zero crossing flips: half the frequency
            }
            sub[i] = body[i] * sign;
        }
        return lowPass(sub, 260, rate);
    }

    static double[] lowPass(double[] samples, double hz, double rate) {
        double alpha = 1 - Math.exp(-2 * Math.PI * hz / rate);
        double[] out = new double[samples.length];
        double state = 0;
        for (int i = 0; i < samples.length; i++) {
            state += alpha * (samples[i] - state);
            out[i] = state;
        }
        return out;
    }

    /** Plays the samples at {@code factor} of the speed: lower pitch, longer (linear interpolation). */
    static double[] resample(double[] samples, double factor) {
        int length = (int) Math.floor((samples.length - 1) / factor);
        double[] out = new double[Math.max(0, length)];
        for (int i = 0; i < out.length; i++) {
            double position = i * factor;
            int index = (int) position;
            double fraction = position - index;
            out[i] = samples[index] * (1 - fraction) + samples[Math.min(index + 1, samples.length - 1)] * fraction;
        }
        return out;
    }

    /** Cuts the silence before and after the word, keeping a little air. */
    static double[] trim(double[] samples, float rate) {
        double peak = 1e-9;
        for (double s : samples) {
            peak = Math.max(peak, Math.abs(s));
        }
        double threshold = peak * 0.03;
        int first = 0;
        while (first < samples.length && Math.abs(samples[first]) < threshold) {
            first++;
        }
        int last = samples.length - 1;
        while (last > first && Math.abs(samples[last]) < threshold) {
            last--;
        }
        int pad = (int) (0.03 * rate);
        int from = Math.max(0, first - pad);
        int to = Math.min(samples.length, last + pad);
        double[] out = new double[Math.max(1, to - from)];
        System.arraycopy(samples, from, out, 0, out.length);
        return out;
    }

    private static double[] toSamples(AudioInputStream in) throws IOException {
        AudioFormat target = new AudioFormat(in.getFormat().getSampleRate(), 16, 1, true, false);
        try (AudioInputStream pcm = AudioSystem.getAudioInputStream(target, in)) {
            byte[] bytes = pcm.readAllBytes();
            ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
            double[] samples = new double[bytes.length / 2];
            for (int i = 0; i < samples.length; i++) {
                samples[i] = buffer.getShort() / 32768.0;
            }
            return samples;
        }
    }

    private static void write(Path file, double[] samples, int rate) throws IOException {
        ByteBuffer pcm = ByteBuffer.allocate(samples.length * 2).order(ByteOrder.LITTLE_ENDIAN);
        for (double s : samples) {
            pcm.putShort((short) Math.round(Math.max(-1, Math.min(1, s)) * Short.MAX_VALUE));
        }
        AudioFormat format = new AudioFormat(rate, 16, 1, true, false);
        try (AudioInputStream stream = new AudioInputStream(new ByteArrayInputStream(pcm.array()), format, samples.length)) {
            AudioSystem.write(stream, AudioFileFormat.Type.WAVE, file.toFile());
        }
    }
}
