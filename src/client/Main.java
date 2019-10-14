package client;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {
    public static final String READ_PATH = "/home/victor-reis/Pictures/pés.jpg";
    public static final String WRITE_PATH = "/home/victor-reis/Pictures/";
    public static int COUNT = 0;
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    public static void main(String[] args) throws Exception {

        final File file = new File(READ_PATH);
        TCPClient.sendImageToServer(file);

       }

    private static void showImage(BufferedImage image){
        ImageIcon imageIcon = new ImageIcon(image);
        JLabel jlabel = new JLabel(imageIcon);

        JPanel painel = new JPanel();
        painel.add(jlabel);

        JFrame janela = new JFrame("ALGUMA IMAGEM");

        janela.add(painel);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.pack();
        janela.setVisible(true);
    }

}