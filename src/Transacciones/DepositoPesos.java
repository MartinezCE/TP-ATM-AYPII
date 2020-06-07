package Transacciones;
import java.util.Date;
import Cuentas.Cuenta;
import Cuentas.EjecutarTransaccion;
import Cuentas.ErrorSaldo;
import Cuentas.Moneda;

public class DepositoPesos extends Transaccion implements EjecutarTransaccion {

	public DepositoPesos(Cuenta origen) {
		super(origen);
	}

	@Override
	public String getTipo() {
		return "Deposito pesos";
	}

	@Override
	public void ejecutar() throws ErrorSaldo {
		if(!getOrigen().depositar(getMonto(), Moneda.PESOS)) {
			throw new ErrorSaldo ("Error en el monto ingresado");
		}
		setFechaCreacion(new Date());
	}
}
