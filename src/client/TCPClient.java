package client;

import java.io.*;
import java.net.Socket;

class TCPClient {

    public static final int TAMANHO_BUFFER = 4096;

    public static void sendImageToServer(File file) throws Exception
    {
        File f = new File("/home/victor-reis/Pictures/pés.jpg");
        FileInputStream in = new FileInputStream(f);

        Socket clientSocket = new Socket("localhost", 6789);


        OutputStream out = clientSocket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(out);
//
//        InputStream inputStream = clientSocket.getInputStream();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//

        BufferedWriter writer = new BufferedWriter(osw);
        writer.write(f.getName() + "\n");
        writer.flush();

        byte[] buffer = new byte[TAMANHO_BUFFER];
        int lidos = -1;
        while ((lidos = in.read(buffer, 0, TAMANHO_BUFFER)) != -1) {
            out.write(buffer, 0, lidos);
        }
        System.out.println("[CLIENT] arquivo enviado");

//        String fName = reader.readLine();
//        System.out.println(fName);
//
//        File f1 = new File("/home/victor-reis/Pictures/pés-server.jpg");
//        FileOutputStream outputStream = new FileOutputStream(f1);
//
//        buffer = new byte[TAMANHO_BUFFER];
//        lidos = -1;
//
//
//        while ((lidos = inputStream.read(buffer, 0, TAMANHO_BUFFER)) != -1)
//            outputStream.write(buffer, 0, lidos);
//
//        outputStream.flush();


        clientSocket.close();
    }
}