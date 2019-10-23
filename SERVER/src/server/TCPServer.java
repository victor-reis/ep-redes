package server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.String.format;

class TCPServer {

    private static final int TAMANHO_BUFFER = 4096;

    ServerSocket welcomeSocket;
    String path;
    ImageEditor imageEditor = new ImageEditor();


    public void upServer(){
        try {
            this.welcomeSocket = new ServerSocket(6789);
            byte[] b = InetAddress.getByName("localhost").getAddress();
            System.out.println("[SERVER] started in -> " + b[0] + "." + b[1] + "." + b[2] + "." + b[3]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void startAplication() throws Exception
    {
        try{
            while(true) {
                String operation;

                Socket clSocket = welcomeSocket.accept();
                System.out.println("[SERVER] Conexão aceita com -> " +clSocket.getInetAddress().getHostName());

                InputStream inputStream = clSocket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                operation = reader.readLine();
                System.out.println("[SERVER] Operação recebida -> " + operation);

                switch(operation) {
                    case("enviar"):
                    receiveAnImage(clSocket, reader);
                    break;
                    case("solicitar"):
                    sendAnImage(clSocket, reader);
                    break;
                    default:
                        System.out.println(format("[SERVER] A operação %s não é válida", operation));
                }
            }
        } catch (IOException e) {
        }
    }

    private void receiveAnImage(Socket clSocket, BufferedReader reader) throws Exception {
        byte[] buffer = new byte[TAMANHO_BUFFER];
        int lidos = -1;
        InputStream inputStream = clSocket.getInputStream();

        imageEditor.FILE_NAME = reader.readLine();
        System.out.println("[SERVER] Nome do arquivo -> " + imageEditor.FILE_NAME);

        readSizeOfCrop(imageEditor,reader);
        imageEditor.setFilePath(imageEditor.PATH,imageEditor.FILE_NAME);

        File f1 = new File(imageEditor.FILE_PATH);
        FileOutputStream fileOutputStream = new FileOutputStream(f1);

        while ((lidos = inputStream.read(buffer, 0, TAMANHO_BUFFER)) != -1)
            fileOutputStream.write(buffer, 0, lidos);

        fileOutputStream.flush();
        fileOutputStream.close();

        try{imageEditor.editImage();
            System.out.println("[SERVER] Terminou edicao");
        }
        catch (Exception e){
            System.out.println("[SERVER] houve falha na edição da imagem, por favor reenvie a imagem");
            e.printStackTrace();
        }
    }

    private void sendAnImage(Socket clSocket, BufferedReader reader) throws Exception {
        byte[] buffer = new byte[TAMANHO_BUFFER];
        int lidos = -1;

        String fileName = reader.readLine();

        System.out.println(format("[SERVER] está sendo requisitado -> %s",fileName));

        OutputStream outputStream = clSocket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(outputStream);
        BufferedWriter writer = new BufferedWriter(osw);

        String editedFileName = format("bw-%s",fileName);
        writeToServer(writer,editedFileName);

        System.out.println(format("[SERVER] responderá o nome do arquivo como -> %s", editedFileName));

        File f1 = new File (ImageEditor.PATH + editedFileName);

        FileInputStream in = new FileInputStream(f1);


        while ((lidos = in.read(buffer, 0, TAMANHO_BUFFER)) != -1)
            outputStream.write(buffer, 0, lidos);

        outputStream.flush();

        System.out.println("[SERVER] arquivo enviado");
    }

    private void readSizeOfCrop(ImageEditor imageEditor, BufferedReader reader) throws IOException {
        String dimensions = reader.readLine();

        String[] dimension = dimensions.split("x");
        imageEditor.WIDTH = Integer.parseInt(dimension[0]);
        imageEditor.HEIGHT = Integer.parseInt(dimension[1]);

        System.out.println("[SERVER] dimensoes do arquivo ->\n  - " + dimension[0] + "px de largura\n  - " + dimension[1] + "px de altura\n");
    }

    void getUserInputs() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("[SERVER] insira o Path para salvar o arquivo: [ex: ' /home/victor-reis/Music/ ' ]");
        imageEditor.PATH = scanner.nextLine();
    }

    private void writeToServer(BufferedWriter writer, String text) throws IOException {
        writer.write(text + "\n");
        writer.flush();
    }
}
