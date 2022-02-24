package Comprobante;

import Transacciones.Transaccion;
import Transacciones.TransferenciaBancaria;
import java.io.IOException;
import java.io.PrintWriter;

public class ComprobanteRevertirTransferencia extends ComprobanteRevertirTransaccion {
	@Override
	public String imprimirComprobante(Transaccion transaccion) {
		String ticket="";
		PrintWriter archivo;
		try {
			ticket=transaccion.getFechaCreacion() + "," + transaccion.getTipo() + "," + transaccion.getOrigen().getTipo()
					+ "," +((TransferenciaBancaria)transaccion).getDestino().getTipo()+"," +transaccion.getMonto();
			archivo = generarArchivo("ticket.txt");
			archivo.println(ticket);
			archivo.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ticket;
	}
}
