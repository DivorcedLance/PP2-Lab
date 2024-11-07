package com.mycompany.app;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

public class Sumador extends UntypedAbstractActor {

    private static final int n = 500000; // número de términos de la serie de Leibniz
    private ActorRef maestro;

    private void crearMaestro() {
        maestro = getContext().actorOf(Props.create(Maestro.class));
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        crearMaestro();
        maestro.tell(100, getSelf()); // Número de esclavos
        maestro.tell(new Maestro.IniciarCalculo(n), getSelf()); // Iniciar cálculo con n términos
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof Double) {
            System.out.println("El valor aproximado de π es: " + (double) message);
            getContext().getSystem().terminate();
        }
    }
}