package Transacciones;
import Comprobante.ComprobanteDeposito;
import Comprobante.ComprobanteRetiroEfectivo;
import Cuentas.Cuenta;
import Cuentas.ErrorSaldo;
import Cuentas.Moneda;
import Dispensador.Dia;
import Dispensador.DispensadorDeEfectivo;
import Dispensador.NoHaySaldoSuficiente;
import Exceptions.OperacionErroneaParaTipoMoneda;

import java.util.Date;

public class RetirarPesos extends Transaccion {
	private DispensadorDeEfectivo dispensador;

	public RetirarPesos(Cuenta origen)  {
		super (origen);
	}

	public boolean requiereDispensador() {
		return true;
	}

	public void setDispensador(DispensadorDeEfectivo dispensador) {
		this.dispensador = dispensador;
	}

	@Override
	public String getTipo() {
		return "Retiro de efectivo";
	}

	@Override
	public String ejecutar() throws ErrorSaldo {
		try {
			dispensador.Operar(getMonto(), new Dia());
			if (!getOrigen().extraer(getMonto(), Moneda.PESOS)) {
				throw new ErrorSaldo("No posee saldo suficiente para esta operacion");
			}
			setFechaCreacion(new Date());
			getOrigen().setHistorialTransacciones(this);
			ComprobanteRetiroEfectivo ticket = new ComprobanteRetiroEfectivo();
			setComprobante(ticket);
			return ticket.imprimirComprobante(this);
		}catch (NoHaySaldoSuficiente | OperacionErroneaParaTipoMoneda noHaySaldoSuficiente) {
			noHaySaldoSuficiente.getMessage();
		}
        return null;
    }
}


