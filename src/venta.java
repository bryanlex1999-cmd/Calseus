import java.util.ArrayList;

public class venta {
    public String codigo_de_venta;
    public String tipo;
    public cliente cliente;
    public ArrayList<zapato> productos;
    public ArrayList<Integer> cantidades;
    public double total;
    public envio envio;
    public String fecha_emision;

    public venta() {
    }
}