package Cuentas;

public class TarjetaNoReconocida extends Exception {

	public TarjetaNoReconocida() {
        super("La tarjeta ingresada no esta registrada en nuestra base de datos");
    }
	
}
