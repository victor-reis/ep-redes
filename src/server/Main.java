package server;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class Main {
    public static final String READ_PATH = "/home/victor-reis/Pictures/pés-server.jpg";
    public static final String WRITE_PATH = "/home/victor-reis/Pictures/";
    public static int COUNT = 0;
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    private static imageEditor imageEditor = new imageEditor();


    public static void main(String[] args) throws Exception {

        final Image img = recieveImageFromClient();

        BufferedImage tempJPG = null;

        showImage((BufferedImage)img);

        tempJPG = imageEditor.resizeImage(img, WIDTH, HEIGHT);

        responseImageToClient(tempJPG);

        tempJPG = imageEditor.toBlackAndWhite(tempJPG);

        responseImageToClient(tempJPG);

    }

    private static void showImage(BufferedImage image){
        ImageIcon imageIcon = new ImageIcon(image);
        JLabel jlabel = new JLabel(imageIcon);

        JPanel painel = new JPanel();
        painel.add(jlabel);

        JFrame janela = new JFrame("ALGUMA IMAGEM");

        janela.add(painel);
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janela.pack();
        janela.setLocation(15 + 100*COUNT,15 + 50*COUNT);
        janela.setVisible(true);
    }

    private static Image recieveImageFromClient(){
        Image img = null;
        try {
            img = ImageIO.read(new File(READ_PATH));
        } catch (Exception e) {
            System.out.println("provavelmente O READ_PATH está errado");
        }
        return img;
    }

    private static void responseImageToClient(BufferedImage image) throws Exception {
        COUNT++;
        File newFileJPG =  new File(WRITE_PATH + COUNT + "pés-server-edited.jpeg");
        try{ImageIO.write(image, "jpeg", newFileJPG);}
        catch (Exception e){
            System.out.println("deu caquinha na escrita");
            throw e;
        }

        showImage(image);
    }
}