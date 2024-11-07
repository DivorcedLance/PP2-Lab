package com.mycompany.app;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

public class Maestro extends UntypedAbstractActor {

    private ActorRef sumador;
    private ActorRef[] esclavos;
    private double total = 0;
    private int respuesta = 0;
    private int n;

    public static class IniciarCalculo {
        public int n;

        public IniciarCalculo(int n) {
            this.n = n;
        }
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof Integer) {
            sumador = getSender();
            crearEsclavos((int) message);
        }
        if (message instanceof Double) {
            respuesta += 1;
            total += (Double) message;
            if (respuesta == esclavos.length) {
                sumador.tell(total * 4, getSelf()); // Multiplicamos por 4 para obtener π
            }
        }
        if (message instanceof IniciarCalculo) {
            IniciarCalculo iniciarCalculo = (IniciarCalculo) message;
            n = iniciarCalculo.n;
            repartirTrabajo();
        }
    }

    public void crearEsclavos(int cantidad) {
        esclavos = new ActorRef[cantidad];
        for (int i = 0; i < cantidad; i++) {
            esclavos[i] = getContext().actorOf(Props.create(Esclavo.class));
        }
    }

    public void repartirTrabajo() {
        int cantidadPorEsclavo = n / esclavos.length;
        int inicio = 0;
        int fin = cantidadPorEsclavo;

        for (int i = 0; i < esclavos.length; i++) {
            if (i == esclavos.length - 1) {
                fin = n; // Asegurar que el último esclavo cubra hasta el final
            }
            esclavos[i].tell(new Esclavo.CalcularTerminos(inicio, fin), getSelf());
            inicio = fin;
            fin += cantidadPorEsclavo;
        }
    }
}