package com.justb.messages;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by ben on 06/01/15.
 * <p/>
 * JGUILibrary
 */
public class ImageExchange implements Serializable {

    private BufferedImage image;

    private String name;

    public ImageExchange(BufferedImage image, String name) {
        this.image = image;
        this.name = name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    private void writeObject(java.io.ObjectOutputStream out)throws IOException {
        out.writeObject(name);
        ImageIO.write(image, "jpeg", ImageIO.createImageOutputStream(out));
    }

    private void readObject(java.io.ObjectInputStream in)throws IOException, ClassNotFoundException{
        name=(String)in.readObject();
        image=ImageIO.read(ImageIO.createImageInputStream(in));
    }
}
