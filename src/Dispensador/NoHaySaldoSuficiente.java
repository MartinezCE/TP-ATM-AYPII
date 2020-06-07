package Dispensador;


public class NoHaySaldoSuficiente extends Exception {
	
	public NoHaySaldoSuficiente() {
        super("El monto es superior al dinero disponible en el cajero");
    }

}
