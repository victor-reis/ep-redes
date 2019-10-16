package server;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class Main {
    public static String PATH = "/home/victor-reis/Music/";
    public static String FILE_NAME = "image-in-server.jpg";
    public static String FILE_PATH = PATH + FILE_NAME;
    public static int COUNT = 0;
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    private static imageEditor imageEditor = new imageEditor();


    public static void main(String[] args) throws Exception {
        TCPServer.start();
    }

    public static void editImage() throws Exception {
        final Image img = readImageRecievedFromClient();

        BufferedImage tempJPG = (BufferedImage)img;

        saveImage(tempJPG);
        showImage(tempJPG);

        tempJPG = imageEditor.resizeImage(img, WIDTH, HEIGHT);

        saveImage(tempJPG);
        showImage(tempJPG);

        tempJPG = imageEditor.toBlackAndWhite(tempJPG);

        saveImage(tempJPG);
        showImage(tempJPG);

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

    private static Image readImageRecievedFromClient(){
        Image img = null;
        try {
            File f = new File(FILE_PATH);
            img = ImageIO.read(f);
        } catch (Exception e) {
            System.out.println("provavelmente O FILE_PATH está errado");
        }
        return img;
    }

    private static void saveImage(BufferedImage image) throws Exception {
        COUNT++;
        File newFileJPG =  new File(PATH + COUNT + FILE_NAME);
        try{ImageIO.write(image, "jpg", newFileJPG);}
        catch (Exception e){
            System.out.println("deu caquinha na escrita");
            throw e;
        }
    }
}