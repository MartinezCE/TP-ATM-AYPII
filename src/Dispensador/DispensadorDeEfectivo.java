package Dispensador;

public class DispensadorDeEfectivo {
	private billeteDeMil billetesDeMil;
	private billeteDeQuinientos billetesDeQuinientos;
	private billeteDeCien billetesDeCien;
	private Dia diaActual;

	public DispensadorDeEfectivo(int billetesDeMil, int billetesDeQuinientos, int billetesDeCien) {
		this.billetesDeMil = new billeteDeMil(billetesDeMil);
		this.billetesDeQuinientos = new billeteDeQuinientos(billetesDeQuinientos);
		this.billetesDeCien = new billeteDeCien(billetesDeCien);
		this.diaActual = new Dia();
	}

	public void Operar(double monto, Dia diaDeOperacion) throws NoHaySaldoSuficiente {
		if (validarFecha(diaDeOperacion)) {
			this.billetesDeMil.reiniciarValores(500);
			this.billetesDeQuinientos.reiniciarValores(500);
			this.billetesDeCien.reiniciarValores(500);
			this.diaActual = diaDeOperacion;
		}
		if (!haySaldo(monto)) {
			throw new NoHaySaldoSuficiente();
		}
		monto = this.billetesDeMil.descontarMonto(monto);
		monto = this.billetesDeQuinientos.descontarMonto(monto);
		this.billetesDeCien.descontarMonto(monto);
	}

	private boolean validarFecha(Dia diaDeOperacion) {
		return this.diaActual.compareTo(diaDeOperacion);
	}

	private boolean haySaldo(double monto) {
		double saldoCajero = saldoCajero();
		if (monto <= saldoCajero) return true;
		else return false;
	}

	private double saldoCajero() {
		return billetesDeMil.getMonto() + billetesDeQuinientos.getMonto() + billetesDeCien.getMonto();
	}
}
