package Transacciones;

import Cuentas.Cuenta;
import Cuentas.ErrorSaldo;
import Cuentas.Moneda;

import java.util.Date;

public class TransferenciaBancaria extends Transaccion implements RevertirTransaccion {
	private Cuenta destino;
	public TransferenciaBancaria (Cuenta origen) {
		super(origen);
	}

	public void setDestino(Cuenta destino) {
		this.destino = destino;
	}

	@Override
	public String getTipo() {
		return "Transferencia Bancaria";
	}

	@Override
	public void ejecutar() throws ErrorSaldo, ErrorCuenta {
		if(!getOrigen().extraer(getMonto(), Moneda.PESOS)) {
			throw new ErrorSaldo("Saldo de la cuenta no es suficiente para realizar la operacion");
		}
		if(!destino.depositar(getMonto(), Moneda.PESOS)) {
			throw new ErrorCuenta("La cuenta de destino es invalida");
		}
		setFechaCreacion(new Date());
	}

	@Override
	public void revertir() throws ErrorSaldo, ErrorCuenta {
		if(!destino.extraer(getMonto(), Moneda.PESOS)) {
			throw new ErrorSaldo("Saldo de la cuenta no es suficiente para realizar la operacion");
		}
		if(!getOrigen().depositar(getMonto(), Moneda.PESOS)) {
			throw new ErrorCuenta("La cuenta de destino es invalida");
		}
	}
}
