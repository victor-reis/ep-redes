package client;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

public class Main {
    public static String PATH = "/home/victor-reis/Pictures/";
    public static String FILE_NAME = "dog.jpg";
    public static String READ_PATH = PATH + FILE_NAME;
    public static String IP_DO_SERVER = "localhost";

    public static int COUNT = 0;
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    public static void main(String[] args) throws Exception {
        getUserInputs();
        final File file = new File(READ_PATH);
        TCPClient.sendImageToServer(file);

       }

    private static void getUserInputs() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Qual o ip do servidor ? [Dica, se estiver rodando na mesma máquina, digite localhost]: ");
        IP_DO_SERVER = scanner.nextLine();
        System.out.println("Qual o nome do arquivo: ");
        FILE_NAME = scanner.nextLine();
        System.out.println("insira o caminho para o arquivo: [ex: '/home/user/imagens/' ]");
        PATH = scanner.nextLine();
        READ_PATH = PATH + FILE_NAME;
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