package streams;

import java.io.Serializable;


public class JustAClass implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int a;
	private float b;
	
	public JustAClass(int a, float b) {
		super();
		this.a = a;
		this.b = b;
	}
	
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
	}
	public float getB() {
		return b;
	}
	public void setB(float b) {
		this.b = b;
	}
	
	public String toString() {
		return "" + "a = " + a + ", b = " + b;
	}
	
}
