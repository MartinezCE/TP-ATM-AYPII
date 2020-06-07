package Cuentas;
import Transacciones.Transaccion;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Cliente {
    private long numeroTarjeta;
    private int pin;
    private long cuit = 0;
    private Cuenta cuentaActiva;
    private HashMap<Integer, Cuenta> cuentas = new HashMap<Integer, Cuenta>();
    private Stack<Transaccion> transacciones = new Stack<Transaccion>();

    public void setNumeroTarjeta(long numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public Cuenta getCuentaActiva() {
        return cuentaActiva;
    }

    public void setCuentaActiva(Cuenta cuentaActiva) {
        this.cuentaActiva = cuentaActiva;
    }

    //TODO: Customizar el error, "No posee xxxx (nombre de la cuenta)" --> usar un enum
    public Cuenta getCuenta(int id) {
        Cuenta cuenta = cuentas.get(id);
        if(cuenta == null) {
            throw new Error("No posee el tipo de cuenta " + id);
        }
        return cuenta;
    }

    public HashMap<Integer, Cuenta> getCuentas() {
        return cuentas;
    }

    public void login() throws TarjetaNoReconocida, PinInvalido, NoHayCuentasDisponibles {
        this.getInfoValidaciones();
        this.getInfoClientes();
    }

    private void getInfoValidaciones() throws PinInvalido, TarjetaNoReconocida {
        try {
            List<String[]> clientes = getInfoFile("validaciones.txt");
            ListIterator<String[]> iterator = clientes.listIterator();

            while ((iterator.hasNext()) && (this.cuit == 0)) {
                String[] cliente = iterator.next();
                long numeroTarjeta = Long.parseLong(cliente[0]);
                long pin = Long.parseLong(cliente[1]);
                long cuit = Long.parseLong(cliente[2]);

                if(numeroTarjeta == this.numeroTarjeta && pin == this.pin ) {
                    this.cuit = cuit;
                }else if(pin != this.pin){
                    throw new PinInvalido();
                }
            }
            if(this.cuit == 0) {
                throw new TarjetaNoReconocida();
            }
        }catch (IOException e) {
            //TODO: RETORNA UN ERROR MAS FRIENDLY, "HUBO UN PROBLEMA EN NUESTRA BASE, VUELVA A INTENTERLO MAS TARDE."
        }
    }

    private void getInfoClientes() throws NoHayCuentasDisponibles {
        try {
            List<String> listaAlias = new ArrayList<String>();
            List<String[]> cuentas = getInfoFile("clientes.txt");

            for (String[] cuenta : cuentas) {
                long cuitAlmacenado = Long.parseLong(cuenta[0]);
                if (this.cuit == cuitAlmacenado) {
                    listaAlias.add(cuenta[1]);
                }
            }
            if (listaAlias.size() == 0) {
                throw new NoHayCuentasDisponibles("No existen más cuentas disponibles");
            } else {
                this.getInfoCuentas(listaAlias);
            }
        }catch (IOException e) {
            //TODO: RETORNA UN ERROR MAS FRIENDLY, "HUBO UN PROBLEMA EN NUESTRA BASE, VUELVA A INTENTERLO MAS TARDE."
        }
    }

    private void getInfoCuentas(List<String> listaAlias) {
        try {
            List<String[]> cuentas = getInfoFile("cuentas.txt");
            for(String[] cuenta: cuentas) {
                int tipoCuenta = Integer.parseInt(cuenta[0]);
                String alias = cuenta[1];
                double monto = Double.parseDouble(cuenta[2]);
                double descubierto = (cuenta.length == 4) ? Double.parseDouble(cuenta[3]) : 0;

                if(listaAlias.contains(alias)) {
                    if(tipoCuenta == TipoDeCuenta.CajaDeAhorroPesos.getValor()) {
                        this.cuentas.put(tipoCuenta, new CajaAhorroPesos(alias, monto));
                    }else if(tipoCuenta == TipoDeCuenta.CuentaCorriente.getValor()) {
                        this.cuentas.put(tipoCuenta, new CuentaCorriente(alias, monto, descubierto));
                    }else if(tipoCuenta == TipoDeCuenta.CajaDeAhorroEnDolares.getValor()) {
                        this.cuentas.put(tipoCuenta, new CajaAhorroDolares(alias, monto));
                    }else {
                        //TODO: Crear custom error para el error de cuenta no disponible (Error en el archivo)
                        throw new Error("No encontro cuentas disponibles");
                    }
                }
            }
        }catch (IOException e) {
            //TODO: RETORNA UN ERROR MAS FRIENDLY, "HUBO UN PROBLEMA EN NUESTRA BASE, VUELVA A INTENTERLO MAS TARDE."
        }
    }

    private List<String[]> getInfoFile(String fileName) throws IOException {
        String path = System.getProperty("user.dir");
        List<String[]> registros = new ArrayList<String[]>();
        File myObj = new File(path + "/" +fileName);
        Scanner reader = new Scanner(myObj);
        while (reader.hasNextLine()) {
            String reg = reader.nextLine();
            String[] data = reg.split(",");
            registros.add(data);
        }
        reader.close();
        return registros;
    }

    public void agregarTransaccion(Transaccion transaccion) throws IOException {
        this.transacciones.push(transaccion);
        //TODO: Quizas la misma transaccion deba guardarse en el archivo de movimientos
        this.guardarTransacciones(transaccion);
    }

    public Stack<Transaccion> getTransacciones() {
        return transacciones;
    }

    //TODO: Validar si la pila posee transacciones antes de ejecutarse
    public Transaccion getTransaccion() {
        return this.transacciones.peek();
    }

    //TODO: Quizas la misma transaccion deba guardarse en el archivo de movimientos
    private static void guardarTransacciones(Transaccion transaccion) throws IOException {
        String path = System.getProperty("user.dir");
        FileWriter flwriter = null;
        flwriter = new FileWriter(path + "/movimientos.txt", true);
        BufferedWriter bfwriter = new BufferedWriter(flwriter);
        bfwriter.write(transaccion.getFechaCreacion() + ", " +
                transaccion.getTipo() + "," +
                transaccion.getOrigen().getAlias() + ", $" +
                String.format("%.2f",transaccion.getMonto()));
        bfwriter.newLine();
        bfwriter.close();
    }
}