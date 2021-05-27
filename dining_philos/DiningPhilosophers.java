package dining_philos;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The dining philosophers illustration
 */
public class DiningPhilosophers extends JFrame {
    private static final int WIDTH = 1500;
    private static final int HEIGHT = 1500;
    private static final int PHIL_AMOUNT = 5;
    private static final int EAT_DURATION = 5000;
    private static final SecureRandom generator = new SecureRandom();
    private final JLabel[] labels;
    private final Philosopher[] philosophers;
    private final JPanel[] panels;

    /**
     * Create the illustration
     */
    public DiningPhilosophers(){
        labels = initializeLabels();
        Stick[] sticks = initializeSticks();
        philosophers = initializePhilosophers(sticks, labels);
        panels = initializePanels();
    }

    /**
     * Get a random integer
     * @return Random int
     */
    public static int getRandInt() {
        return generator.nextInt(EAT_DURATION);
    }

    /**
     * Get stick
     * @param sticks All sticks
     * @param index The index of the stick to get
     * @return The requested stick
     */
    private static Stick getStick(Stick[] sticks, int index){
        return sticks[index % PHIL_AMOUNT];
    }

    /**
     * Initialize the panels of the illustration
     * @return An array of the panels which give the illustration
     */
    private static JPanel[] initializePanels(){
        JPanel[] panels = new JPanel[PHIL_AMOUNT];
        for (int i=0; i<PHIL_AMOUNT; i++){
            panels[i] = new JPanel();
        } return panels;
    }

    /**
     * Initialize the philosophers
     * @param sticks All sticks
     * @param labels All labels (images of the philosophers)
     * @return An array containing the philosophers
     */
    private static Philosopher[] initializePhilosophers(Stick[] sticks, JLabel[] labels){
        Philosopher[] philosophers = new Philosopher[PHIL_AMOUNT];
        for (int i=0; i<PHIL_AMOUNT; i++){
            philosophers[i] = new Philosopher(getStick(sticks, i), getStick(sticks, i+1), labels[i], i);
        } return philosophers;
    }

    /**
     * Initialize the sticks
     * @return All sticks
     */
    private static Stick[] initializeSticks(){
        Stick[] sticks = new Stick[PHIL_AMOUNT];
        for (int i=0; i<PHIL_AMOUNT; i++){
            sticks[i] = new Stick(i);
        } return sticks;
    }

    /**
     * Initialize the labels
     * @return All labels (images of the philosophers)
     */
    private static JLabel[] initializeLabels(){
        JLabel[] labels = new JLabel[PHIL_AMOUNT];
        for (int i=0; i<PHIL_AMOUNT; i++){
            labels[i] =
                    new JLabel(new ImageIcon(Paths.get(System.getProperty("user.dir"),
                            Philosopher.THINKING_PIC).toString()));
        } return labels;
    }

    /**
     * Add the labels to the panels and the panels to the frame
     */
    private void addComponents(){
        for (int i=0; i<PHIL_AMOUNT; i++){
            panels[i].add(labels[i], BorderLayout.CENTER);
            add(panels[i]);
        }
    }

    /**
     * Run the illustration
     */
    public void run(){
        setLayout(new GridBagLayout());
        addComponents();
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println("\n\nThreads Log:\n\n");
        for (Philosopher phil : philosophers){
            executorService.execute(phil);
        }
    }
}