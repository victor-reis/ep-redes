package client;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedImage image = new BufferedImage(0,0,0);

        BufferedImage result;

        sendImageToServer(image);

        result = recieveImageFromServer();

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

    private static BufferedImage recieveImageFromServer(){
        return new BufferedImage(0,0,0);
    }

    private static void sendImageToServer(BufferedImage image) throws Exception {
    }
}