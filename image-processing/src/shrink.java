
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class shrink {

    public static void shrink(BufferedImage img, int m, int n) throws IOException {
        float k1 = (float)m/img.getWidth();
        float k2 = (float)n/img.getHeight();

        if(k1 >1 || k2>1) {//如果k1 >1 || k2>1则是图片放大，不是缩小
            System.err.println("this is shrink image funcation, please set k1<=1 and k2<=1！");

        }
        float ii = 1/k1;	//采样的行间距
        float jj = 1/k2; //采样的列间距
        int dd = (int)(ii*jj);
        //int m=0 , n=0;
        int imgType = img.getType();
        int w = img.getWidth();
        int h = img.getHeight();
        int m1 = (int) (k1*w);
        int n1 = (int) (k2*h);
        int[] pix = new int[w*h];
        pix = img.getRGB(0, 0, w, h, pix, 0, w);
        System.out.println(w + " * " + h);
        System.out.println(m + " * " + n);
        int[] newpix = new int[m*n];

        for(int j=0; j<n; j++) {
            for(int i=0; i<m; i++) {
                int r = 0, g=0, b=0;
                ColorModel cm = ColorModel.getRGBdefault();
                for(int k=0; k<(int)jj; k++) {
                    for(int l=0; l<(int)ii; l++) {
                        r = r + cm.getRed(pix[(int)(jj*j+k)*w + (int)(ii*i+l)]);
                        g = g + cm.getGreen(pix[(int)(jj*j+k)*w + (int)(ii*i+l)]);
                        b = b + cm.getBlue(pix[(int)(jj*j+k)*w + (int)(ii*i+l)]);
                    }
                }
                r = r/dd;
                g = g/dd;
                b = b/dd;
                newpix[j*m + i] = 255<<24 | r<<16 | g<<8 | b;
                //255<<24 | r<<16 | g<<8 | b 颜色的RGB在内存中是
                //以二进制的形式保存的，从右到左1-8位表示blue，9-16表示green，17-24表示red
                //所以"<<24" "<<16" "<<8"分别表示左移24,16,8位

                //newpix[j*m + i] = new Color(r,g,b).getRGB();
            }
        }

        BufferedImage imgOut = new BufferedImage( m, n, imgType);

        imgOut.setRGB(0, 0, m, n, newpix, 0, m);
        File outFile = new File("newshrinkXbb.jpg");
        ImageIO.write(imgOut,"jpg",outFile);
    }


}
