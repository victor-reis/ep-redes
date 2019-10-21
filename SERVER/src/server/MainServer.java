package server;


public class MainServer {

    public static void main(String[] args) throws Exception {
        TCPServer tcpServer = new TCPServer();
        tcpServer.getUserInputs();
        tcpServer.upServer();
        tcpServer.startAplication();
    }
}