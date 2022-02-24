package Transacciones;
import Comprobante.*;
import Cuentas.Cuenta;
import Cuentas.ErrorSaldo;
import Cuentas.Moneda;
import Dispensador.DispensadorDeEfectivo;
import Exceptions.OperacionErroneaParaTipoMoneda;

import java.util.Date;

public abstract class Transaccion {
	private Cuenta origen;
	private double monto;
	private Date fechaCreacion;
	private Comprobante comprobante;

	public Transaccion(Cuenta origen) {
		this.origen = origen;
	}

	public abstract String getTipo();

	public abstract String ejecutar() throws ErrorSaldo, ErrorCuenta, OperacionErroneaParaTipoMoneda;

	public Moneda operaMoneda() { return Moneda.PESOS; };

	public boolean requiereDestino() {
		return false;
	};

	public boolean requiereDispensador() {
		return false;
	}

	public boolean requiereMonto() {
		return true;
	}

	public void setDestino(Cuenta destino) {
		//TODO: No soporta destino
		throw new Error("La cuenta no soporta destino");
	}

	public void setDispensador(DispensadorDeEfectivo dispensador) {
		throw new Error("La transacción no requiere dispensador");
	}

	public Cuenta getOrigen() {
		return origen;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	public Date getFechaCreacion () {
		return fechaCreacion;
	}

	protected void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
}
