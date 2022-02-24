package Transacciones;
import java.util.Date;
import Comprobante.*;
import Cuentas.Cuenta;
import Cuentas.ErrorSaldo;
import Cuentas.Moneda;
import Exceptions.OperacionErroneaParaTipoMoneda;

public class DepositoPesos extends Transaccion {

	public DepositoPesos(Cuenta origen) {
		super(origen);
	}

	@Override
	public String getTipo() {
		return "Deposito pesos";
	}

	@Override
	public String ejecutar() throws ErrorSaldo, OperacionErroneaParaTipoMoneda {
		if(!getOrigen().depositar(getMonto(), Moneda.PESOS)) {
			throw new ErrorSaldo ("Error en el monto ingresado");
		}
		setFechaCreacion(new Date());
		getOrigen().setHistorialTransacciones(this);
		ComprobanteDeposito ticket = new ComprobanteDeposito();
		setComprobante(ticket);
        return ticket.imprimirComprobante(this);
    }
}
