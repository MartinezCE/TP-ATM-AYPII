package Cuentas;

public class CuentaNoDisponible extends Exception{

	private static final long serialVersionUID = 1L;
	
	public CuentaNoDisponible () {
		super("Cuenta no disponible");
	}

}
