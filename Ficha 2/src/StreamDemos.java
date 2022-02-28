import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;



public class StreamDemos {

	private static final int MAX_SIZE = 256;

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		PipedInputStream pin = new PipedInputStream();
		PipedOutputStream pout = new PipedOutputStream(pin);
		
		/*** FIRST: the raw channel ***/
		String s = new String("Hello Hello!");
		byte [] b = s.getBytes();
		pout.write(b);
		pout.flush();
		
		// note that the receiver doesn't  know the exact contents of the phrase
		byte [] receivebuffer = new byte[MAX_SIZE];
		int howmany = pin.read(receivebuffer);
		// notice the data we need to reconstruct the string.
		// the byte array by itself is not enough, because it is larger than the string
		String received = new String(receivebuffer, 0, howmany);
		System.out.println("I received: " + received);
		
		
		/*** SECOND: the Data Stream ***/
		DataOutputStream dos = new DataOutputStream(pout);
		// send something
		dos.writeInt(40);
		dos.flush();
		
		DataInputStream dis = new DataInputStream(pin);
		int val = dis.readInt();
		System.out.println("I received the following integer: " + val + ". It could also be other data type, including a String.");
		
		/*** THIRD: the Object Stream ***/
		ObjectOutputStream oos = new ObjectOutputStream(pout);
		JustAClass myobj = new JustAClass(8, (float) 3.3);
		oos.writeObject(myobj);
		myobj.setA(9);
		oos.reset();
		oos.writeObject(myobj);
		myobj.setA(10);
		oos.reset();
		oos.writeObject(myobj);
		oos.flush();
		
		ObjectInputStream ois = new ObjectInputStream(pin);
		JustAClass receiveobj = (JustAClass) ois.readObject();
		System.out.println("First object: " + receiveobj);
		receiveobj = (JustAClass) ois.readObject();
		System.out.println("Second object: " + receiveobj + ". Surprise??");
		receiveobj = (JustAClass) ois.readObject();
		System.out.println("Third object: " + receiveobj + ". Ahah!!");

		dos.close();
		oos.close();
	}

}
