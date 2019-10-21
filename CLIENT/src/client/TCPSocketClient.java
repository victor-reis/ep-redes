package client;

import java.io.*;
import java.net.Socket;

import static client.MainClient.*;
import static java.lang.String.*;

class TCPSocketClient {

    private static final int PORT = 6789;
    private static final int TAMANHO_BUFFER = 4096;


    Socket socket;

    public void startConectWithServer(){
        try {
            System.out.println("[CLIENT] conectando com o servidor -> " + IP_DO_SERVER + ": " + PORT );
            this.socket = new Socket(IP_DO_SERVER, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void endConectWithServer(){
        try {

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendImageToServer(File file) throws Exception
    {
        System.out.println("[CLIENT] Começando à enviar o arquivo -> " + file.getName());

        FileInputStream in = new FileInputStream(file);
        byte[] buffer = new byte[TAMANHO_BUFFER];
        int lidos = -1;
        String config = format("%s\n%s\n%s",OPERATION,file.getName(),DIMENSION);

        OutputStream outputStream = socket.getOutputStream();

        sendInitialConfigs(outputStream,config);

        while ((lidos = in.read(buffer, 0, TAMANHO_BUFFER)) != -1) {
            outputStream.write(buffer, 0, lidos);
        }

        outputStream.close();

        System.out.println("[CLIENT] arquivo enviado");
    }

    private void sendInitialConfigs(OutputStream outputStream, String config) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(outputStream);
        BufferedWriter writer = new BufferedWriter(osw);
        writeToServer(writer, config);
    }

    public void requestImageFromServer(String fileName) throws IOException {
        byte[] buffer = new byte[TAMANHO_BUFFER];
        int lidos = -1;
        String fileNameFromServer;
        String config = format("%s\n%s\n",OPERATION,fileName);

        OutputStream outputStream = socket.getOutputStream();

        sendInitialConfigs(outputStream,config);

        System.out.println(format("[CLIENT] requisitando do servidor arquivo -> %s",fileName));
        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        fileNameFromServer = reader.readLine();

        File f1 = new File(PATH + fileNameFromServer);
        FileOutputStream fileOutputStream = new FileOutputStream(f1);

        while ((lidos = inputStream.read(buffer, 0, TAMANHO_BUFFER)) != -1)
            fileOutputStream.write(buffer, 0, lidos);

        fileOutputStream.flush();

        System.out.println(format("[CLIENT] imagem salva em -> %s%s" , PATH ,fileNameFromServer));

        fileOutputStream.close();

    }

    private void writeToServer(BufferedWriter writer, String text) throws IOException {
        writer.write(text + "\n");
        writer.flush();
    }
}