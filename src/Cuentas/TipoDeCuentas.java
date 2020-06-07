package Cuentas;

enum TipoDeCuenta {
    CajaDeAhorroPesos(1),
    CuentaCorriente(2),
    CajaDeAhorroEnDolares(3);
    private final int valor;
    TipoDeCuenta(int id){valor = id;}
    int getValor(){return valor;}
}