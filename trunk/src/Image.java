import java.io.*;
import javax.imageio.*;
import java.awt.Image;
import java.awt.image.*;
import java.net.URL;

public class ImageDownload {


	public static void main(String[] args) {
BufferedImage image = null;
		try {


    // Read from a URL
    URL url = new URL("http://dev.caringmeals.com/images/logo.png");
    image = ImageIO.read(url);


    File file = new File("newimage.png");
    ImageIO.write(image, "png", file);

} catch (IOException e) {
System.out.println("error");
}


	}

}

