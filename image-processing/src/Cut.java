import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Cut {
    public static void cut(){
        try{
            BufferedImage image = ImageIO.read( new File("Xbb.jpg"));
            int width = image.getWidth();
            int height = image.getHeight();
            int[] imageArray = new int[width*height];
            image.getRGB(0, 0,width,height,imageArray,0,width);
            BufferedImage imageNew = new BufferedImage(width/2,height,BufferedImage.TYPE_INT_RGB);
            imageNew.setRGB(0,0,width/2,height,imageArray,0,width);
            File outFile = new File("newcutXbb.jpg");
            ImageIO.write(imageNew,"jpg",outFile);
            for(int i=0;i<width/2;i++){
                for(int k=0;k<height;k++){
                    GetARGB.getARGB(imageNew.getRGB(i, k));
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}



