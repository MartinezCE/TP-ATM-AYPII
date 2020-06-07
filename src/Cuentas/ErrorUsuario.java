package Cuentas;

public class ErrorUsuario extends Exception {
    private static final long serialVersionUID = 693019083772447597L;
    public ErrorUsuario(String msg) {
        super(msg);
    }
}
