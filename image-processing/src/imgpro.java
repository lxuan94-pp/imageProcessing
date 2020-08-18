import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class imgpro {
    public static void main(String[] args) throws IOException {
        Cut.cut();
        BufferedImage image = ImageIO.read( new File("Xbb.jpg"));
        shrink.shrink(image, 200, 200);
        enlarge.amplify(image, 2, 2);


    }


}