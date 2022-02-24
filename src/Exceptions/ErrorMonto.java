package Exceptions;

public class ErrorMonto extends Exception{

	private static final long serialVersionUID = 1L;

	public ErrorMonto () {
		super("Error en el monto ingresado. Ingrese el monto nuevamente por favor...");
	}

}
