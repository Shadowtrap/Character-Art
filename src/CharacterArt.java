import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class CharacterArt {

    private final double SCALE_FACTOR = 0.30;
    private int width, height;
    private String path;
    private BufferedImage original;

    public CharacterArt(String path){
        this.path = path;
        original = null;
    }

    public void produce(){
        try {
            original = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        width = (int) (original.getWidth() * SCALE_FACTOR);
        height = (int) (original.getHeight() * SCALE_FACTOR);

        Image newImage = original.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        original = new BufferedImage(width, height, TYPE_INT_RGB);

        Graphics g = original.createGraphics();
        g.drawImage(newImage, 0, 0, null);
        g.dispose();

        int[][] img = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                img[x][y] = original.getRGB(x,y);
            }
        }

        int[][][] converted = ConvertImage.convertBW(img, width, height);

        BufferedImage bwImage = new BufferedImage(width, height, TYPE_INT_RGB);
        BufferedImage bwInvertedImage = new BufferedImage(width, height, TYPE_INT_RGB);
        BufferedImage invertedImage = new BufferedImage(width, height, TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bwImage.setRGB(x,y,converted[ConvertImage.BW][x][y]);
                bwInvertedImage.setRGB(x,y,converted[ConvertImage.INVERTED_BW][x][y]);
                invertedImage.setRGB(x,y,converted[ConvertImage.INVERTED_COLOR][x][y]);
            }
        }

        RenderedImage renderedImageBW = bwImage;
        RenderedImage renderedImageBWInverted = bwInvertedImage;
        RenderedImage renderedImageInverted = invertedImage;

        File bW = new File("src\\Outcome\\BW.png");
        File bWInverted = new File("src\\Outcome\\BW_Inverted.png");
        File inverted = new File("src\\Outcome\\Color_Inverted.png");

        try {
            ImageIO.write(renderedImageBW, "png", bW);
            ImageIO.write(renderedImageBWInverted, "png", bWInverted);
            ImageIO.write(renderedImageInverted, "png", inverted);
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        try {
            FileWriter myWriter = new FileWriter("src\\Outcome\\Char.txt");
            FileWriter myWriterInverted = new FileWriter("src\\Outcome\\Inverted_Char.txt");

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    myWriter.write((char)converted[ConvertImage.CHAR][x][y] + " ");
                    myWriterInverted.write((char)converted[ConvertImage.INVERTED_CHAR][x][y] + " ");
                    System.out.print((char)converted[ConvertImage.CHAR][x][y] + " ");
                }
                myWriter.write("\n");
                myWriterInverted.write("\n");
                System.out.print("\n");
            }

            myWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        CharacterArt art = new CharacterArt("");
        art.produce();
    }
}
