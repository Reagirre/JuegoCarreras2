import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        // Configuración de coches
        ArrayList<Coche> coches = new ArrayList<>();

        System.out.println("=================================");
        System.out.println("  CONFIGURACIÓN DE COCHES");
        System.out.println("=================================");

        for (int i = 0; i <= 1; i++) {  // Por ahora, configuramos 2 coches
            System.out.println("\nConfigurando el Coche #" + (i + 1));
            System.out.println("----------------------------");

            System.out.print("Nombre del coche: ");
            String nombre = scanner.nextLine();

            System.out.print("Nombre del piloto: ");
            String nombrePiloto = scanner.nextLine();
            Piloto piloto = new Piloto(nombrePiloto);

            double velocidadMaxima = solicitarDouble(scanner, "Velocidad máxima (km/h) [valor positivo]: ", 0, Double.MAX_VALUE);
            double aceleracion = solicitarDouble(scanner, "Aceleración (km/h por segundo) [valor positivo]: ", 0, Double.MAX_VALUE);
            int manejo = solicitarInt(scanner, "Manejo (1-10): ", 1, 10);
            double combustible = solicitarDouble(scanner, "Combustible (litros) [valor positivo]: ", 0, Double.MAX_VALUE);
            int durabilidad = solicitarInt(scanner, "Durabilidad (1-100): ", 1, 100);
            scanner.nextLine();
            coches.add(new Coche(nombre, velocidadMaxima, aceleracion, manejo, combustible, durabilidad, piloto));
            System.out.println("\n[Coche #" + (i + 1) + " configurado con éxito]");
        }

        System.out.println("\n=================================");
        System.out.println("  CONFIGURACIÓN DEL CIRCUITO");
        System.out.println("=================================");

        double longitud = solicitarDouble(scanner, "Longitud del circuito (km) [valor positivo]: ", 0, Double.MAX_VALUE);
        int vueltas = solicitarInt(scanner, "Número de vueltas [valor positivo]: ", 1, Integer.MAX_VALUE);
        int curvas = solicitarInt(scanner, "Número de curvas [valor positivo]: ", 1, Integer.MAX_VALUE);

        scanner.nextLine();  // Consumir el salto de línea

        String clima = solicitarClima(scanner);
        int dificultad = solicitarInt(scanner, "Dificultad del circuito (1-10): ", 1, 10);

        System.out.println("\n=================================");
        System.out.println("  CONFIGURACIÓN COMPLETADA");
        System.out.println("=================================");
        System.out.println("¡Todo está listo para iniciar la carrera!");

        Circuito circuito = new Circuito(longitud, vueltas, curvas, dificultad);

        // Iniciar la carrera
        Carrera carrera = new Carrera(coches, circuito, clima);
        carrera.iniciarCarrera();
    }
    private static int solicitarInt(Scanner scanner, String mensaje, int min, int max) {
        int valor = 0;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                System.out.print(mensaje);
                valor = scanner.nextInt();
                if (valor >= min && valor <= max) {
                    entradaValida = true;
                } else {
                    System.out.println("Error: el valor debe estar entre " + min + " y " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un número entero válido.");
                scanner.next();  // Limpiar el valor no válido del buffer
            }
        }
        return valor;
    }

    private static double solicitarDouble(Scanner scanner, String mensaje, double min, double max) {
        double valor = 0;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                System.out.print(mensaje);
                valor = scanner.nextDouble();
                if (valor > min && valor <= max) {
                    entradaValida = true;
                } else {
                    System.out.println("Error: el valor debe ser mayor que " + min + " y hasta " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un número válido.");
                scanner.next();  // Limpiar el valor no válido del buffer
            }
        }
        return valor;
    }

    private static String solicitarClima(Scanner scanner) {
        String clima;
        do {
            System.out.print("Condiciones climáticas (sol, lluvia): ");
            clima = scanner.nextLine().toLowerCase();
            if (!clima.equals("sol") && !clima.equals("lluvia")) {
                System.out.println("Error: Condición climática no válida. Por favor, elige entre 'sol' o 'lluvia'.");
            }
        } while (!clima.equals("sol") && !clima.equals("lluvia"));
        return clima;
    }
}