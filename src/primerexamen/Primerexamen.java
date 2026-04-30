/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package primerexamen;
import java.util.Scanner;
public class Primerexamen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Controlador controlador = new Controlador();
        int op = -1;

        do {
            System.out.println("INNOVADORES S.A. - RRHH ");
            System.out.println(" 1. Registrar trabajador  ");
            System.out.println(" 2. Listar trabajadores   ");
            System.out.println(" 0. Salir                 ");
            System.out.print("Opción: ");

            try {
                op = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  [!] Ingrese un número válido.");
                op = -1;
            }

            switch (op) {
                case 1 -> controlador.agregar();
                case 2 -> controlador.listar();
                case 0 -> System.out.println("  Hasta luego.");
                case -1 -> {} // ya se mostró el error
                default -> System.out.println("  [!] Opción no válida.");
            }

        } while (op != 0);
    }
}
