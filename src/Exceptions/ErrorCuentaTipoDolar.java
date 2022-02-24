package Exceptions;

public class ErrorCuentaTipoDolar extends Exception{

	private static final long serialVersionUID = 1L;
	
	public ErrorCuentaTipoDolar() {
		super("La cuenta destino debe ser del tipo dolares");
	}

}
