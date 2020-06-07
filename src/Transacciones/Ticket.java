package Transacciones;

public class Ticket {
	private Transaccion transaccion;

	public Ticket(Transaccion transaccion) {
		this.transaccion = transaccion;
	}



	public String getTicket() {
		return  transaccion.getFechaCreacion() + ", " +
				transaccion.getOrigen().getTipo() + "," +
				transaccion.getTipo() + ", $" +
				String.format("%.2f",transaccion.getMonto()) + ", Saldo actual $" +
				String.format("%.2f",transaccion.getOrigen().getSaldo());
	}

	public String getTicketDosCajas() {
		return transaccion.getFechaCreacion() + ", " +
			   transaccion.getOrigen().getTipo()  + ", $" +
			   String.format("%.2f",transaccion.getOrigen().getSaldo()) + "," +
			   transaccion.getTipo() + ", $" +
			   String.format("%.2f",transaccion.getMonto()) + ", a " +
			   transaccion.getDestino().getTipo() + ", Saldo actual $" +
			   String.format("%.2f", transaccion.getDestino().getSaldo());
		
	}
}
