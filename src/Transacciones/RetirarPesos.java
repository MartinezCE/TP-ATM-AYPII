package Transacciones;
import Cuentas.Cuenta;
import Cuentas.EjecutarTransaccion;
import Cuentas.ErrorSaldo;
import Cuentas.Moneda;

import java.util.Date;

public class RetirarPesos extends Transaccion {
	public RetirarPesos(Cuenta origen)  {
		super (origen);
	}

	@Override
	public String getTipo() {
		return "Retiro de efectivo";
	}

	@Override
	public void ejecutar() throws ErrorSaldo {
		if(getOrigen().extraer(getMonto(), Moneda.PESOS)){
			throw new ErrorSaldo("No posee saldo suficiente para esta operacion");
		}
		setFechaCreacion(new Date());
	}
}


