package media;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio implements Runnable
{
    private final File file;

    public Audio(final String path)
    {
        this.file = new File(path);

        if ((!file.exists()) || (!file.canRead())) {
            throw new IllegalArgumentException(
                    "file does not exist, or cannot be read."
            );
        }
    }

    @Override
    public void run()
    {
        try
        {
            Clip clip = AudioSystem.getClip();
            AudioInputStream in =
                    AudioSystem.getAudioInputStream(file);
            clip.open(in);
            clip.start();
        }
        catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }
}
