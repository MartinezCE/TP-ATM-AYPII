package Transacciones;
import Cuentas.Cuenta;
import Cuentas.ErrorSaldo;

import java.util.Date;

public abstract class Transaccion {
	private Cuenta origen;
	private double monto;
	private Date fechaCreacion;

	public Transaccion(Cuenta origen) {
		this.origen = origen;
	}

	public abstract String getTipo();

	public abstract void ejecutar() throws ErrorSaldo, ErrorCuenta;

	public Cuenta getOrigen() {
		return origen;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public Date getFechaCreacion () {
		return fechaCreacion;
	}

	protected void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
}
