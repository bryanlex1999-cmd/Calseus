import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    // COLECCIONES
    public static Map<String, String> diccionario_usuarios = new HashMap<>();
    public static Map<String, cliente> diccionario_clientes = new HashMap<>();
    public static Map<String, zapato> indice_zapatos = new HashMap<>();
    public static ArrayList<venta> lista_ventas = new ArrayList<>();
    public static Map<String, envio> indice_envios = new HashMap<>();

    public static int contador_boleta = 1;
    public static int contador_factura = 1;
    public static int contador_envio = 1;

    // MAIN
    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        cargar_usuarios();

        if (!inicio_sesion(lector)) {
            System.out.println("FIN DEL PROGRAMA.");
            return;
        }

        mostrar_menu_principal(lector);
        lector.close();
    }

    // LOGIN
    public static void cargar_usuarios() {
        diccionario_usuarios.put("admin", "1234");
        diccionario_usuarios.put("ventas", "calseus");
    }

    public static boolean inicio_sesion(Scanner lector) {
        int intentos = 0;
        while (intentos < 3) {
            System.out.println("=== INICIO DE SESIÓN ===");
            System.out.print("Usuario: ");
            String usuario = lector.nextLine();
            System.out.print("Contraseña: ");
            String contrasena = lector.nextLine();

            if (diccionario_usuarios.containsKey(usuario)
                    && diccionario_usuarios.get(usuario).equals(contrasena)) {
                System.out.println("USUARIO VÁLIDO");
                return true;
            } else {
                System.out.println("USUARIO INVÁLIDO");
                intentos++;
            }
        }
        return false;
    }

    // MENÚ PRINCIPAL
    public static void mostrar_menu_principal(Scanner lector) {
        int opcion;
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n===== MENÚ PRINCIPAL – CALSEUS =====");
            System.out.println("1) Registrar");
            System.out.println("2) Buscar");
            System.out.println("3) Actualizar");
            System.out.println("4) Salir");
            System.out.print("Elija una opción: ");
            opcion = leer_entero(lector);

            switch (opcion) {
                case 1:
                    mostrar_menu_registrar(lector);
                    break;
                case 2:
                    mostrar_menu_buscar(lector);
                    break;
                case 3:
                    mostrar_menu_actualizar(lector);
                    break;
                case 4:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción incorrecta.");
                    break;
            }
        }
    }

    // MENÚ REGISTRAR
    public static void mostrar_menu_registrar(Scanner lector) {
        int opcion;
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- REGISTRAR ---");
            System.out.println("1) Zapato");
            System.out.println("2) Cliente");
            System.out.println("3) Venta");
            System.out.println("4) Envio");
            System.out.println("5) Volver");
            System.out.print("Opción: ");
            opcion = leer_entero(lector);

            switch (opcion) {
                case 1:
                    registrar_zapato(lector);
                    break;
                case 2:
                    registrar_cliente(lector);
                    break;
                case 3:
                    registrar_venta(lector);
                    break;
                case 4:
                    registrar_envio(lector);
                    break;
                case 5:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción incorrecta.");
                    break;
            }
        }
    }

    // REGISTRAR ZAPATO
    public static void registrar_zapato(Scanner lector) {
        System.out.println("\n=== REGISTRAR ZAPATO ===");
        System.out.print("Marca: ");
        String marca = lector.nextLine();
        System.out.print("Modelo: ");
        String modelo = lector.nextLine();
        System.out.print("Talla: ");
        int talla = leer_entero(lector);
        System.out.print("Precio (S/.): ");
        double precio = leer_double(lector);
        System.out.print("Cantidad: ");
        int cantidad = leer_entero(lector);

        if (talla < 35 || talla > 50) {
            System.out.println("ZAPATO NO REGISTRADO: talla inválida.");
            return;
        }
        if (precio < 300 || precio > 800) {
            System.out.println("ZAPATO NO REGISTRADO: precio inválido.");
            return;
        }
        if (cantidad < 0) {
            System.out.println("ZAPATO NO REGISTRADO: cantidad inválida.");
            return;
        }

        String clave = modelo + "|" + talla;
        if (indice_zapatos.containsKey(clave)) {
            System.out.println("ZAPATO YA EXISTE.");
            return;
        }

        zapato z = new zapato();
        z.marca = marca;
        z.modelo = modelo;
        z.talla = talla;
        z.precio = precio;
        z.cantidad = cantidad;
        indice_zapatos.put(clave, z);
        System.out.println("ZAPATO REGISTRADO CORRECTAMENTE.");
    }

    // REGISTRAR CLIENTE / VENTA / ENVÍO
    public static void registrar_cliente(Scanner lector) {
        System.out.println("\n=== REGISTRAR CLIENTE ===");
        System.out.print("DNI / RUC : ");
        String documento = lector.nextLine();      // “dni” o “ruc”
        System.out.print("Número: ");
        String numero = lector.nextLine();         // número del documento
        System.out.print("Nombre y Apellido / Razón social: ");
        String nombre_razon = lector.nextLine();
        System.out.print("Teléfono: ");
        String telefono = lector.nextLine();
        System.out.print("Dirección: ");
        String direccion = lector.nextLine();
        System.out.print("Distrito: ");
        String distrito = lector.nextLine();
        System.out.print("Provincia: ");
        String provincia = lector.nextLine();
        System.out.print("Departamento: ");
        String departamento = lector.nextLine();

        if (diccionario_clientes.containsKey(numero)) {
            System.out.println("CLIENTE YA EXISTE.");
            return;
        }

        cliente c = new cliente();
        c.documento = documento;
        c.numero = numero;
        c.nombre_razon = nombre_razon;
        c.telefono = telefono;
        c.direccion = direccion;
        c.distrito = distrito;
        c.provincia = provincia;
        c.departamento = departamento;

        diccionario_clientes.put(numero, c);
        System.out.println("CLIENTE REGISTRADO CORRECTAMENTE.");
    }

    public static void registrar_venta(Scanner lector) {
        System.out.println("\n=== REGISTRAR VENTA ===");
        System.out.print("Número de documento del cliente: ");
        String numero = lector.nextLine();
        if (!diccionario_clientes.containsKey(numero)) {
            System.out.println("CLIENTE NO ENCONTRADO.");
            return;
        }
        cliente c = diccionario_clientes.get(numero);

        System.out.print("Tipo de comprobante (boleta/factura): ");
        String tipo = lector.nextLine();

        // Crear venta vacía
        venta v = new venta();
        v.tipo = tipo.toLowerCase();
        v.codigo_de_venta = generar_codigo_venta(v.tipo);
        v.cliente = c;
        v.productos = new ArrayList<zapato>();
        v.cantidades = new ArrayList<Integer>();
        v.total = 0.0;
        v.fecha_emision = obtener_fecha_actual();

        boolean terminar = false;
        while (!terminar) {
            System.out.println("\n--- VENTA " + v.codigo_de_venta + " ---");
            System.out.println("Total acumulado: S/ " + formato_dos_decimales(v.total));
            System.out.println("1) Añadir artículo");
            System.out.println("2) Imprimir boleta");
            System.out.println("3) Cancelar Venta");
            System.out.print("Opción: ");
            int op = leer_entero(lector);

            switch (op) {
                case 1: {
                    System.out.print("Modelo del zapato: ");
                    String modelo = lector.nextLine();
                    System.out.print("Talla del zapato: ");
                    int talla = leer_entero(lector);
                    String clave = modelo + "|" + talla;

                    if (!indice_zapatos.containsKey(clave)) {
                        System.out.println("ZAPATO NO ENCONTRADO.");
                        break;
                    }
                    zapato z = indice_zapatos.get(clave);
                    System.out.println("Stock disponible: " + z.cantidad);
                    System.out.print("Cantidad a comprar: ");
                    int cantidad = leer_entero(lector);

                    if (cantidad <= 0 || cantidad > z.cantidad) {
                        System.out.println("Cantidad inválida o sin stock suficiente.");
                        break;
                    }

                    // descontar stock y agregar línea
                    z.cantidad -= cantidad;
                    v.productos.add(z);
                    v.cantidades.add(cantidad);
                    v.total += z.precio * cantidad;

                    System.out.println("Artículo agregado. Nuevo total: S/ " + formato_dos_decimales(v.total));
                    break;
                }
                case 2: { // finalizar
                    if (v.productos.isEmpty()) {
                        System.out.println("No hay artículos en la venta. No se imprimió boleta.");
                        return;
                    }
                    lista_ventas.add(v);
                    imprimir_boleta(v);
                    terminar = true; // vuelve al menú REGISTRAR
                    break;
                }
                case 3: { // cancelar y restaurar stock de todo lo agregado
                    for (int i = 0; i < v.productos.size(); i++) {
                        zapato z = v.productos.get(i);
                        int cant = v.cantidades.get(i);
                        z.cantidad += cant;
                    }
                    System.out.println("VENTA CANCELADA. Stock restaurado.");
                    return;
                }
                default:
                    System.out.println("Opción incorrecta.");
                    break;
            }
        }
    }

    public static void registrar_envio(Scanner lector) {
        System.out.println("\n=== REGISTRAR ENVÍO ===");
        System.out.print("Número de documento del cliente: ");
        String numero = lector.nextLine();
        if (!diccionario_clientes.containsKey(numero)) {
            System.out.println("CLIENTE NO ENCONTRADO.");
            return;
        }
        cliente c = diccionario_clientes.get(numero);
        System.out.print("Courier: ");
        String courier = lector.nextLine();
        System.out.print("Costo del envío: ");
        double costo = leer_double(lector);
        System.out.print("Fecha y hora programada (ej. 25/10/2025 14:00): ");
        String fecha_programada = lector.nextLine();

        envio e = new envio();
        e.codigo_de_envio = generar_codigo_envio();
        e.courier = courier;
        e.costo = costo;
        e.estado = "pendiente";
        e.documento_cliente = numero;
        e.direccion = c.direccion;
        e.fecha_emision = obtener_fecha_actual();
        e.fecha_programada = fecha_programada;
        indice_envios.put(e.codigo_de_envio, e);
        imprimir_voucher_envio(e, c);
    }

    // BUSCAR
    public static void mostrar_menu_buscar(Scanner lector) {
        int opcion;
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- BUSCAR ---");
            System.out.println("1) Cliente");
            System.out.println("2) Zapato");
            System.out.println("3) Venta");
            System.out.println("4) Envio");
            System.out.println("5) Volver");
            System.out.print("Opción: ");
            opcion = leer_entero(lector);

            switch (opcion) {
                case 1:
                    buscar_cliente(lector);
                    break;
                case 2:
                    buscar_zapato(lector);
                    break;
                case 3:
                    resumen_ventas(lector);
                    break;
                case 4:
                    resumen_envios(lector);
                    break;
                case 5:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción incorrecta.");
                    break;
            }
        }
    }

    public static void buscar_cliente(Scanner lector) {
        System.out.print("Número de documento del cliente: ");
        String numero = lector.nextLine();
        cliente c = diccionario_clientes.get(numero);
        if (c == null) {
            System.out.println("CLIENTE NO ENCONTRADO.");
            return;
        }
        System.out.println("Documento: " + c.documento);
        System.out.println("Número: " + c.numero);
        System.out.println("Nombre/Razón: " + c.nombre_razon);
        System.out.println("Teléfono: " + c.telefono);
        System.out.println("Dirección: " + c.direccion);
        System.out.println("Distrito: " + c.distrito);
        System.out.println("Provincia: " + c.provincia);
        System.out.println("Departamento: " + c.departamento);
    }

    public static void buscar_zapato(Scanner lector) {
        System.out.print("Modelo: ");
        String modelo = lector.nextLine();
        System.out.print("Talla: ");
        int talla = leer_entero(lector);
        zapato z = indice_zapatos.get(modelo + "|" + talla);
        if (z == null) {
            System.out.println("ZAPATO NO ENCONTRADO.");
            return;
        }
        System.out.println("Marca | Modelo | Talla | Cantidad | Precio");
        System.out.println(z.marca + " | " + z.modelo + " | " + z.talla + " | " + z.cantidad + " | S/ " + formato_dos_decimales(z.precio));
    }

    public static void resumen_ventas(Scanner lector) {
        if (lista_ventas.isEmpty()) {
            System.out.println("NO EXISTEN VENTAS REGISTRADAS.");
            return;
        }
        System.out.println("\nCódigo | Cliente / Razón social | Fecha | Total (S/)");
        for (venta v : lista_ventas) {
            String nombre = v.cliente.nombre_razon;
            System.out.println(v.codigo_de_venta + " | " + nombre + " | " + v.fecha_emision + " | S/ " + formato_dos_decimales(v.total));
        }
        System.out.print("\n¿Desea ver el detalle de una venta? (s/n): ");
        String resp = lector.nextLine();
        if (resp.equalsIgnoreCase("s")) {
            System.out.print("Ingrese el código de venta: ");
            buscar_venta(lector);
        }
    }

    public static void buscar_venta(Scanner lector) {
        String codigo = lector.nextLine();
        for (venta v : lista_ventas) {
            if (v.codigo_de_venta.equalsIgnoreCase(codigo)) {
                imprimir_boleta(v);
                return;
            }
        }
        System.out.println("VENTA NO ENCONTRADA.");
    }

    public static void resumen_envios(Scanner lector) {
        if (indice_envios.isEmpty()) {
            System.out.println("NO EXISTEN ENVÍOS REGISTRADOS.");
            return;
        }
        System.out.println("\nCódigo | Cliente / Razón social | Fecha programada | Estado");
        for (envio e : indice_envios.values()) {
            cliente c = diccionario_clientes.get(e.documento_cliente);
            String nombre = (c != null) ? c.nombre_razon : "-";
            System.out.println(e.codigo_de_envio + " | " + nombre + " | " + e.fecha_programada + " | " + e.estado);
        }
        System.out.print("\n¿Desea ver el detalle de un envío? (s/n): ");
        String resp = lector.nextLine();
        if (resp.equalsIgnoreCase("s")) {
            System.out.print("Ingrese el código de envío: ");
            buscar_envio(lector);
        }
    }

    public static void buscar_envio(Scanner lector) {
        String codigo = lector.nextLine();
        envio e = indice_envios.get(codigo);
        if (e == null) {
            System.out.println("ENVÍO NO ENCONTRADO.");
            return;
        }
        cliente c = diccionario_clientes.get(e.documento_cliente);
        imprimir_voucher_envio(e, c);
    }

    // ACTUALIZAR
    public static void mostrar_menu_actualizar(Scanner lector) {
        int opcion;
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- ACTUALIZAR ---");
            System.out.println("1) Eliminar zapato");
            System.out.println("2) Eliminar venta");
            System.out.println("3) Actualizar estado de envío");
            System.out.println("4) Volver");
            System.out.print("Opción: ");
            opcion = leer_entero(lector);

            switch (opcion) {
                case 1:
                    eliminar_zapato(lector);
                    break;
                case 2:
                    eliminar_venta(lector);
                    break;
                case 3:
                    actualizar_envio(lector);
                    break;
                case 4:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción incorrecta.");
                    break;
            }
        }
    }

    public static void eliminar_zapato(Scanner lector) {
        System.out.print("Modelo: ");
        String modelo = lector.nextLine();
        System.out.print("Talla: ");
        int talla = leer_entero(lector);
        String clave = modelo + "|" + talla;
        zapato z = indice_zapatos.get(clave);

        if (z == null) {
            System.out.println("ZAPATO NO ENCONTRADO.");
            return;
        }

        System.out.println("Stock actual: " + z.cantidad);
        System.out.print("Cantidad a eliminar: ");
        int eliminar = leer_entero(lector);

        if (eliminar <= 0) {
            System.out.println("Cantidad inválida.");
            return;
        }

        if (eliminar >= z.cantidad) {
            indice_zapatos.remove(clave);
            System.out.println("ZAPATO ELIMINADO COMPLETAMENTE.");
        } else {
            z.cantidad -= eliminar;
            System.out.println("STOCK ACTUALIZADO. Nuevo stock: " + z.cantidad);
        }
    }

    public static void eliminar_venta(Scanner lector) {
        System.out.print("Código de boleta o factura: ");
        String codigo = lector.nextLine();
        for (int i = 0; i < lista_ventas.size(); i++) {
            venta v = lista_ventas.get(i);
            if (v.codigo_de_venta.equalsIgnoreCase(codigo)) {
                // restaurar stock de todas las líneas
                for (int j = 0; j < v.productos.size(); j++) {
                    zapato z = v.productos.get(j);
                    int cant = v.cantidades.get(j);
                    z.cantidad += cant;
                }
                lista_ventas.remove(i);
                System.out.println("VENTA ELIMINADA y stock restaurado.");
                return;
            }
        }
        System.out.println("VENTA NO ENCONTRADA.");
    }

    public static void actualizar_envio(Scanner lector) {
        System.out.print("Código de envío: ");
        String codigo = lector.nextLine();
        envio e = indice_envios.get(codigo);
        if (e == null) {
            System.out.println("ENVÍO NO ENCONTRADO.");
            return;
        }

        System.out.println("Estado actual: " + e.estado);
        System.out.println("Seleccione nuevo estado:");
        System.out.println("1) Pendiente");
        System.out.println("2) En camino");
        System.out.println("3) Entregado");
        System.out.print("Opción: ");
        int op = leer_entero(lector);

        switch (op) {
            case 1:
                e.estado = "Pendiente";
                break;
            case 2:
                e.estado = "En camino";
                break;
            case 3:
                e.estado = "Entregado";
                break;
            default:
                System.out.println("Opción incorrecta. No se actualizó.");
                return;
        }
        System.out.println("ESTADO DE ENVÍO ACTUALIZADO A: " + e.estado);
    }

    // UTILIDADES
    public static String generar_codigo_venta(String tipo) {
        String prefijo = tipo.equalsIgnoreCase("boleta") ? "B" : "F";
        int numero = tipo.equalsIgnoreCase("boleta") ? contador_boleta++ : contador_factura++;
        return String.format("%s%04d", prefijo, numero);
    }

    public static String generar_codigo_envio() {
        return String.format("E%04d", contador_envio++);
    }

    public static String obtener_fecha_actual() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    }

    public static int leer_entero(Scanner lector) {
        try {
            return Integer.parseInt(lector.nextLine().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public static double leer_double(Scanner lector) {
        try {
            return Double.parseDouble(lector.nextLine().trim());
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static String formato_dos_decimales(double valor) {
        return String.format("%.2f", valor);
    }

    // IMPRESIONES=
    public static void imprimir_boleta(venta v) {
        cliente c = v.cliente;
        String nombre = c.nombre_razon;

        System.out.println("========================================");
        System.out.println("              CALSEUS");
        System.out.println("========================================");
        System.out.println("Código: " + v.codigo_de_venta);
        System.out.println("Fecha de emisión: " + v.fecha_emision);
        System.out.println("----------------------------------------");
        System.out.println("Cliente / Razón social : " + nombre);
        System.out.println("DNI/RUC                : " + c.numero);
        System.out.println("----------------------------------------");
        System.out.println("Marca     Modelo     Talla  Cant  Precio_Unit.  Importe");

        for (int i = 0; i < v.productos.size(); i++) {
            zapato z = v.productos.get(i);
            int cant = v.cantidades.get(i);
            double importe = z.precio * cant;
            System.out.println(
                    z.marca + "   " +
                            z.modelo + "   " +
                            z.talla  + "     " +
                            cant     + "     " +
                            "S/ " + formato_dos_decimales(z.precio) + "      " +
                            "S/ " + formato_dos_decimales(importe)
            );
        }

        System.out.println("----------------------------------------");
        System.out.println("TOTAL: S/ " + formato_dos_decimales(v.total));
        System.out.println("========================================");
        System.out.println("Gracias por su compra.");
    }

    public static void imprimir_voucher_envio(envio e, cliente c) {
        String nombre = (c != null) ? c.nombre_razon : "-";
        String numero = (c != null) ? c.numero : "-";
        String direccion = (c != null) ? c.direccion : "-";

        System.out.println("========================================");
        System.out.println("              CALSEUS");
        System.out.println("========================================");
        System.out.println("Código: " + e.codigo_de_envio);
        System.out.println("Fecha de emisión  : " + e.fecha_emision);
        System.out.println("Fecha programada  : " + e.fecha_programada);
        System.out.println("----------------------------------------");
        System.out.println("Cliente / Razón social : " + nombre);
        System.out.println("DNI/RUC                : " + numero);
        System.out.println("Dirección              : " + direccion);
        System.out.println("----------------------------------------");
        System.out.println("Courier     Costo (S/)   Estado");
        System.out.println(e.courier + "     S/ " + formato_dos_decimales(e.costo) + "     " + e.estado);
        System.out.println("----------------------------------------");
        System.out.println("TOTAL: S/ " + formato_dos_decimales(e.costo));
        System.out.println("========================================");
        System.out.println("Gracias por su preferencia.");
    }
}