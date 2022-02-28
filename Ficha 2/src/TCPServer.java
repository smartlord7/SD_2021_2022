// TCPServer2.java: Multithreaded server
import java.net.*;
import java.io.*;
import java.util.HashMap;

public class TCPServer{
    private static int serverPort = 8001;

    public static void main(String args[]){
        int numero = 0;
        SharedMessage sharedMessage = new SharedMessage();
        HashMap<Integer, Socket> clients = new HashMap<Integer, Socket>();

        new SharedMessageListener(sharedMessage, clients).start();

        try (ServerSocket listenSocket = new ServerSocket(serverPort)) {
            System.out.println("A escuta no porto 6000");
            System.out.println("LISTEN SOCKET=" + listenSocket);

            while (true) {
                Socket clientSocket = listenSocket.accept(); // BLOQUEANTE
                System.out.println("CLIENT_SOCKET (created at accept())="+clientSocket);
                numero++;
                clients.put(numero, clientSocket);
                new Connection(clientSocket, numero, sharedMessage);
            }
        } catch(IOException e) {
            System.out.println("Listen:" + e.getMessage());
        }
    }
}

class SharedMessageListener extends Thread {
    private SharedMessage sharedMessage;
    private HashMap<Integer, Socket> clients;

    public SharedMessageListener(SharedMessage sharedMessage, HashMap<Integer, Socket> clients) {
        this.sharedMessage = sharedMessage;
        this.clients = clients;
    }

    public void run() {
        while (true) {
            synchronized (sharedMessage) {
                while (sharedMessage.getMessage() == null) {
                    try {
                        sharedMessage.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            broadcastMessage();
        }
    }

    private void broadcastMessage() {
        DataOutputStream outputStream;

        for (Socket client : clients.values()) {
            try {
                outputStream =  new DataOutputStream(client.getOutputStream());
                outputStream.writeUTF(sharedMessage.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        sharedMessage.setMessage(null);
    }
}

//= Thread para tratar de cada canal de comunicação com um cliente
class Connection extends Thread {
    private DataInputStream in;
    private DataOutputStream out;
    private Socket clientSocket;
    private SharedMessage sharedMessage;
    private int thread_number;
    
    public Connection (Socket aClientSocket, int numero, SharedMessage sharedMessage) {
        this.thread_number = numero;
        this.sharedMessage = sharedMessage;
        try{
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            this.start();
        }catch(IOException e){System.out.println("Connection:" + e.getMessage());}
    }
    //=============================
    public void run(){
        String resposta;
        try {
            while(true){
                //an echo server
                String data = in.readUTF();
                System.out.println("T[" + thread_number + "] Recebeu: "+data);
                resposta=data.toUpperCase();
                sharedMessage.setMessage(data);
            }
        } catch(EOFException e) {
            System.out.println("EOF:" + e);
        } catch(IOException e) {
            System.out.println("IO:" + e);
        }
    }
}