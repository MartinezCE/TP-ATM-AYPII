package Cuentas;

public class OperacionErroneaParaTipoMoneda extends Exception {

	private static final long serialVersionUID = 1L;

	public OperacionErroneaParaTipoMoneda() {
		super("Operación no valida para el tipo de moneda");
	}

}
