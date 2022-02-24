package Cuentas;

import Exceptions.OperacionErroneaParaTipoMoneda;
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
        HashMap<Integer, Transaccion> transacciones = new HashMap<Integer, Transaccion>();
        transacciones.put(TipoDeTransacciones.Depositar.getValor(), new ComprarDolares(this));
        transacciones.put(TipoDeTransacciones.MonstrarAlias.getValor(), new MostrarAlias(this));
        transacciones.put(TipoDeTransacciones.MostrarUltimasTransacciones.getValor(), new MostrarUltimasTransacciones(this));
        return transacciones;
    }

    @Override
    public boolean operaMoneda(Moneda moneda) {
        return moneda == Moneda.DOLARES;
    }

    @Override
    public boolean extraer(double monto, Moneda moneda) throws OperacionErroneaParaTipoMoneda {
        //TODO: Crear custom error
        if (!operaMoneda(moneda)) {
            throw new OperacionErroneaParaTipoMoneda();
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
    public boolean depositar(double monto, Moneda moneda) throws OperacionErroneaParaTipoMoneda {
        //TODO: Crear custom error
        if (!operaMoneda(moneda)) {
            throw new OperacionErroneaParaTipoMoneda();
        }
        double saldo = this.getSaldo();
        saldo += monto;
        this.setSaldo(saldo);
        return true;
    }
}
