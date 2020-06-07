package Cuentas;

import Transacciones.Transaccion;
import java.util.HashMap;

public abstract class Cuenta {
    private String alias;
    private double saldo;

    public Cuenta(String alias, double saldo) {
        this.alias = alias;
        this.saldo = saldo;
    }

    public String getAlias() {
        return alias;
    }

    protected double getSaldo() {
        return saldo;
    }

    protected void setSaldo(double monto) {
        this.saldo = monto;
    }

    public abstract String getTipo();

    public abstract HashMap<Integer, Transaccion> transaccionesDisponibles();

    public abstract boolean operaMoneda(Moneda moneda);

    public abstract boolean extraer(double monto, Moneda moneda);

    public abstract boolean depositar(double monto, Moneda moneda);
}