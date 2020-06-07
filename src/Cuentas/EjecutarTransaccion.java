package Cuentas;

import Transacciones.ErrorCuenta;

public interface EjecutarTransaccion {
	public void ejecutar() throws ErrorSaldo, ErrorCuenta;
}
