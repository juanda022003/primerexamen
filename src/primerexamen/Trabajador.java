/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package primerexamen;
import java.util.Scanner;
public class Trabajador {
  private String nombre;
    private String apP;
    private String apM;
    private String tipoDocumento;   // "DNI" o "CE"
    private String documento;
    private String regimen;         // "728" o "LOCACION"
    private String fondoPension;    // "ONP", "INTEGRA", "PRIMA", "HABITAT"
    private double sueldoBase;
    private boolean tienehijos;
    private boolean trabajaNoche;

    // ─── Setters simples ─────────────────────────────────────────
    public void setNombre(String nombre)   { this.nombre = nombre; }
    public void setApP(String apP)         { this.apP = apP; }
    public void setApM(String apM)         { this.apM = apM; }
    public void setSueldoBase(double s)    { this.sueldoBase = s; }
    public void setTieneHijos(boolean b)   { this.tienehijos = b; }
    public void setTrabajaNoche(boolean b) { this.trabajaNoche = b; }

    // ─── Validación: Tipo de documento ───────────────────────────
    public void setDocumento(Scanner sc) {
        // Paso 1: tipo
        boolean okTipo = false;
        do {
            System.out.print("Tipo documento (DNI / CE): ");
            String tipo = sc.nextLine().trim().toUpperCase();
            if (tipo.equals("DNI") || tipo.equals("CE")) {
                tipoDocumento = tipo;
                okTipo = true;
            } else {
                System.out.println("  [!] Solo se acepta DNI o CE.");
            }
        } while (!okTipo);

        // Paso 2: número según tipo
        boolean okNum = false;
        do {
            System.out.print("Número de documento: ");
            String num = sc.nextLine().trim();
            if (tipoDocumento.equals("DNI")) {
                if (num.matches("\\d{8}")) {
                    documento = num;
                    okNum = true;
                } else {
                    System.out.println("  [!] DNI debe tener exactamente 8 dígitos.");
                }
            } else { // CE = Residencia Temporal
                if (num.matches("\\d{11}")) {
                    documento = num;
                    okNum = true;
                } else {
                    System.out.println("  [!] CE (Residencia Temporal) debe tener exactamente 11 dígitos.");
                }
            }
        } while (!okNum);
    }

    // ─── Validación: Régimen laboral ─────────────────────────────
    public void setRegimen(Scanner sc) {
        boolean ok = false;
        do {
            System.out.println("Régimen laboral:");
            System.out.println("  [1] Régimen 728 (planilla completa)");
            System.out.println("  [2] Locación de Servicios");
            System.out.print("Opción: ");
            String op = sc.nextLine().trim();
            if (op.equals("1")) { regimen = "728";      ok = true; }
            else if (op.equals("2")) { regimen = "LOCACION"; ok = true; }
            else { System.out.println("  [!] Opción inválida."); }
        } while (!ok);
    }

    // ─── Validación: Fondo de pensión ────────────────────────────
    public void setFondoPension(Scanner sc) {
        boolean ok = false;
        do {
            System.out.println("Fondo de pensión:");
            System.out.println("  [1] ONP      (13.0%)");
            System.out.println("  [2] AFP Integra (12.1%)");
            System.out.println("  [3] AFP Prima   (12.5%)");
            System.out.println("  [4] AFP Habitat (12.7%)");
            System.out.print("Opción: ");
            String op = sc.nextLine().trim();
            switch (op) {
                case "1" -> { fondoPension = "ONP";     ok = true; }
                case "2" -> { fondoPension = "INTEGRA"; ok = true; }
                case "3" -> { fondoPension = "PRIMA";   ok = true; }
                case "4" -> { fondoPension = "HABITAT"; ok = true; }
                default  -> System.out.println("  [!] Opción inválida.");
            }
        } while (!ok);
    }

    // ─── Lógica: tasa de pensión ─────────────────────────────────
    private double tasaPension() {
        return switch (fondoPension) {
            case "ONP"     -> 0.130;
            case "INTEGRA" -> 0.121;
            case "PRIMA"   -> 0.125;
            case "HABITAT" -> 0.127;
            default        -> 0.0;
        };
    }

    // ─── Lógica: bonificaciones con reglas de negocio ────────────
    private double bonificaciones() {
        double bono = 0;

        // Pacto colectivo solo para Régimen 728
        if (regimen.equals("728")) {
            bono += sueldoBase * 0.05; // 5% ejemplo pacto colectivo
        }

        // Asignación familiar solo si tiene hijos (cualquier régimen)
        if (tienehijos) {
            bono += 102.50; // valor referencial RMV-base
        }

        // Bono nocturno: solo si trabaja de noche Y es Régimen 728
        if (trabajaNoche && regimen.equals("728")) {
            bono += sueldoBase * 0.35; // 35% sobre sueldo base (mínimo legal)
        }

        return bono;
    }

    // ─── Cálculo sueldo neto ─────────────────────────────────────
    public double sueldoNeto() {
        double bruto = sueldoBase + bonificaciones();
        double descuento = bruto * tasaPension();
        return bruto - descuento;
    }

    // ─── Mostrar ─────────────────────────────────────────────────
    public void mostrar() {
        System.out.println("  Nombre    : " + nombre + " " + apP + " " + apM);
        System.out.println("  Documento : [" + tipoDocumento + "] " + documento);
        System.out.println("  Régimen   : " + regimen);
        System.out.println("  Pensión   : " + fondoPension
                + " (" + (int)(tasaPension() * 100 * 10) / 10.0 + "%)");
        System.out.printf ("  Sueldo base: S/ %.2f%n", sueldoBase);
        System.out.printf ("  Bonificac. : S/ %.2f%n", bonificaciones());
        System.out.printf ("  Sueldo neto: S/ %.2f%n", sueldoNeto());
        System.out.println("  Hijos: " + (tienehijos ? "Sí" : "No")
                + "  |  Turno nocturno: " + (trabajaNoche ? "Sí" : "No"));
    }
}
