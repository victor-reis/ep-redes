package server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static server.Main.*;

class TCPServer {

    public static final int TAMANHO_BUFFER = 4096;

    public static void start() throws Exception
    {

        getUserInputs();

        byte[] b = InetAddress.getByName("localhost").getAddress();
        System.out.println("[SERVER] started in: " + b[0] + "." + b[1] + "." + b[2] + "." + b[3]);

        try{
            ServerSocket welcomeSocket = new ServerSocket(6789);

            while(true) {
                Socket clSocket = welcomeSocket.accept();
                System.out.println("[SERVER] conection accepted with: " +clSocket.getInetAddress().getHostName());

                InputStream in = clSocket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String fName = reader.readLine();
                System.out.println("Nome do arquivo: " + fName);

                readSizeOfCrop(reader);

                File f1 = new File(FILE_PATH);
                FileOutputStream out = new FileOutputStream(f1);

                byte[] buffer = new byte[TAMANHO_BUFFER];
                int lidos = -1;

                while ((lidos = in.read(buffer, 0, TAMANHO_BUFFER)) != -1)
                    out.write(buffer, 0, lidos);

                out.flush();

                Thread.sleep(1000);

                Main.editImage();
                System.out.println("[SERVER] Terminou edicao");

            }
        } catch (IOException e) {
        }
    }

    private static void readSizeOfCrop(BufferedReader reader) throws IOException {
        String dimensions = reader.readLine();

        String[] dimension = dimensions.split("x");
        WIDTH = Integer.parseInt(dimension[0]);
        HEIGHT = Integer.parseInt(dimension[1]);

        System.out.println("dimensoes do arquivo:\n  -" + dimension[0] + " de largura\n  -" + dimension[1] + " de altura");
    }

    private static void getUserInputs() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("insira o Path para salvar o arquivo: [ex: ' /home/victor-reis/Music/ ' ]");
        PATH = scanner.nextLine();

        FILE_PATH = PATH + FILE_NAME;
    }
}
