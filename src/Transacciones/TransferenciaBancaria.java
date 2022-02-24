package Transacciones;

import Comprobante.ComprobanteRevertirTransferencia;
import Comprobante.ComprobanteTransferencia;
import Cuentas.Cuenta;
import Cuentas.ErrorSaldo;
import Cuentas.Moneda;
import Exceptions.OperacionErroneaParaTipoMoneda;

import java.util.Date;

public class TransferenciaBancaria extends Transaccion implements RevertirTransaccion {
	private Cuenta destino;
	public TransferenciaBancaria (Cuenta origen) {
		super(origen);
	}

	public Cuenta getDestino() {
		return destino;
	}

	public void setDestino(Cuenta destino) {
		this.destino = destino;
	}

	public boolean requiereDestino() {
		return true;
	}

	@Override
	public String getTipo() {
		return "Transferencia Bancaria";
	}

	@Override
	public String ejecutar() throws ErrorSaldo, ErrorCuenta, OperacionErroneaParaTipoMoneda {
		if(!getOrigen().extraer(getMonto(), Moneda.PESOS)) {
			throw new ErrorSaldo("Saldo de la cuenta no es suficiente para realizar la operacion");
		}
		if(!destino.depositar(getMonto(), Moneda.PESOS)) {
			throw new ErrorCuenta("La cuenta de destino es invalida");
		}
		setFechaCreacion(new Date());
		getOrigen().setHistorialTransacciones(this);
		ComprobanteTransferencia ticket = new ComprobanteTransferencia();
		setComprobante(ticket);
		return ticket.imprimirComprobante(this);
    }

	@Override
	public String revertir() throws ErrorSaldo, ErrorCuenta, OperacionErroneaParaTipoMoneda {
		if(!destino.extraer(getMonto(), Moneda.PESOS)) {
			throw new ErrorSaldo("Saldo de la cuenta no es suficiente para realizar la operacion");
		}
		if(!getOrigen().depositar(getMonto(), Moneda.PESOS)) {
			throw new ErrorCuenta("La cuenta de destino es invalida");
		}
		getOrigen().setHistorialTransacciones(this);
		ComprobanteRevertirTransferencia ticket = new ComprobanteRevertirTransferencia();
		setComprobante(ticket);
		return ticket.imprimirComprobante(this);
	}
}
