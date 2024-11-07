import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.PoisonPill;
import akka.actor.Actor;
import akka.actor.ActorLogging;

public class App {

    public static void main(String[] args) {
        int n = 1000000; // Número de términos a calcular
        int numWorkers = 4; // Número de trabajadores (esclavos)

        ActorSystem system = ActorSystem.create("LeibnizSystem");
        ActorRef master = system.actorOf(Props.create(Master.class, n, numWorkers), "master");
        
        // Iniciar el cálculo
        master.tell(new Master.Calculate(), ActorRef.noSender());

        system.getWhenTerminated();
    }

    // Mensajes
    static class Task {
        public final int start;
        public final int end;

        public Task(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    static class Result {
        public final double sum;

        public Result(double sum) {
            this.sum = sum;
        }
    }

    static class Calculate {}

    // Actor Esclavo
    static class Worker extends AbstractActor {
        
        @Override
        public Receive createReceive() {
            return receiveBuilder()
                .match(Task.class, task -> {
                    double localSum = 0.0;
                    for (int i = task.start; i < task.end; i++) {
                        localSum += Math.pow(-1, i) / (2 * i + 1); // Término de Leibniz
                    }
                    getSender().tell(new Result(localSum), getSelf());
                })
                .build();
        }
    }

    // Actor Maestro
    static class Master extends AbstractActor {
        private final int n;
        private final int numWorkers;
        private final int termsPerWorker;
        private double totalSum = 0.0;
        private int resultsReceived = 0;

        public Master(int n, int numWorkers) {
            this.n = n;
            this.numWorkers = numWorkers;
            this.termsPerWorker = n / numWorkers;
        }

        @Override
        public Receive createReceive() {
            return receiveBuilder()
                .match(Calculate.class, msg -> {
                    // Crear actores esclavos y asignar tareas
                    for (int i = 0; i < numWorkers; i++) {
                        int start = i * termsPerWorker;
                        int end = (i == numWorkers - 1) ? n : start + termsPerWorker;
                        ActorRef worker = getContext().actorOf(Props.create(Worker.class), "worker" + i);
                        worker.tell(new Task(start, end), getSelf());
                    }
                })
                .match(Result.class, result -> {
                    totalSum += result.sum;
                    resultsReceived++;

                    if (resultsReceived == numWorkers) {
                        double pi = totalSum * 4;
                        System.out.println("Valor aproximado de pi: " + pi);
                        getContext().getSystem().terminate();
                    }
                })
                .build();
        }
    }
}