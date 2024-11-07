package com.mycompany.app;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

public class Sumador extends UntypedAbstractActor {
    
    private static final int n = 100000;
    private double arreglo[];
    private ActorRef maestro;
    
    private void crearMaestro() {
        maestro = getContext().actorOf(Props.create(Maestro.class));
    }
    
    private void instanciarArreglo() {
        arreglo = new double[n];
        for (int i = 0; i < n; i++) {
            arreglo[i] = Math.random();
        }
    }

    @Override
    public void postStop() throws Exception {
        super.postStop(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void preStart() throws Exception {
        super.preStart(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        instanciarArreglo();
        crearMaestro();
        maestro.tell(10, getSelf());
        maestro.tell(new Maestro.Mensaje(arreglo), getSelf());
    }
    
    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Long) {
            System.out.println("El total es: " + (long)message);
            getContext().getSystem().terminate();
        }
    }
    
}