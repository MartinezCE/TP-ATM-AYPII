package Perifericos;
import java.util.Scanner;

public class Teclado {
	Scanner teclado = new Scanner(System.in);
	Pantalla pantalla = new Pantalla();
	Cifra cifra = new Cifra();
	int maxDigitosTarjeta = 8;

	public long leerNumeroDeTarjeta() {
		long numeroDeTarjeta = teclado.nextLong();
		if (cifra.obtenerCifraLong(numeroDeTarjeta) != maxDigitosTarjeta) {
			System.out.print("Debe ingresar 8 digitos: ");
			leerNumeroDeTarjeta();
		}
		return numeroDeTarjeta;
	}

	public int leerNumeroDePin() {
		int numeroDePin = teclado.nextInt();
		if (cifra.obtenerCifraInt(numeroDePin) != 4) {
			System.out.print("Debe ingresar 4 digitos: ");
			leerNumeroDePin();
		}
		return numeroDePin;
	}

	public int selecionDeTipoCuenta() {
		int i = teclado.nextInt();
		if (i < 1 || i > 3) {
			System.out.print("Seleccion invalida, numero de operación incorrecto");
			this.selecionDeTipoCuenta();
		}
		return i;
	}

	public int selecionDeTipoOperacion() {
		int i = teclado.nextInt();
		if (i < 1 && i > 6) {
			System.out.print("Seleccion invalida, numero de operación incorrecto");
			this.selecionDeTipoOperacion();
		}
		return i;
	}

	public long getMonto() {
		return teclado.nextInt();
	}

	public boolean getOpcionTicket() {
		int i = teclado.nextInt();
		return i == 1;
	}
}
