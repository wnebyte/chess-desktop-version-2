package ui.util;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;

import java.awt.image.BufferedImage;

public class BufferedImageTranscoder extends ImageTranscoder
{
    private BufferedImage image = null;

    @Override
    public BufferedImage createImage(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    public void writeImage(BufferedImage img, TranscoderOutput output) throws TranscoderException {
        this.image = img;
    }

    public BufferedImage getBufferedImage() {
        return image;
    }
}
