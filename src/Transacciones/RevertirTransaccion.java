package Transacciones;

import Cuentas.ErrorSaldo;

public interface RevertirTransaccion {
    public void revertir() throws ErrorSaldo, ErrorCuenta;
}
