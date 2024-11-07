package com.mycompany.app;

import akka.actor.UntypedAbstractActor;

public class Esclavo extends UntypedAbstractActor {
    
    @Override
    public void onReceive(Object message) throws InterruptedException {
        // SI recibe un double subarreglo[] entonces sumar los elementos del subarreglo y enviar el resultado al maestro

        if (message instanceof double[]) {
            double[] subarreglo = (double[]) message;
            double suma = 0;
            for (int i = 0; i < subarreglo.length; i++) {
                suma += subarreglo[i];
            }
            getSender().tell((long) suma, getSelf());            
        }
    }
}