package server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

class TCPServer {

    public static final int TAMANHO_BUFFER = 4096;

    public static void main(String argv[]) throws Exception
    {

        byte[] b = InetAddress.getByName("localhost").getAddress();
        System.out.println("[SERVER] started in: " + b[0] + "." + b[1] + "." + b[2] + "." + b[3]);

        try{
            ServerSocket welcomeSocket = new ServerSocket(6789);

            while(true) {
                Socket clSocket = welcomeSocket.accept();
                System.out.println("[SERVER] conection accepted with: " +clSocket.getInetAddress().getHostName());

                InputStream in = clSocket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

//                OutputStream outputStream = clSocket.getOutputStream();
//                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
//

                String fName = reader.readLine();
                System.out.println(fName);
                File f1 = new File("/home/victor-reis/Pictures/pés-server.jpg");
                FileOutputStream out = new FileOutputStream(f1);

                byte[] buffer = new byte[TAMANHO_BUFFER];
                int lidos = -1;

                while ((lidos = in.read(buffer, 0, TAMANHO_BUFFER)) != -1)
                    out.write(buffer, 0, lidos);

                out.flush();
                Main.main(argv);
                System.out.println("[SERVER] Terminou edicao");



//                writer.write(f1.getName() + "\n");
//                writer.flush();
//
//                buffer = new byte[TAMANHO_BUFFER];
//                lidos = -1;
//
//                while ((lidos = in.read(buffer, 0, TAMANHO_BUFFER)) != -1)
//                    outputStream.write(buffer, 0, lidos);

                System.out.println("[SERVER] Terminou de retornar");


            }
        } catch (IOException e) {
        }
    }
}
