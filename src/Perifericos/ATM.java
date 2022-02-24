package Perifericos;

import Cuentas.*;
import Dispensador.DispensadorDeEfectivo;
import Dispensador.NoHaySaldoSuficiente;
import Exceptions.NoPoseeTipoDeCuenta;
import Exceptions.OperacionErroneaParaTipoMoneda;
import Transacciones.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ATM {
	private Pantalla pantalla;
	private Teclado teclado;
	private Cliente cliente;
	private int tipoOperacion;
	private DispensadorDeEfectivo dispensador;

	public ATM() {
		this.pantalla = new Pantalla();
		this.teclado = new Teclado();
		this.cliente = new Cliente();
		this.dispensador = new DispensadorDeEfectivo(500, 500, 500);
	}

	public void ejecutar() throws ErrorSaldo, NoHaySaldoSuficiente, IOException, ErrorCuenta, NoPoseeTipoDeCuenta, OperacionErroneaParaTipoMoneda {
		try {
			pantalla.imprimir("CAJERO AUTOMATICO - Los Magios");
			this.iniciarSesion();
			this.seleccionarCuenta();
		}catch (ErrorSaldo | PinInvalido | NoHayCuentasDisponibles | TarjetaNoReconocida | NoHaySaldoSuficiente | ErrorCuenta | Exceptions.NoPoseeTipoDeCuenta | Exceptions.ErrorEnBaseDeDatos | Exceptions.CuentaNoDisponible | Exceptions.OperacionErroneaParaTipoMoneda error) {
			pantalla.imprimir(error.getMessage());
			siguienteOperacion();
		}catch(IOException errFile) {
			pantalla.imprimir("Error interno, intente nuevamente mas tarde");
		}
	};

	private void iniciarSesion() throws PinInvalido, NoHayCuentasDisponibles, TarjetaNoReconocida, Exceptions.CuentaNoDisponible, Exceptions.ErrorEnBaseDeDatos {
		pantalla.imprimir("Introduzca su numero de tarjeta de debito: ");
		this.cliente.setNumeroTarjeta(teclado.leerNumeroDeTarjeta());
		pantalla.imprimir("Introduzca su numero de PIN : ");
		this.cliente.setPin(teclado.leerNumeroDePin());
		this.cliente.login();
	}

	private void seleccionarCuenta() throws ErrorSaldo, NoHaySaldoSuficiente, IOException, ErrorCuenta, Exceptions.NoPoseeTipoDeCuenta, Exceptions.OperacionErroneaParaTipoMoneda {
		pantalla.imprimir("Seleccione el tipo de cuenta que desea : ");
		for(Map.Entry<Integer, Cuenta> cuenta : cliente.getCuentas().entrySet()) {
			pantalla.imprimir(cuenta.getKey() + ". " + cuenta.getValue().getTipo());
		}
		int tipoCuenta = teclado.selecionDeTipoCuenta();
		this.cliente.setCuentaActiva(this.cliente.getCuenta(tipoCuenta));
		this.seleccionarTransaccion();
	}

	private void seleccionarTransaccion() throws ErrorSaldo, NoHaySaldoSuficiente, IOException, ErrorCuenta, Exceptions.NoPoseeTipoDeCuenta, Exceptions.OperacionErroneaParaTipoMoneda {
		pantalla.imprimir("Seleccione el tipo de transaccion que desea :");
		for(Map.Entry<Integer, Transaccion> cuenta : cliente.getCuentaActiva().transaccionesDisponibles().entrySet()) {
			pantalla.imprimir(cuenta.getKey() + ". " + cuenta.getValue().getTipo());
		}
		this.tipoOperacion = this.teclado.selecionDeTipoOperacion();
		this.ejecutarTransaccion();
	}

	private void ejecutarTransaccion() throws ErrorSaldo, NoHaySaldoSuficiente, IOException, ErrorCuenta, Exceptions.NoPoseeTipoDeCuenta, Exceptions.OperacionErroneaParaTipoMoneda {
		HashMap<Integer, Transaccion> transacciones = cliente.getCuentaActiva().transaccionesDisponibles();
		Transaccion transaccion = transacciones.get(this.tipoOperacion);
		if(transaccion.requiereDestino()) {
			if(!cliente.cuentaOperanMoneda(transaccion.operaMoneda())){
				throw new NoPoseeTipoDeCuenta("No posee el tipo de cuenta caja de ahorros en dolares");
			};
			transaccion.setDestino(getCuentaDestino());
		}
		if(transaccion.requiereDispensador()) {
			transaccion.setDispensador(dispensador);
		}
		if(transaccion.requiereMonto()) {
			pantalla.imprimir("Ingrese monto");
			double monto = teclado.getMonto();
			transaccion.setMonto(monto);
		}
		String ticket = transaccion.ejecutar();
		pantalla.imprimir(ticket);
		this.siguienteOperacion();
	}

	private Cuenta getCuentaDestino() throws Exceptions.NoPoseeTipoDeCuenta {
		pantalla.imprimir("Ingrese cuenta de destino");
		for(Map.Entry<Integer, Cuenta> cuenta : cliente.getCuentas().entrySet()) {
			pantalla.imprimir(cuenta.getKey() + ". " + cuenta.getValue().getTipo());
		}
		return this.cliente.getCuenta(teclado.selecionDeTipoCuenta());
	}

	private void siguienteOperacion() throws ErrorSaldo, NoHaySaldoSuficiente, IOException, ErrorCuenta, Exceptions.NoPoseeTipoDeCuenta, Exceptions.OperacionErroneaParaTipoMoneda {
		pantalla.imprimir("¿Desea realizar otra operación?");
		pantalla.imprimir("1 - Si");
		pantalla.imprimir("2 - No");
		if(teclado.getOpcionTicket()) {
			this.seleccionarTransaccion();
		}else {
			pantalla.imprimir("AYP2: Mariano aprobame please!");
			System.exit(0);
		}
	}
}