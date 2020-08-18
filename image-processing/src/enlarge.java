import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class enlarge {
    /**
     * 双线性插值法图像的放大
     * @param img 要缩小的图像对象
     * @param k1 要缩小的列比列
     * @param k2 要缩小的行比列
     * @throws IOException
     */
    public static void amplify(BufferedImage img, float k1, float k2) throws IOException {
        if(k1 <1 || k2<1) {//如果k1 <1 || k2<1则是图片缩小，不是放大
            System.err.println("this is shrink image funcation, please set k1<=1 and k2<=1！");
        }
        float ii = 1/k1;	//采样的行间距
        float jj = (1/k2); //采样的列间距
        int dd = (int)(ii*jj);
        //int m=0 , n=0;
        int imgType = img.getType();
        int w = img.getWidth();		//原图片的宽
        int h = img.getHeight();	//原图片的宽
        int m2 = Math.round(k1*w);	//放大后图片的宽
        int n2 = Math.round(k2*h);	//放大后图片的宽
        int[] pix = new int[w*h];
        pix = img.getRGB(0, 0, w, h, pix, 0, w);
		/*System.out.println(w + " * " + h);
		System.out.println(m + " * " + n);*/
        int[] newpix = new int[m2*n2];

        for(int j=0; j<h-1; j++){
            for(int i=0; i<w-1; i++) {
                int x0 = Math.round(i*k1);
                int y0 = Math.round(j*k2);
                int x1, y1;
                if(i == w-2) {
                    x1 = m2-1;
                } else {
                    x1 = Math.round((i+1)*k1);
                }
                if(j == h-2) {
                    y1 = n2-1;
                } else {
                    y1 = Math.round((j+1)*k2);
                }
                int d1 = x1 - x0;
                int d2 = y1 - y0;
                if(0 == newpix[y0*m2 + x0]) {
                    newpix[y0*m2 + x0] =  pix[j*w+i];
                }
                if(0 == newpix[y0*m2 + x1]) {
                    if(i == w-2) {
                        newpix[y0*m2 + x1] = pix[j*w+w-1];
                    } else {
                        newpix[y0*m2 + x1] =  pix[j*w+i+1];
                    }
                }
                if(0 == newpix[y1*m2 + x0]){
                    if(j == h-2) {
                        newpix[y1*m2 + x0] = pix[(h-1)*w+i];
                    } else {
                        newpix[y1*m2 + x0] =  pix[(j+1)*w+i];
                    }
                }
                if(0 == newpix[y1*m2 + x1]) {
                    if(i==w-2 && j==h-2) {
                        newpix[y1*m2 + x1] = pix[(h-1)*w+w-1];
                    } else {
                        newpix[y1*m2 + x1] = pix[(j+1)*w+i+1];
                    }
                }
                int r, g, b;
                float c;
                ColorModel cm = ColorModel.getRGBdefault();
                for(int l=0; l<d2; l++) {
                    for(int k=0; k<d1; k++) {
                        if(0 == l) {
                            //f(x,0) = f(0,0) + c1*(f(1,0)-f(0,0))
                            if(j<h-1 && newpix[y0*m2 + x0 + k] == 0) {
                                c = (float)k/d1;
                                r = cm.getRed(newpix[y0*m2 + x0]) + (int)(c*(cm.getRed(newpix[y0*m2 + x1]) - cm.getRed(newpix[y0*m2 + x0])));//newpix[(y0+l)*m + k]
                                g = cm.getGreen(newpix[y0*m2 + x0]) + (int)(c*(cm.getGreen(newpix[y0*m2 + x1]) - cm.getGreen(newpix[y0*m2 + x0])));
                                b = cm.getBlue(newpix[y0*m2 + x0]) + (int)(c*(cm.getBlue(newpix[y0*m2 + x1]) - cm.getBlue(newpix[y0*m2 + x0])));
                                newpix[y0*m2 + x0 + k] = new Color(r,g,b).getRGB();
                            }
                            if(j+1<h && newpix[y1*m2 + x0 + k] == 0) {
                                c = (float)k/d1;
                                r = cm.getRed(newpix[y1*m2 + x0]) + (int)(c*(cm.getRed(newpix[y1*m2 + x1]) - cm.getRed(newpix[y1*m2 + x0])));
                                g = cm.getGreen(newpix[y1*m2 + x0]) + (int)(c*(cm.getGreen(newpix[y1*m2 + x1]) - cm.getGreen(newpix[y1*m2 + x0])));
                                b = cm.getBlue(newpix[y1*m2 + x0]) + (int)(c*(cm.getBlue(newpix[y1*m2 + x1]) - cm.getBlue(newpix[y1*m2 + x0])));
                                newpix[y1*m2 + x0 + k] = new Color(r,g,b).getRGB();
                            }
                            //System.out.println(c);
                        } else {
                            //f(x,y) = f(x,0) + c2*f(f(x,1)-f(x,0))
                            c = (float)l/d2;
                            r = cm.getRed(newpix[y0*m2 + x0+k]) + (int)(c*(cm.getRed(newpix[y1*m2 + x0+k]) - cm.getRed(newpix[y0*m2 + x0+k])));
                            g = cm.getGreen(newpix[y0*m2 + x0+k]) + (int)(c*(cm.getGreen(newpix[y1*m2 + x0+k]) - cm.getGreen(newpix[y0*m2 + x0+k])));
                            b = cm.getBlue(newpix[y0*m2 + x0+k]) + (int)(c*(cm.getBlue(newpix[y1*m2 + x0+k]) - cm.getBlue(newpix[y0*m2 + x0+k])));
                            newpix[(y0+l)*m2 + x0 + k] = new Color(r,g,b).getRGB();
                            //System.out.println((int)(c*(cm.getRed(newpix[y1*m + x0+k]) - cm.getRed(newpix[y0*m + x0+k]))));
                        }
                    }
                    if(i==w-2 || l==d2-1) {	//最后一列的计算
                        //f(1,y) = f(1,0) + c2*f(f(1,1)-f(1,0))
                        c = (float)l/d2;
                        r = cm.getRed(newpix[y0*m2 + x1]) + (int)(c*(cm.getRed(newpix[y1*m2 + x1]) - cm.getRed(newpix[y0*m2 + x1])));
                        g = cm.getGreen(newpix[y0*m2 + x1]) + (int)(c*(cm.getGreen(newpix[y1*m2 + x1]) - cm.getGreen(newpix[y0*m2 + x1])));
                        b = cm.getBlue(newpix[y0*m2 + x1]) + (int)(c*(cm.getBlue(newpix[y1*m2 + x1]) - cm.getBlue(newpix[y0*m2 + x1])));
                        newpix[(y0+l)*m2 + x1] = new Color(r,g,b).getRGB();
                    }
                }
            }
        }
		/*
		for(int j=0; j<50; j++){
			for(int i=0; i<50; i++) {
				System.out.print(new Color(newpix[j*m + i]).getRed() + "\t");
			}
			System.out.println();
		}
		*/
        BufferedImage imgOut = new BufferedImage( m2, n2, imgType);

        imgOut.setRGB(0, 0, m2, n2, newpix, 0, m2);
        File outFile = new File("newenlargeXbb.jpg");
        ImageIO.write(imgOut,"jpg",outFile);
    }
}
