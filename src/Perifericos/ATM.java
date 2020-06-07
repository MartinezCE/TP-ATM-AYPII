package Perifericos;

import Cuentas.*;
import Dispensador.Dia;
import Dispensador.DispensadorDeEfectivo;
import Dispensador.NoHaySaldoSuficiente;
import Transacciones.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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

	public void ejecutar() {
		try {
			pantalla.imprimir("CAJERO AUTOMATICO - Los Magios");
			this.iniciarSesion();
			this.seleccionarCuenta();
			//TODO: Revisar todos los exceptions y sus mensajes de respuesta
		}catch (ErrorSaldo | PinInvalido | NoHayCuentasDisponibles | TarjetaNoReconocida | NoHaySaldoSuficiente | ErrorCuenta error) {
			pantalla.imprimir(error.getMessage());
		}catch(IOException errFile) {
			pantalla.imprimir("Error interno, intente nuevamente mas tarde");
		}
	};

	private void iniciarSesion() throws PinInvalido, NoHayCuentasDisponibles, TarjetaNoReconocida {
		pantalla.imprimir("Introduzca su numero de tarjeta de debito: ");
		this.cliente.setNumeroTarjeta(teclado.leerNumeroDeTarjeta());
		pantalla.imprimir("Introduzca su numero de PIN : ");
		this.cliente.setPin(teclado.leerNumeroDePin());
		this.cliente.login();
	}

	private void seleccionarCuenta() throws ErrorSaldo, NoHaySaldoSuficiente, IOException, ErrorCuenta {
		pantalla.imprimir("Seleccione el tipo de cuenta que desea : ");
		for(Map.Entry<Integer, Cuenta> cuenta : cliente.getCuentas().entrySet()) {
			pantalla.imprimir(cuenta.getKey() + ". " + cuenta.getValue().getTipo());
		}
		int tipoCuenta = teclado.selecionDeTipoCuenta();
		this.cliente.setCuentaActiva(this.cliente.getCuenta(tipoCuenta));
		this.seleccionarTransaccion();
	}

	private void seleccionarTransaccion() throws ErrorSaldo, NoHaySaldoSuficiente, IOException, ErrorCuenta {
		pantalla.imprimir("Seleccione el tipo de transaccion que desea :");
		for(Map.Entry<Integer, Transaccion> cuenta : cliente.getCuentaActiva().transaccionesDisponibles().entrySet()) {
			pantalla.imprimir(cuenta.getKey() + ". " + cuenta.getValue().getTipo());
		}
		pantalla.imprimir("6. Mostrar alias de la cuenta");
		pantalla.imprimir("7. Mostrar ultimas transacciones");
		this.tipoOperacion = this.teclado.selecionDeTipoOperacion();
		//TODO: Crear metodo validar tipoPeracion que chequee contra "transaccionesDisponibles" para ver si puede ejecutar
		this.ejecutarTransaccion();
	}

	private void ejecutarTransaccion() throws ErrorSaldo, NoHaySaldoSuficiente, IOException, ErrorCuenta {
		HashMap<Integer, Transaccion> transacciones = cliente.getCuentaActiva().transaccionesDisponibles();
		if(this.tipoOperacion == TipoDeTransacciones.MonstrarAlias.getValor()) {
			this.mostrarAlias();
		}else if(this.tipoOperacion == TipoDeTransacciones.MostrarUltimasTransacciones.getValor()) {
			this.mostrarUltimasTransacciones();
		}else {
			pantalla.imprimir("Ingrese monto");
			Transaccion transaccion = transacciones.get(this.tipoOperacion);
			double monto = teclado.getMonto();
			if(this.tipoOperacion == TipoDeTransacciones.Comprar.getValor()) {
				((ComprarDolares) transaccion).setDestino(getCuentaDestino());
			}
			if(this.tipoOperacion == TipoDeTransacciones.Transferir.getValor()) {
				((TransferenciaBancaria) transaccion).setDestino(getCuentaDestino());
			}
			if(this.tipoOperacion == TipoDeTransacciones.Retirar.getValor()) {
				dispensador.Operar(monto, new Dia());
			}
			transaccion.setMonto(monto);
			transaccion.ejecutar();
			this.cliente.agregarTransaccion(transaccion);
		}
		this.mostrarUltimaTransaccion();
		this.siguienteOperacion();
	}

	private Cuenta getCuentaDestino() {
		pantalla.imprimir("Ingrese cuenta de destino");
		for(Map.Entry<Integer, Cuenta> cuenta : cliente.getCuentas().entrySet()) {
			pantalla.imprimir(cuenta.getKey() + ". " + cuenta.getValue().getTipo());
		}
		return this.cliente.getCuenta(teclado.selecionDeTipoCuenta());
	}

	/*private void crearRevertir() throws ErrorSaldo, IOException {
		Transaccion revertir = this.cliente.getTransaccion();
		pantalla.imprimir("Se revertira la ultima transacción " + revertir.getTipo());

		pantalla.imprimir("¿Está seguro de la transacción?");
		pantalla.imprimir("1 - Si");
		pantalla.imprimir("2 - No");
		if(teclado.getOpcionTicket()) {
			((TransferenciaBancaria) revertir).revertir();
		}
		this.cliente.agregarTransaccion(revertir);
	}*/

	private void mostrarAlias() throws ErrorSaldo, NoHaySaldoSuficiente, ErrorCuenta, IOException {
		pantalla.imprimir("Alias de la cuenta: " + this.cliente.getCuentaActiva().getAlias());
		this.siguienteOperacion();
	}

	private void mostrarUltimaTransaccion() {
		pantalla.imprimir("¿Desea imprimir la transacción?");
		pantalla.imprimir("1 - Si");
		pantalla.imprimir("2 - No");
		if(teclado.getOpcionTicket()) {
			Transaccion transaccion = this.cliente.getTransaccion();
			Ticket ticket = new Ticket(transaccion);
			pantalla.imprimir(ticket.getTicket());
		}
	}

	private void mostrarUltimasTransacciones() {
		Stack<Transaccion> transaccions = this.cliente.getTransacciones();
		for (Transaccion transaccion: transaccions) {
			Ticket ticket = new Ticket(transaccion);
			pantalla.imprimir(ticket.getTicket());
		}
	}

	private void siguienteOperacion() throws ErrorSaldo, NoHaySaldoSuficiente, IOException, ErrorCuenta {
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