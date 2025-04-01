package Utils;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UtilityTool {
	//Simple load method
	public static BufferedImage loadImage(String path) throws IOException {
		return ImageIO.read(new File(Paths.get("res\\"+path).toAbsolutePath().toString()));
	}
	//Scale image to save us from scaling every time we need too
	public static BufferedImage scaleImage(BufferedImage original, int width, int height) {
		BufferedImage scaledImage = new BufferedImage(width,height,original.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(original,0,0,width,height,null);
		g2.dispose();
		
		return scaledImage;
	}
	//Image slicer for the animation sheets
	public static BufferedImage[] cutImagePiece(BufferedImage image , int length, int frameSize, double scale, int x, int y) throws IOException {
		y *= frameSize;
		BufferedImage[] returnArray = new BufferedImage[length];
		for(int i = 0; i<length; i++) {
			returnArray[i] = scaleImage(image.getSubimage((i+x)*frameSize, y, frameSize, frameSize), (int)(frameSize*scale), (int)(frameSize*scale));
		}
		return returnArray;
	}
	//Text file loader
	public static File loadFile(String path) throws IOException {
		return new File(Paths.get(path).toAbsolutePath().toString());
	}
}
