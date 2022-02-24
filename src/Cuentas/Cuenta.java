package Cuentas;

import Exceptions.OperacionErroneaParaTipoMoneda;
import Transacciones.Transaccion;
import java.util.HashMap;
import java.util.Stack;

public abstract class Cuenta {
    private String alias;
    private double saldo;
    private Stack<Transaccion> historialTransacciones = new Stack<Transaccion>();

    public Cuenta(String alias, double saldo) {
        this.alias = alias;
        this.saldo = saldo;
    }

    public String getAlias() {
        return alias;
    }

    public Stack<Transaccion> getHistorialTransacciones() {
        return historialTransacciones;
    }

    public void setHistorialTransacciones(Transaccion transaccion) {
        this.historialTransacciones.push(transaccion);
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

    public abstract boolean extraer(double monto, Moneda moneda) throws OperacionErroneaParaTipoMoneda;

    public abstract boolean depositar(double monto, Moneda moneda) throws OperacionErroneaParaTipoMoneda;
}