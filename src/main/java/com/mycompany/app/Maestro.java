package com.mycompany.app;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

public class Maestro extends UntypedAbstractActor  {

        public static class Mensaje {
            public double arreglo[];

            public Mensaje(double arreglo[]) {
                this.arreglo = arreglo;
            }
        }

        private ActorRef sumador;
        private ActorRef esclavos[];
        private Long total = 0L;
        private int respuesta = 0;

        @Override
        public void onReceive(Object message) {
            if (message instanceof Integer) {
                sumador = getSender();
                crearEsclavos((int)message);
            }
            if (message instanceof Long) {
                respuesta += 1;
                total += (Long)message;
                if (respuesta == esclavos.length) {
                    sumador.tell(total, getSelf());
                }
            }
            if (message instanceof Mensaje) {
                Mensaje m = (Mensaje) message;
                mensajeEsclavos(m);
            }
        }


        public void crearEsclavos(int cantidad) {
            esclavos = new ActorRef[cantidad];
            for (int i=0; i<cantidad; i++) {
                esclavos[i] = getContext().actorOf(Props.create(Esclavo.class));
            }
        }

        public void mensajeEsclavos(Mensaje m) {
            // repartir el array entre los esclavos
            int cantidad = m.arreglo.length / esclavos.length;
            int inicio = 0;
            int fin = cantidad;
            for (int i=0; i<esclavos.length; i++) {
                if (i == esclavos.length - 1) {
                    fin = m.arreglo.length;
                }
                double subarreglo[] = new double[fin - inicio];
                for (int j=inicio; j<fin; j++) {
                    subarreglo[j-inicio] = m.arreglo[j];
                }
                esclavos[i].tell(subarreglo, getSelf());
                inicio = fin;
                fin += cantidad;
            }
        }

}