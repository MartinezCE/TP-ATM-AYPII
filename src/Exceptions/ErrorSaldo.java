package Exceptions;

public class ErrorSaldo extends Exception {

	private static final long serialVersionUID = 1L;

	public ErrorSaldo() {
		super("No posee los fondos suficientes para realizar esta operacion");
	}
}
