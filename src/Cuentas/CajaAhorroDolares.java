package Cuentas;

import Transacciones.*;

import java.util.HashMap;

public class CajaAhorroDolares extends Cuenta {
    public CajaAhorroDolares(String alias, double saldo) {
        super(alias, saldo);
    }

    @Override
    public String getTipo() {
        return "Caja de Ahorros Dolares";
    }

    @Override
    public HashMap<Integer, Transaccion> transaccionesDisponibles() {
        //TODO: Chequear el orden de las transacciones
        HashMap<Integer, Transaccion> transacciones = new HashMap<Integer, Transaccion>();
        //TODO: Chequear de agregar la cuenta destino, quizas sea mejor con un setter
        transacciones.put(TipoDeTransacciones.Depositar.getValor(), new ComprarDolares(this));
        return transacciones;
    }

    @Override
    public boolean operaMoneda(Moneda moneda) {
        return moneda == Moneda.DOLARES;
    }

    @Override
    public boolean extraer(double monto, Moneda moneda) {
        //TODO: Crear custom error
        if (this.operaMoneda(moneda)) {
            throw new Error("No opera con el timpo de moneda");
        }
        double saldo = this.getSaldo();
        if(saldo < monto) {
            return false;
        }
        saldo -= monto;
        this.setSaldo(saldo);
        return true;
    }

    @Override
    public boolean depositar(double monto, Moneda moneda) {
        //TODO: Crear custom error
        if (this.operaMoneda(moneda)) {
            throw new Error("No opera con el timpo de moneda");
        }
        double saldo = this.getSaldo();
        saldo += monto;
        this.setSaldo(saldo);
        return true;
    }
}
