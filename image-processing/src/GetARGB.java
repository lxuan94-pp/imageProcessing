
public class GetARGB {
    static void getARGB(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
		/*System.out.println("argb: " + alpha + "," + red + "," + green + "," + blue);*/
    }
}
