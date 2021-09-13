package ui.builder;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import ui.util.BufferedImageTranscoder;

import java.io.IOException;
import java.io.InputStream;

public class SVGFactory
{
    private final String path;

    public SVGFactory(String path) {
        this.path = path;
    }

    public Image load()
    {
        Image image = null;

        BufferedImageTranscoder transcoder = new BufferedImageTranscoder();

        try (InputStream file = getClass().getResourceAsStream(path)) {
            TranscoderInput transIn = new TranscoderInput(file);
            try {
                transcoder.transcode(transIn, null);
                image = SwingFXUtils.toFXImage(transcoder.getBufferedImage(), null);
            } catch (TranscoderException ex) {
                ex.printStackTrace();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return image;
    }
}
