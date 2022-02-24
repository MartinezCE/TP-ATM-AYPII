package Cuentas;

public class ErrorEnBaseDeDatos extends Exception {

	private static final long serialVersionUID = 1L;

	public ErrorEnBaseDeDatos() {
		super("Ups... hubo un problema en nuestra base de datos. Vuelva a intentarlo más tarde por favor.");
	}

}
