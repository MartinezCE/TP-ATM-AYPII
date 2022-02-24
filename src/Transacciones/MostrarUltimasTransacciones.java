package Transacciones;

import Cuentas.Cuenta;
import Cuentas.ErrorSaldo;
import Perifericos.Pantalla;

import java.util.Stack;

public class MostrarUltimasTransacciones extends Transaccion {
    public MostrarUltimasTransacciones(Cuenta origen) {
        super(origen);
    }

    public boolean requiereMonto() {
        return false;
    };

    @Override
    public String getTipo() {
        return "Mostrar últimas transacciones";
    }

    @Override
    public String ejecutar() throws ErrorSaldo, ErrorCuenta {
        StringBuilder ticket = new StringBuilder();
        Stack<Transaccion> transaccions = getOrigen().getHistorialTransacciones();
        for (Transaccion transaccion: transaccions) {
            ticket.append(transaccion.getComprobante().imprimirComprobante(transaccion)).append("\n");
        }
        return ticket.toString();
    }
}
