package Dispensador;


import java.util.Calendar;

public class Dia {

	private Calendar calendario = Calendar.getInstance();
	private int dia = calendario.get(Calendar.DATE);
	
	
	public boolean compareTo(Dia otro){
		
		if(this.dia < otro.dia) {
	    	
	    	return true;
		}
		else {
			return false;
		}
		
	}

}
