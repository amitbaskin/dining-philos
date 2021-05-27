package dining_philos;

/**
 * The running class
 */
public abstract class Main {

    /**
     * The running method
     * @param args The arguments for this program (ignored)
     */
    public static void main(String[] args){
        new DiningPhilosophers().run();
    }
}
