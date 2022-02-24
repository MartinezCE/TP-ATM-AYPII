## Decisiones de diseño

El enfoque del diseño se basó en una estructura orientada a objetos, mediante la implementación de interfaces y herencia de clases abstractas.

## Estructura de archivos

## Clase cliente:

La clase cliente es la encargada de almacenar toda la información del usuario. A sí mismo contiene el listado de todas las cuentas que posee el usuario.

Además almacena todas las operaciones del cliente.

La clase cliente posee varios métodos getters y setters, además posee un método login que valida las credenciales del usuario.

## Paquete dispensador

## Clase DispensadorDeEfectivo:

Es la columna vertebral del dispensador, la clase contiene tres objetos de tipo billeteDeMil, billeteDeQuinientos y billeteDeCien además de un objeto de tipo Dia.

El DispensadorDeEfectivo presenta su constructor que inicializa los objetos mencionados anteriormente. Avanzando en el código llegamos al método Operar, el cual recibe el monto y el día de operación, éste se encarga de chequear como primera instancia si la operación a realizar se produce en el mismo día o no, en caso de ser un nuevo día, este método utiliza ReinicializarValores de la clase billete para reestablecer los valores a los iniciales. Para las operaciones en el mismo día, el método operar, primero chequea si hay saldo en el cajero, en caso de disponer de efectivo utiliza el método de la clase billete, DescontarMonto , para descontar del monto del cajero los retiros realizados por los usuarios (primero entrega los billetes de $1000, luego los de $500 y por último los de $100).

Si no hay saldo en el cajero se lanza una excepción personalizada.

SaldoDeCajero:

Éste método calcula el dinero disponible en el cajero.

Clase Dia:

Ésta clase se utiliza para comparar dos objetos día y establecer cuál es el mayor.

Clase Billete

Esta clase es abstracta y es la clase padre de billeteDeMil, billeteDeCien y billeteDeQuinientos.

Contiene los siguientes atributos: valor y monto de tipo double, cantidad de tipo int

La clase billete presenta un constructor que recibe como parámetros la cantidad de billetes y el valor individual de cada uno. En este constructor también se calcula el monto y se guarda en la variable correspondiente(monto)

Clase billete

Presenta los métodos:

HayBilletes, en donde se consulta por medio de un booleano si quedan billetes de ese tipo para entregar.

Getters y Setters correspondientes a cada atributo

ReiniciarValores, se encarga de reestablecer los valores de cantidad de billetes y monto del cajero a los iniciales del día

DescontarMonto, se encarga de calcular la cantidad de billetes restantes y monto luego del retiro del usuario. Utiliza la lógica del DispensadorDeEfectivo para ir descontando los billetes en el orden deseado y devuelve en cada descuento el resto de los billetes consecutivos.

Clases hijas de billete

BilleteDeMil,billeteDeQuinientos,BilleteDeCien

Éstas clases extienden de billete, sólo presentan un constructor y cada vez que son invocadas envían a la clase padre la cantidad de billetes de ese tipo y el valor unitario del billete.

**Package Perifericos**

**Class ATM** : Administra la funcionalidad del cajero.
 -Métodos

• ejecutar: ejecuta iniciarSesion y seleccionarCuenta.

• iniciarSesion: inicializa el número de tarjeta y pin ingresado por teclado.

• seleccionarCuenta: selecciona las opciones entre Cuenta Corriente, Caja de ahorro en pesos y en dólares.

• seleccionarTransaccion: Muestra por pantalla las transacciones disponibles para cada cuenta.

• ejecutarTransaccion: ejecuta la transacción seleccionada.

• crearDeposito: ingresa efectivo a una cuenta deseada excepto cuentas en dólares

• crearTransferencia: realiza transferencias entre cuentas en pesos.

• comprarDolar: realiza la compra de dólares descontando el saldo de las cuentas en pesos. Utilizando el valor dólar predefinido.

• crearExtraccion: cumple con la funcionalidad de extraer de una cuenta en pesos el monto deseado y descuenta el mismo de la terminal.

• crearRevertir: El método revierte por el momento las transferencias. A futuro puede aplicarse para otras operaciones.

• mostrarUltimaTransaccion: utiliza el método get de la clase ticket para mostrar la última transacción.

• siguienteOperacion: El método devuelve por pantalla las opciones para continuar operando.

**Class Cifra** _:_

-Métodos

• obtenerCifraInt: impide que la cantidad de cifras ingresadas por teclado no sea mayor a la esperada.

• obtenerCifraLong: impide que la cantidad de cifras ingresadas por teclado no sea mayor a la esperada.

**Class Pantalla** :

-Método

• imprimir: imprime por consola el mensaje ingresado por parámetro.

**Class Teclado** :

-Método

• leerNumeroDeTarjeta: Realiza la lectura del número de tarjeta ingresado por teclado

• leerNumeroDePin: Realiza la lectura del número de pin ingresado por teclado

• seleccionDeTipoCuenta: Se ingresa por teclado el tipo de cuenta deseada, 1 por CajaAhorroPesos, 2 por CuentaCorriente y 3 para CajaAhorroDolares.

• seleccionDeTipoOperacion: El método evalúa la operación requerida por el usuario y devuelve por pantalla el número correspondiente a la operación.

• getMonto: obtiene el monto previamente ingresado por teclado.

• getOpcionTicket: evalúa si se imprime o no el ticket y además de hacerlo material, lo muestra por pantalla.

**Package Transacciones**

**Class Transaccion** : Clase abstracta que hereda los getters y setters a las clases hijas:

Clases hijas:

• ComprarDolares:

• Ejecutar()

• DepositoPesos

• Ejecutar()

• RetirarPesos

• Ejecutar()

• TranferenciaBancaria

• Ejecutar()

• RevertirTransaccion

• Revertir()

**Class Ticket**

-Metodos:

• getTicket: devuelve el ticket de operaciones realizadas por cuenta.

• getTicketDosCajas: devuelve el ticket de operaciones realizadas entre dos cuentas

**Class ErrorCuenta** : excepción customizada.

**Clase Cuenta:** La clase Cuenta es la clase abstracta de la cual heredarán las otras clases.

La misma posee un set de atributos que serán utilizados por las subclases:

- id: Identificador del tipo de Cuenta

- alias: Alias de la cuenta del usuario.

- saldo: Es el saldo que posee la cuenta.

- tipo: Es el tipo de la cuenta, el mismo lo define la subclase.

- descubierto: (opcional) Es el saldo descubierto permitido, solo para Cuenta corrientes.

\*Lista de sub clases\*

- Caja de ahorro en pesos.

- Cuenta corriente.

- Caja de ahorro en dolares.