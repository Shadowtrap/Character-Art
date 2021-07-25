import java.awt.*;

public class ConvertImage {

    final static Color black = Color.BLACK;
    final static Color white = Color.WHITE;
    final static Color grey = Color.GRAY;
    final static Color darkGrey = Color.DARK_GRAY;
    final static Color lightGrey = Color.LIGHT_GRAY;
    final static int BW = 0;
    final static int CHAR = 1;
    final static int INVERTED_CHAR = 2;
    final static int INVERTED_BW = 3;
    final static int INVERTED_COLOR = 4;

    static Color currColor = null;
    static Color invertColor = null;
    static int red, green, blue, invertRed, invertGreen, invertBlue;
    static double avg;

    public static int[][][] convertBW(int[][] img, int width, int height) {
        int[][][] output = new int[5][width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                currColor = new Color(img[x][y]);
                red = currColor.getRed();
                green = currColor.getGreen();
                blue = currColor.getBlue();
                invertRed = 255 - red;
                invertGreen = 255 - green;
                invertBlue = 255 - blue;
                avg = (double) (red + green + blue) / 3;
                invertColor = new Color(invertRed, invertGreen, invertBlue);

                output[INVERTED_COLOR][x][y] = invertColor.getRGB();

                if(avg <= 51){
                    output[BW][x][y] = black.getRGB();
                    output[CHAR][x][y] = '*';
                    output[INVERTED_BW][x][y] = white.getRGB();
                    output[INVERTED_CHAR][x][y] = ' ';
                }
                else if (avg <= 102) {
                    output[BW][x][y] = darkGrey.getRGB();
                    output[CHAR][x][y] = '-';
                    output[INVERTED_BW][x][y] = lightGrey.getRGB();
                    output[INVERTED_CHAR][x][y] = '.';
                }
                else if(avg <= 153){
                    output[BW][x][y] = grey.getRGB();
                    output[CHAR][x][y] = ',';
                    output[INVERTED_BW][x][y] = grey.getRGB();
                    output[INVERTED_CHAR][x][y] = ',';
                }
                else if(avg <= 204){
                    output[BW][x][y] = lightGrey.getRGB();
                    output[CHAR][x][y] = '.';
                    output[INVERTED_BW][x][y] = darkGrey.getRGB();
                    output[INVERTED_CHAR][x][y] = '-';
                }
                else {
                    output[BW][x][y] = white.getRGB();
                    output[CHAR][x][y] = ' ';
                    output[INVERTED_BW][x][y] = black.getRGB();
                    output[INVERTED_CHAR][x][y] = '*';
                }
            }
        }

        return output;
    }
}
