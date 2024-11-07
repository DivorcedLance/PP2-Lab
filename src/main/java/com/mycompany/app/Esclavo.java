package com.mycompany.app;

import akka.actor.UntypedAbstractActor;

public class Esclavo extends UntypedAbstractActor {

    public static class CalcularTerminos {
        public int inicio;
        public int fin;

        public CalcularTerminos(int inicio, int fin) {
            this.inicio = inicio;
            this.fin = fin;
        }
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof CalcularTerminos) {
            CalcularTerminos rango = (CalcularTerminos) message;
            double suma = 0;
            for (int i = rango.inicio; i < rango.fin; i++) {
                // System.out.println("Calculando término " + i);
                suma += Math.pow(-1, i) / (2 * i + 1); // Cálculo del término de la serie de Leibniz
            }
            getSender().tell(suma, getSelf());
        }
    }

    @Override
    public void preStart() throws Exception {
        // TODO Auto-generated method stub
        super.preStart();
        System.out.println("Esclavo creado");
    }
}