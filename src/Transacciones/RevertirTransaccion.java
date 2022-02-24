package Transacciones;

import Cuentas.ErrorSaldo;
import Exceptions.OperacionErroneaParaTipoMoneda;

public interface RevertirTransaccion {
    public String revertir() throws ErrorSaldo, ErrorCuenta, OperacionErroneaParaTipoMoneda;
}
