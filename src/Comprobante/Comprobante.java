package Comprobante;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import Transacciones.Transaccion;

public abstract class Comprobante {
	public PrintWriter generarArchivo(String ticket) throws IOException {
		FileWriter fichero = new FileWriter(ticket);
		return new PrintWriter(fichero);
	}
	public abstract String imprimirComprobante(Transaccion transaccion);
}
