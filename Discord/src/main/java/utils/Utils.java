package utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Utils {
	
	public static BufferedImage convertBlobToImage(byte[] data) {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		BufferedImage image = null;
	    try {
			image = ImageIO.read(bis);
		} catch(IOException e) {
			e.printStackTrace();
		}
	    return image;
	}
	
}
