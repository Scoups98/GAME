package model;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;

public class Music {
    public static void main(String[] args) {
        final Music player = new Music();
        player.play("D:\\\\CloudMusic\\\\电台节目\\\\库马达.wav");//The path to find the file
    }

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static Music instance;
    private boolean isPlaying = false;
    private boolean stopRequested = false;

    public static Music getInstance() {
        if (instance == null) {
            instance = new Music();
        }
        return instance;
    }


    public void play(String filePath) {
        final File file = new File(filePath);
        try (final AudioInputStream in = getAudioInputStream(file)) {
            final AudioFormat outFormat = getOutFormat(in.getFormat());
            final Info info = new Info(SourceDataLine.class, outFormat);
            try (final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
                if (line != null) {
                    line.open(outFormat);
                    line.start();
                    isPlaying = true;
                    stream(getAudioInputStream(outFormat, in), line);
                    line.drain();
                    line.stop();
                    isPlaying = false;
                }
            }
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void stop() {
        stopRequested = true;
        isPlaying = false;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    private AudioFormat getOutFormat(AudioFormat inFormat) {
        final int ch = inFormat.getChannels();
        final float rate = inFormat.getSampleRate();
        return new AudioFormat(PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
    }

    private void stream(AudioInputStream in, SourceDataLine line)
            throws IOException {
        final byte[] buffer = new byte[65536];
        while (!stopRequested){
            int n = in.read(buffer, 0, buffer.length);
            if (n ==-1){
                break;
            }
            line.write(buffer, 0, n);
        }
    }
}
