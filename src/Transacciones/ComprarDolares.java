package Transacciones;
import Cuentas.Cuenta;
import Cuentas.ErrorSaldo;
import Cuentas.Moneda;

import java.util.Date;

public class ComprarDolares extends Transaccion {
	private Cuenta destino;
	public ComprarDolares(Cuenta origen) {
		super(origen);
	}

	@Override
	public String getTipo() {
		return "Comprar dolares";
	}

	@Override 
	public void ejecutar() throws ErrorSaldo, ErrorCuenta {
		double dolarCompra = 70;
		if(!getOrigen().extraer(getMonto(), Moneda.PESOS)) {
			throw new ErrorSaldo ("No posee los fondos suficientes para comprar dolares");
		}
		if(destino.depositar(getMonto()/dolarCompra, Moneda.DOLARES)) {
			throw new ErrorCuenta("La cuenta destino debe ser en dolares");
		}
		setFechaCreacion(new Date());
	}

	public void setDestino(Cuenta destino) {
		this.destino = destino;
	}
}
