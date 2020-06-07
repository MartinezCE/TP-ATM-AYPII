package CuentasTest;

import Transacciones.*;
import org.junit.Test;
import Cuentas.Cliente;
import Cuentas.Cuenta;
import Cuentas.ErrorSaldo;
import Dispensador.Dia;
import Dispensador.DispensadorDeEfectivo;
import Dispensador.NoHaySaldoSuficiente;
import junit.framework.Assert;

public class CuentasTest {

	// PRUEBA Package Cuentas
	@Test(expected = Error.class)
	public void ingresoDeNumeroDeTarjetaDeDebitoEsErroneo() {
		Cliente clienteUno = new Cliente();
		Cuenta cuentaDeMauro = new Cuenta(01, "Mauro", 10, "CajaAhorroPesos");
		clienteUno.setCuentaActiva(cuentaDeMauro);
		Assert.assertEquals(clienteUno.getCuenta(02), clienteUno.getCuenta(01));
	}

	@Test()
	public void getInfoValidacionesRompe() {
		Cliente clienteUno = new Cliente();
		try {
			clienteUno.setNumeroTarjeta(12345678);
			clienteUno.login();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// PRUEBAS PACKAGE DISPENSADOR

	@Test(expected = Error.class)
	public void dispensadorSinSaldoSuficiente() {
		DispensadorDeEfectivo miDispensador = new DispensadorDeEfectivo(500, 500, 500);
		Dia hoy = new Dia();
		try {
			miDispensador.Operar(900000, hoy);
		} catch (NoHaySaldoSuficiente e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void dispensadorConSaldoSuficiente() {
		DispensadorDeEfectivo miDispensador = new DispensadorDeEfectivo(500, 500, 500);
		Dia hoy = new Dia();
		try {
			miDispensador.Operar(200000, hoy);
		} catch (NoHaySaldoSuficiente e) {
			System.out.println(e.getMessage());
		}
	}

	// PRUEBAS PACKAGE TRANSACCIONES
	@Test(expected = Error.class)
	public void depositoEnPesosConErrorDeSaldoIngresado() {
		Cuenta mauro = new Cuenta(01, "mauro", 10000, "CajaAhorroPesos");
		DepositoPesos nuevoDeposito = new DepositoPesos(mauro, -1);
		try {
			nuevoDeposito.ejecutar();
		} catch (ErrorSaldo e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void retirarPesosFuncionaCorrectamente() {
		Cuenta mauro = new Cuenta(01, "mauro", 10000, "CajaAhorroPesos");
		RetirarPesos nuevoRetiro = new RetirarPesos(mauro, 9000);
		try {
			nuevoRetiro.ejecutar();
		} catch (ErrorSaldo | ErrorCuenta e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void comprarDolaresFuncionaCorrectamente() {
		Cuenta mauro = new Cuenta(03, "mauro", 40000, "CajaAhorroDolares");
		Cuenta hernan = new Cuenta(03, "hernan", 10000, "CajaAhorroDolares");
		ComprarDolares billeteDolar = new ComprarDolares(mauro, hernan, 500);
		try {
			billeteDolar.ejecutar();
		} catch (ErrorSaldo | ErrorCuenta e) {
			System.out.println(e.getMessage());
		}
	}

	@Test(expected = Error.class)
	public void tranferenciaBancariaIncorrecta() {
		Cuenta mauro = new Cuenta(03, "mauro", 40000, "CajaAhorroDolares");
		Cuenta hernan = new Cuenta(03, "hernan", 10000, "CajaAhorroDolares");
		TransferenciaBancaria transferencia = new TransferenciaBancaria(mauro, hernan, 500);
		try {
			transferencia.ejecutar();
		} catch (ErrorSaldo | ErrorCuenta e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void revertirTransaccionesFuncionaCorrectamente() {
		Cuenta mauro = new Cuenta(02, "mauro", 40000, "CajaAhorroDolares");
		Cuenta hernan = new Cuenta(02, "hernan", 10000, "CajaAhorroDolares");
		TransferenciaBancaria transferencia = new TransferenciaBancaria(mauro, hernan, 10000);
		TransferenciaBancaria transferencia2 = new TransferenciaBancaria(mauro, hernan, 10000);
		try {
			transferencia.ejecutar();
			transferencia.revertir();
			transferencia2.ejecutar();
		} catch (ErrorSaldo | ErrorCuenta e) {
			System.out.println(e.getMessage());
		}
		Assert.assertEquals(30000, 40000, 0.01);
	}
}