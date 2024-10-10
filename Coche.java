public class Coche {
    private String nombre;
    private double velocidadMaxima;
    private double aceleracion;
    private int manejo;
    private double combustible;
    private int durabilidad;
    private Piloto piloto;

    private double velocidadActual = 0;
    private double distanciaRecorrida = 0;
    private int vueltasCompletadas = 0;

    public Coche(String nombre, double velocidadMaxima, double aceleracion, int manejo, double combustible, int durabilidad, Piloto piloto) {
        this.nombre = nombre;
        this.velocidadMaxima = velocidadMaxima;
        this.aceleracion = aceleracion;
        this.manejo = manejo;
        this.combustible = combustible;
        this.durabilidad = durabilidad;
        this.piloto = piloto;
        this.velocidadActual = 0;
    }

    public void acelerar(String clima) {
        if (combustible > 0) {

            double aceleracionAjustada = aceleracion;
            if (clima.equals("lluvia")) {
                aceleracionAjustada *= 0.7;
            }


            velocidadActual += aceleracionAjustada;


            double velocidadMaximaAjustada = obtenerVelocidadMaximaAjustada(clima);
            if (velocidadActual > velocidadMaximaAjustada) {
                velocidadActual = velocidadMaximaAjustada;
            }

            consumirCombustible();
        } else {
            velocidadActual = 0;
        }
    }

    private double obtenerVelocidadMaximaAjustada(String clima) {
        if (clima.equals("lluvia")) {
            return velocidadMaxima * 0.92;
        }
        return velocidadMaxima;
    }



    public void tomarCurva(int dificultadCurva, String clima) {

        double reduccionVelocidad = (10 - manejo) * 0.1;
        velocidadActual = velocidadActual * (1 - reduccionVelocidad);

        System.out.println("\uD83D\uDFE1 \uD83D\uDFE1 \uD83D\uDFE1 \uD83D\uDFE1");
        System.out.printf("%s ha reducido su velocidad a %.2f km/h para tomar una curva.\n", nombre, velocidadActual);

        // Ajustar la probabilidad de daño en función del clima
        double probabilidadDañoBase = dificultadCurva / 20.0;

        // Aumentar el riesgo si el clima es "lluvia"
        if (clima.equals("lluvia")) {
            probabilidadDañoBase = 1.3; // Aumentar el riesgo en un 30%
        }

        // Simular daños por curva
        if (Math.random() < probabilidadDañoBase) {
            int daño = (int) (Math.random() * 5) + 1;
            recibirDaño(daño);
            System.out.println("\uD83D\uDFE0 \uD83D\uDFE0 \n" + nombre + " ha sufrido " + daño + " de daño en la curva. ");
        }

        // Después de tomar la curva, permitir que el coche vuelva a acelerar
        acelerar(clima); // Llamar a acelerar para recuperar velocidad
    }

    private void consumirCombustible() {
        combustible -= velocidadActual * 0.001;
        if (combustible < 0) {
            combustible = 0;
        }
    }

    public double getVelocidadActual() { return velocidadActual; }
    public double getCombustible() { return combustible; }
    public int getDurabilidad() { return durabilidad; }
    public double getDistanciaRecorrida() { return distanciaRecorrida; }
    public int getVueltasCompletadas() { return vueltasCompletadas; }
    public String getNombre() { return nombre; }
    public Piloto getPiloto() { return piloto; }


    public void actualizarDistancia(double distancia, double longitudPorVuelta) {
        distanciaRecorrida += distancia;


        while (distanciaRecorrida >= longitudPorVuelta) {
            completarVuelta();
            //distanciaRecorrida -= longitudPorVuelta;
            distanciaRecorrida = 0;
        }
    }

    public void completarVuelta() {
        vueltasCompletadas++;
        System.out.println(nombre + " ha completado una vuelta.");
    }

    public void recibirDaño(int daño) {
        durabilidad -= daño;
        if (durabilidad < 0) {
            durabilidad = 0;
        }
    }

    public boolean estaFueraDeCarrera() {
        return durabilidad <= 0;
    }
}