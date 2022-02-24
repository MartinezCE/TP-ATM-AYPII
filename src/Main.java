import Cuentas.ErrorSaldo;
import Cuentas.NoHayCuentasDisponibles;
import Cuentas.PinInvalido;
import Cuentas.TarjetaNoReconocida;
import Dispensador.NoHaySaldoSuficiente;
import Exceptions.NoPoseeTipoDeCuenta;
import Exceptions.OperacionErroneaParaTipoMoneda;
import Perifericos.ATM;
import Transacciones.ErrorCuenta;

import java.io.IOException;

/**
 * @name: TP ATM
 * @description: Grupo Los Magios
 */
public class Main {
    public static void main (String[] args) throws ErrorSaldo, NoHaySaldoSuficiente, IOException, ErrorCuenta, NoPoseeTipoDeCuenta, OperacionErroneaParaTipoMoneda {
        ATM atm1 = new ATM();
        atm1.ejecutar();
    }
}
