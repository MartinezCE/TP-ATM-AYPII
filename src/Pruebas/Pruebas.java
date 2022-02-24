package Pruebas;

import Cuentas.*;
import Cuentas.ErrorSaldo;
import Dispensador.NoHaySaldoSuficiente;
import Exceptions.CuentaNoDisponible;
import Exceptions.ErrorEnBaseDeDatos;
import Exceptions.NoPoseeTipoDeCuenta;
import Exceptions.OperacionErroneaParaTipoMoneda;
import Transacciones.RetirarPesos;
import org.junit.*;

import Dispensador.Dia;
import Dispensador.DispensadorDeEfectivo;
import Transacciones.ErrorCuenta;
import Transacciones.TransferenciaBancaria;

public class Pruebas {
	@Test()
	public void cajaAhorroPesosExtraeYDepositaCorrectamente() throws OperacionErroneaParaTipoMoneda {

		CajaAhorroPesos caja = new CajaAhorroPesos("Cristian", 1000);

		caja.extraer(1000, Moneda.PESOS);
		caja.depositar(1, Moneda.PESOS);

	}

	@Test (expected = OperacionErroneaParaTipoMoneda.class)
	public void fallaMonedacajaAhorroPesosExtraeYDepositaCorrectamente() throws OperacionErroneaParaTipoMoneda {
		try {
			CajaAhorroPesos caja = new CajaAhorroPesos("Cristian", 1000);

			caja.extraer(1000, Moneda.DOLARES);
			caja.depositar(1, Moneda.PESOS);
		}catch (OperacionErroneaParaTipoMoneda err) {
			throw err;
		}
	}

	@Test
	public void cajaAhorroPesosRetiraPesosCorrectamente() {
		DispensadorDeEfectivo dispensador = new DispensadorDeEfectivo(500, 500, 500);
		CajaAhorroPesos caja = new CajaAhorroPesos("Mauro", 2000000);
		RetirarPesos transferencia = new RetirarPesos(caja);
		int monto = 10000;
		try {
			transferencia.setDispensador(dispensador);
			transferencia.setMonto(monto);
			transferencia.ejecutar();

		} catch (ErrorSaldo e) {
			System.out.println(e.getMessage());
		}
	}

	@Test(expected = ErrorSaldo.class)
	public void noSaldoCajaAhorroPesos() throws ErrorSaldo {
		DispensadorDeEfectivo dispensador = new DispensadorDeEfectivo(500, 500, 500);
		CajaAhorroPesos caja = new CajaAhorroPesos("Mauro", 0);
		RetirarPesos transferencia = new RetirarPesos(caja);
		try {
			transferencia.setDispensador(dispensador);
			transferencia.setMonto(1000);
			transferencia.ejecutar();
		} catch (ErrorSaldo err) {
			throw err;
		}
	}

	@Test(expected = OperacionErroneaParaTipoMoneda.class)
	public void CuentaCorrienteRompeEnMonedaYEsCapturadoPorException() throws OperacionErroneaParaTipoMoneda {
		try {
			System.out.println("Prueba de Cuenta corriente con exception: ");
			CuentaCorriente caja = new CuentaCorriente("Tete", 1000, -50);
			caja.extraer(1000, Moneda.DOLARES);
			caja.depositar(1, Moneda.PESOS);
		}catch (OperacionErroneaParaTipoMoneda err) {
			throw err;
		}
	}

	@Test
	public void transferenciaCajaAhorroPesosACuentaCorriente() throws ErrorCuenta, ErrorSaldo, OperacionErroneaParaTipoMoneda {
		try {
			CajaAhorroPesos origen = new CajaAhorroPesos("Cristian", 20000);
			CuentaCorriente destino = new CuentaCorriente("Adrian", 100, 0);
			TransferenciaBancaria transferir = new TransferenciaBancaria(origen);
			int monto = 18000;
			destino.depositar(1000, Moneda.PESOS);
			transferir.setDestino(destino);
			transferir.setMonto(monto);
			transferir.ejecutar();
		}catch (Exception err) {
			throw err;
		}
	}

	@Test(expected = OperacionErroneaParaTipoMoneda.class)
	public void transferenciaCajaAhorroPesosAtransferenciaCajaAhorroDolares() throws ErrorCuenta, ErrorSaldo, OperacionErroneaParaTipoMoneda {
		try {
			CajaAhorroPesos origen = new CajaAhorroPesos("Cristian", 20000);
			CajaAhorroDolares destino = new CajaAhorroDolares("Adrian", 100);
			TransferenciaBancaria transferir = new TransferenciaBancaria(origen);
			int monto = 18000;
			destino.depositar(1000, Moneda.DOLARES);
			transferir.setDestino(destino);
			transferir.setMonto(monto);
			transferir.ejecutar();
		}catch (OperacionErroneaParaTipoMoneda err) {
			throw err;
		}
	}

	@Test(expected = NoPoseeTipoDeCuenta.class)
	public void getCuentaRompe() throws NoPoseeTipoDeCuenta {
		System.out.println("Prueba de obtencion de cuenta: ");

		Cliente clienteUno = new Cliente();
		try {
			CajaAhorroPesos caja = new CajaAhorroPesos("Tete", 1000);
			clienteUno.setCuentaActiva(caja);
			Assert.assertEquals(clienteUno.getCuenta(02), clienteUno.getCuenta(01));
		} catch (NoPoseeTipoDeCuenta err) {
			throw err;
		}
	}

	@Test(expected = ErrorEnBaseDeDatos.class)
	public void capturarIngresoErroneoConException() throws ErrorEnBaseDeDatos, PinInvalido, NoHayCuentasDisponibles, CuentaNoDisponible, TarjetaNoReconocida {
		Cliente clienteUno = new Cliente();
		try {
			clienteUno.setNumeroTarjeta(12345678);
			clienteUno.login();
		} catch (ErrorEnBaseDeDatos err) {
			throw err;
		}
	}

	@Test(expected = NoHaySaldoSuficiente.class)
	public void dispensadorSinSaldoSuficiente() throws NoHaySaldoSuficiente {
		System.out.println("Prueba de dispensador sin saldo suficiente: ");
		DispensadorDeEfectivo miDispensador = new DispensadorDeEfectivo(0, 0, 0);
		Dia hoy = new Dia();
		try {
			miDispensador.Operar(900000, hoy);
		} catch (NoHaySaldoSuficiente err) {
			throw err;
		}
	}

	@Test()
	public void dispensadorConSaldoSuficiente() throws NoHaySaldoSuficiente {
		DispensadorDeEfectivo miDispensador = new DispensadorDeEfectivo(500, 500, 500);
		Dia hoy = new Dia();
		try {
			miDispensador.Operar(200000, hoy);
		} catch (Exception err) {
			throw err;
		}
	}
}