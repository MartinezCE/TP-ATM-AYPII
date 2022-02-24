package Transacciones;
import Comprobante.*;
import Cuentas.Cuenta;
import Cuentas.ErrorSaldo;
import Cuentas.Moneda;
import Exceptions.OperacionErroneaParaTipoMoneda;
import java.util.Date;

public class ComprarDolares extends Transaccion {
	private Cuenta destino;
	public ComprarDolares(Cuenta origen) {
		super(origen);
	}

	public Moneda operaMoneda() { return Moneda.DOLARES; };

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
		return "Comprar dolares";
	}

	@Override 
	public String ejecutar() throws ErrorSaldo, ErrorCuenta, OperacionErroneaParaTipoMoneda {
		double dolarCompra = 70;
		if(!getOrigen().extraer(getMonto() * dolarCompra, Moneda.PESOS)) {
			throw new ErrorSaldo ("No posee los fondos suficientes para comprar dolares");
		}
		if(!destino.depositar(getMonto(), Moneda.DOLARES)) {
			throw new ErrorCuenta("La cuenta destino debe ser en dolares");
		}
		setFechaCreacion(new Date());
		getOrigen().setHistorialTransacciones(this);
		ComprobanteCompraDolares ticket = new ComprobanteCompraDolares();
		setComprobante(ticket);
		return ticket.imprimirComprobante(this);
	}
}
