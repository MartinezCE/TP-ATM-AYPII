package Cuentas;

public class OperacionErroneaParaTipoMoneda extends Exception {

	private static final long serialVersionUID = 1L;

	public OperacionErroneaParaTipoMoneda() {
		super("Operaci�n no valida para el tipo de moneda");
	}

}
