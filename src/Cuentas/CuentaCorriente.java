package Cuentas;

import Transacciones.*;

import java.util.HashMap;

public class CuentaCorriente extends Cuenta {
    double descubierto;

    public CuentaCorriente(String alias, double saldo, double descubierto) {
        super(alias, saldo);
        this.descubierto = descubierto;
    }

    @Override
    public String getTipo() {
        return "Cuenta Corriente";
    }

    @Override
    public HashMap<Integer, Transaccion> transaccionesDisponibles() {
        //TODO: Chequear el orden de las transacciones
        HashMap<Integer, Transaccion> transacciones = new HashMap<Integer, Transaccion>();
        transacciones.put(TipoDeTransacciones.Retirar.getValor(), new RetirarPesos(this));
        //TODO: Chequear de agregar la cuenta destino, quizas sea mejor con un setter
        transacciones.put(TipoDeTransacciones.Comprar.getValor(), new ComprarDolares(this));
        transacciones.put(TipoDeTransacciones.Depositar.getValor(), new DepositoPesos(this));
        //TODO: Chequear de agregar la cuenta destino, quizas sea mejor con un setter
        transacciones.put(TipoDeTransacciones.Transferir.getValor(), new TransferenciaBancaria(this));
        return transacciones;
    }

    @Override
    public boolean operaMoneda(Moneda moneda) {
        return moneda == Moneda.PESOS;
    }

    @Override
    public boolean extraer(double monto, Moneda moneda) {
        //TODO: Crear custom error
        if (this.operaMoneda(moneda)) {
            throw new Error("No opera con el timpo de moneda");
        }
        double saldo = this.getSaldo();
        if(saldo < monto) {
            saldo -= monto;
            if (Math.abs(saldo) > descubierto) {
                return false;
            }
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