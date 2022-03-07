package hello_callback;

import java.rmi.*;
import java.rmi.server.*;
import java.util.Scanner;


public class HelloServer extends UnicastRemoteObject implements Hello_S_I {
	static Hello_C_I client;

	public HelloServer() throws RemoteException {
		super();
	}

	public void print_on_server(String s) throws RemoteException {
		System.out.println("> " + s);
	}

	public void subscribe(String name, Hello_C_I c) throws RemoteException {
		System.out.println("Subscribing " + name);
		System.out.print("> ");
		client = c;
	}

	// =======================================================

	public static void main(String args[]) {
		String a;

		/*
		System.getProperties().put("java.security.policy", "policy.all");
		System.setSecurityManager(new RMISecurityManager());
		*/

		try (Scanner sc = new Scanner(System.in)) {
			//User user = new User();
			HelloServer h = new HelloServer();
			Naming.rebind("XPTO", h);
			System.out.println("Hello Server ready.");
			while (true) {
				synchronized (h) {
					System.out.print("> ");
				}
				a = sc.nextLine();
				client.print_on_client(a);
			}
		} catch (Exception re) {
			System.out.println("Exception in HelloImpl.main: " + re);
		} 
	}
}
