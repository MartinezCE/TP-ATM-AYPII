package Transacciones;

public class ErrorCuenta extends Exception {
	private static final long serialVersionUID = 1L;
	ErrorCuenta(String s){
		super(s);
	}
}
