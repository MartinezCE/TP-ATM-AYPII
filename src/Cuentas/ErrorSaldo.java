package Cuentas;

public class ErrorSaldo extends Exception{

	private static final long serialVersionUID = 7187955023532664294L;
	
	public ErrorSaldo(String s){
		super(s);
	}
}
