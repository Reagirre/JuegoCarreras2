import java.util.List;
import java.util.Random;
import java.util.Iterator;

public class Carrera {
    private List<Coche> coches;
    private Circuito circuito;
    private boolean carreraEnCurso;
    String clima;

    public Carrera(List<Coche> coches, Circuito circuito, String clima) {
        this.coches = coches;
        this.circuito = circuito;
        this.carreraEnCurso = true;
        this.clima = clima;
    }

    public void iniciarCarrera() {
        System.out.println("¡La carrera ha comenzado!");

        Random random = new Random();


        double longitudCurva = circuito.getLongitud() / circuito.getNumeroCurvas();


        while (carreraEnCurso) {
            for (Coche coche : coches) {
                if (!coche.estaFueraDeCarrera()) {
                    simularVuelta(coche, random, longitudCurva);
                }
            }

            actualizarPosiciones();
            verificarFinalCarrera();
            esperar(2000);
        }

        mostrarResultadosFinales();
    }

    private void simularVuelta(Coche coche, Random random, double longitudCurva) {
        if (coche.getCombustible() <= 0) {
            System.out.println(coche.getNombre() + " se ha quedado sin combustible.");
            esperar(5000);
            return;
        }


        double distanciaRecorridaEnEstePaso = coche.getVelocidadActual() / 3600;
        double longitudPorVuelta = circuito.getLongitud() / circuito.getNumeroVueltas();


        if (coche.getDistanciaRecorrida() >= longitudCurva) {

            coche.tomarCurva(circuito.getDificultad(), clima);


            double distanciaRecorridaEnCurva = coche.getVelocidadActual() / 3600;
            coche.actualizarDistancia(distanciaRecorridaEnCurva, longitudPorVuelta);

            System.out.printf("%s ha pasado por una curva a %.2f km/h y ha recorrido %.2f km en la curva.%n",
                    coche.getNombre(), coche.getVelocidadActual(), distanciaRecorridaEnCurva);
            esperar(5000);
        } else {

            coche.actualizarDistancia(distanciaRecorridaEnEstePaso, longitudPorVuelta);
        }


        coche.acelerar(clima);
    }

    private void actualizarPosiciones() {

        coches.sort((c1, c2) -> {
            if (c2.getVueltasCompletadas() != c1.getVueltasCompletadas()) {

                return Integer.compare(c2.getVueltasCompletadas(), c1.getVueltasCompletadas());
            } else {

                return Double.compare(c2.getDistanciaRecorrida(), c1.getDistanciaRecorrida());
            }
        });
        System.out.println("\n==================================" +
                "===============================================" +
                "================================================" +
                "=====\n");
        System.out.println("Posiciones actuales:");


        for (int i = 0; i < coches.size(); i++) {
            Coche coche = coches.get(i);
            if (i == 0)
                System.out.printf("\uD83E\uDD47");
            else if (i == 1)
                System.out.printf("\uD83E\uDD48");
            else if (i == 2)
                System.out.printf("\uD83E\uDD49");

            System.out.printf(" \uD83C\uDFCE\uFE0F %s - Velocidad: %.2f km/h, Combustible: %.2f L, Durabilidad: %d, Distancia Recorrida: %.2f km, Vueltas Completadas: %d%n",
                    coche.getNombre(), coche.getVelocidadActual(), coche.getCombustible(), coche.getDurabilidad(),
                    coche.getDistanciaRecorrida(), coche.getVueltasCompletadas());
        }
        System.out.println("\n==================================" +
                "===============================================" +
                "================================================" +
                "=====\n");
    }

    private void verificarFinalCarrera() {
        boolean todosCompleto = true;


        for (Coche coche : coches) {
            if (coche.getVueltasCompletadas() < circuito.getNumeroVueltas()) {
                todosCompleto = false;
                break;
            }
        }


        if (todosCompleto) {
            System.out.println("Todos los coches han completado la carrera.");
            carreraEnCurso = false;
        }


        Iterator<Coche> iterator = coches.iterator();
        while (iterator.hasNext()) {
            Coche coche = iterator.next();
            if (coche.getDistanciaRecorrida() >= circuito.getLongitud()) {
                System.out.println(coche.getNombre() + " ha completado la carrera.");
                iterator.remove();
            }
        }
    }

    private void mostrarResultadosFinales() {
        int filas = 4;
        int columnas = 69;

        System.out.println();

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if ((i + j) % 2 == 0) {
                    System.out.print("⬛");
                } else {
                    System.out.print("⬜");
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("\uD83C\uDFC6 \uD83C\uDFC6 \uD83C\uDFC6 ¡La carrera ha terminado! \uD83C\uDFC6 \uD83C\uDFC6 \uD83C\uDFC6");
        esperar(2000);
        /*actualizarPosiciones();*/
        System.out.println();System.out.println();
        System.out.println("Clasificación final:");
        coches.sort((c1, c2) -> Integer.compare(c2.getVueltasCompletadas(), c1.getVueltasCompletadas())); // Clasificación
        for (int i = 0; i < coches.size(); i++) {
            Coche coche = coches.get(i);
            if (i == 0)
                System.out.printf("\uD83D\uDC51");
            System.out.printf("%d. %s - Vueltas Completadas: %d%n", (i + 1), coche.getNombre(), coche.getVueltasCompletadas());
        }
    }

    private void esperar(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
