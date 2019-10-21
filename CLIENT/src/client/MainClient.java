package client;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

public class MainClient {
    public static String PATH = "/home/victor-reis/Pictures/";
    public static String FILE_NAME = "dog.jpg";
    public static String READ_PATH = PATH + FILE_NAME;
    public static String IP_DO_SERVER = "localhost";
    public static String DIMENSION = "500x500";
    public static String OPERATION = "enviar";
//    public static String OPERATION = "solicitar";

    public static void main(String[] args) throws Exception {
        getUserInputs();
        final File file = new File(READ_PATH);
        TCPSocketClient socketClient = new TCPSocketClient();
        socketClient.startConectWithServer();
        if(OPERATION.equals("enviar")){
            socketClient.sendImageToServer(file);
        }else if(OPERATION.equals("solicitar")) {
            socketClient.requestImageFromServer(FILE_NAME);
        }
        socketClient.endConectWithServer();

    }

    private static void getUserInputs() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Qual o ip do servidor ? [Dica, se estiver rodando na mesma máquina, digite localhost ]: ");
        IP_DO_SERVER = scanner.nextLine();

        System.out.println("qual é a operação? 'enviar' ou 'solicitar' uma imagem? [ex: ' enviar ']");
        OPERATION = scanner.nextLine();

        System.out.println("insira o caminho para o arquivo: [ex: ' /home/victor-reis/Pictures/ ' ]");
        PATH = scanner.nextLine();

        System.out.println("Qual o nome do arquivo: [ex: ' dog.jpg ' ]");
        FILE_NAME = scanner.nextLine();

        if(OPERATION.equals( "enviar")) {
            System.out.println("Qual é a dimensão do arquivo? [ex: ' 500x500 ' ]");
            DIMENSION = scanner.nextLine();
            READ_PATH = PATH + FILE_NAME;
        }
    }

    private static void showImage(BufferedImage image){
        ImageIcon imageIcon = new ImageIcon(image);
        JLabel jlabel = new JLabel(imageIcon);

        JPanel painel = new JPanel();
        painel.add(jlabel);

        JFrame janela = new JFrame("file");

        janela.add(painel);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.pack();
        janela.setVisible(true);
    }

}