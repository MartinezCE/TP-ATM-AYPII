package Cuentas;
import Exceptions.CuentaNoDisponible;
import Exceptions.ErrorEnBaseDeDatos;
import Exceptions.NoPoseeTipoDeCuenta;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Cliente {
    private long numeroTarjeta;
    private int pin;
    private long cuit = 0;
    private Cuenta cuentaActiva;
    private HashMap<Integer, Cuenta> cuentas = new HashMap<Integer, Cuenta>();

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

    public Cuenta getCuenta(int id) throws NoPoseeTipoDeCuenta {
        Cuenta cuenta = cuentas.get(id);
        if(cuenta == null) {
            throw new NoPoseeTipoDeCuenta("No posee el tipo de cuenta " + id);
        }
        return cuenta;
    }

    public HashMap<Integer, Cuenta> getCuentas() {
        return cuentas;
    }

    public void login()throws TarjetaNoReconocida, PinInvalido, NoHayCuentasDisponibles, ErrorEnBaseDeDatos, CuentaNoDisponible {
        this.getInfoValidaciones();
        this.getInfoClientes();
    }

    public boolean cuentaOperanMoneda(Moneda moneda) {
        for(Map.Entry<Integer, Cuenta> cuenta : getCuentas().entrySet()) {
            if(cuenta.getValue().operaMoneda(moneda)) {
                return true;
            }
        }
        return false;
    }

    private void getInfoValidaciones() throws ErrorEnBaseDeDatos {
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
                }else if(numeroTarjeta == this.numeroTarjeta && pin != this.pin){
                    throw new PinInvalido();
                }
            }
            if(this.cuit == 0) {
                throw new TarjetaNoReconocida();
            }
        }catch (IOException | TarjetaNoReconocida | PinInvalido e) {
            throw new ErrorEnBaseDeDatos();
        }
    }

    private void getInfoClientes() throws NoHayCuentasDisponibles, ErrorEnBaseDeDatos {
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
            throw new ErrorEnBaseDeDatos();
        }
    }

    private void getInfoCuentas(List<String> listaAlias) throws ErrorEnBaseDeDatos {
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
                        throw new CuentaNoDisponible();
                    }
                }
            }
        }catch (IOException | CuentaNoDisponible e) {
            throw new ErrorEnBaseDeDatos();
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
}