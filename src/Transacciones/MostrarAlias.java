package Transacciones;

import Cuentas.Cuenta;
import Cuentas.ErrorSaldo;

import java.util.Date;

public class MostrarAlias extends Transaccion {
    public MostrarAlias(Cuenta origen) {
        super(origen);
    }

    public boolean requiereMonto() {
        return false;
    };

    @Override
    public String getTipo() {
        return "Mostrar alias";
    }

    @Override
    public String ejecutar() throws ErrorSaldo, ErrorCuenta {
        setFechaCreacion(new Date());
        return getOrigen().getAlias();
    }
}
