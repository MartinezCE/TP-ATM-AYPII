package Cuentas;

public class NoHayCuentasDisponibles extends Exception {
	private static final long serialVersionUID = 1L;

	public NoHayCuentasDisponibles(String mensaje) {
		super(mensaje);
	}

}
