import Cuentas.ErrorSaldo;
import Cuentas.NoHayCuentasDisponibles;
import Cuentas.PinInvalido;
import Cuentas.TarjetaNoReconocida;
import Dispensador.NoHaySaldoSuficiente;
import Perifericos.ATM;
import Transacciones.ErrorCuenta;

import java.io.IOException;

/**
 * @name: TP ATM
 * @description: Grupo Los Magios
 */
public class Main {
    public static void main (String[] args) {
        ATM atm1 = new ATM();
        atm1.ejecutar();
    }
}
