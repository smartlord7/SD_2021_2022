import java.net.*;
import java.util.Scanner;
import java.io.*;

public class TCPClient {
	private static int serversocket = 8001;
	
	public static void main(String args[]) throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Host name: ");
		String host = input.readLine();


		// 1o passo - criar socket
		try (Socket s = new Socket(host, serversocket)) {
			new MessageListener(s).start();
			System.out.println("SOCKET=" + s);

			// 2o passo
			DataOutputStream out = new DataOutputStream(s.getOutputStream());

		// 3o passo
		while (true) {
			// READ STRING FROM KEYBOARD
			String texto = input.readLine();

			// WRITE INTO THE SOCKET
			out.writeUTF(texto);
		}

		} catch (UnknownHostException e) {
			System.out.println("Sock:" + e.getMessage());
		} catch (EOFException e) {
			System.out.println("EOF:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO:" + e.getMessage());
		}

	}

	static class MessageListener extends Thread {
		private Socket socket;

		public MessageListener(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			DataInputStream in;
			String message;

			try {
				in = new DataInputStream(socket.getInputStream());
				while (true) {
					message = in.readUTF();
					System.out.println("Received: " + message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}