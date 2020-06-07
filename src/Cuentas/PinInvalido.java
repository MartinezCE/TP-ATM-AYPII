package Cuentas;

public class PinInvalido extends Exception {
	
	public PinInvalido() {
        super("El pin ingresado es inválido");
    }

}
