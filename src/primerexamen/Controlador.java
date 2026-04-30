/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package primerexamen;
import java.util.ArrayList;
import java.util.Scanner;

public class Controlador {
    private ArrayList<Trabajador> lista = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    // ─── Agregar trabajador ───────────────────────────────────────
    public void agregar() {
        Trabajador t = new Trabajador();

        System.out.print("Nombre       : "); t.setNombre(sc.nextLine().trim());
        System.out.print("Ap. Paterno  : "); t.setApP(sc.nextLine().trim());
        System.out.print("Ap. Materno  : "); t.setApM(sc.nextLine().trim());

        t.setDocumento(sc);
        t.setRegimen(sc);
        t.setFondoPension(sc);

        // Sueldo base con control de excepciones
        boolean okSueldo = false;
        do {
            System.out.print("Sueldo base  : ");
            try {
                double s = Double.parseDouble(sc.nextLine().trim());
                if (s > 0) { t.setSueldoBase(s); okSueldo = true; }
                else { System.out.println("  [!] El sueldo debe ser mayor a 0."); }
            } catch (NumberFormatException e) {
                System.out.println("  [!] Ingrese un número válido.");
            }
        } while (!okSueldo);

        // Hijos
        System.out.print("¿Tiene hijos? (S/N): ");
        t.setTieneHijos(sc.nextLine().trim().equalsIgnoreCase("S"));

        // Turno nocturno
        System.out.print("¿Trabaja en turno nocturno? (S/N): ");
        t.setTrabajaNoche(sc.nextLine().trim().equalsIgnoreCase("S"));

        lista.add(t);
        System.out.println("  [✓] Trabajador registrado correctamente.");
    }

    // ─── Listar todos ─────────────────────────────────────────────
    public void listar() {
        System.out.println("\n===== LISTA DE TRABAJADORES (" + lista.size() + ") =====");
        if (lista.isEmpty()) {
            System.out.println("  No hay trabajadores registrados.");
            return;
        }
        int i = 1;
        for (Trabajador t : lista) {
            System.out.println("--- Trabajador #" + i++ + " ---");
            t.mostrar();
        }
    }
}