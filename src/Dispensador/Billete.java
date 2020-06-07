package Dispensador;


public abstract class Billete {
	double valor=0;
	double monto=0;
	int cantidad=0;

	public Billete(int cantidadDeBilletes,double valor) {

		this.cantidad=cantidadDeBilletes;
		this.valor=valor;
		monto=valor*cantidad;
	}


	public boolean hayBilletes() {

		return (cantidad!=0) || false;
	  }

	public double getCantidadDeBilletes(){

		return cantidad;
	}

	public double getMonto(){

		return monto;
	}

	public void setCantidad(int cantidad) {

		this.cantidad=cantidad;
	}

	public void setMonto(double monto) {
		this.monto=monto;
	}

	public void reiniciarValores(int cantidad) {
		this.cantidad=cantidad;
		this.monto=cantidad*valor;
	}

	public double descontarMonto(double monto) {
		double resto=monto;
		int cantidadDeBilletes=(int) (monto%valor);
		cantidad-=cantidadDeBilletes;
		this.monto-=cantidadDeBilletes*valor;
		resto-=cantidadDeBilletes*valor;

		return resto;
	}
}
