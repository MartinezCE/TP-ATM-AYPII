package Exceptions;

public class ErrorCuenta extends Exception {

	private static final long serialVersionUID = 1L;

	ErrorCuenta() {
		super("La cuenta de destino es invalida");
	}
}
