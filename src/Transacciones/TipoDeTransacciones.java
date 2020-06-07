package Transacciones;

public enum TipoDeTransacciones {
    Retirar(1),
    Comprar(2),
    Depositar(3),
    Transferir(4),
    MonstrarAlias(6),
    MostrarUltimasTransacciones(7);
    private final int valor;
    TipoDeTransacciones(int id){valor = id;}
    public int getValor(){return valor;}
}
