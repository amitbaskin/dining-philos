package dining_philos;

import javax.swing.*;
import java.nio.file.Paths;

/**
 * A philosopher
 */
public class Philosopher implements Runnable {
    private final Stick[] sticks;
    private final JLabel label;
    private final int serialNum;
    private static final String EATING_PIC = "phil-eats.png";
    static final String THINKING_PIC = "phil-thinks.png";
    private static final String HUNGRY_PIC = "phil-hungry.png";

    /**
     * Create a new philosopher
     * @param stick1 First stick
     * @param stick2 Second stick
     * @param label The image initially representing this philosopher
     * @param serialNum The serial number of this philosopher (used for printing the log)
     */
    public Philosopher(Stick stick1, Stick stick2, JLabel label, int serialNum) {
        this.serialNum = serialNum;
        sticks = initializeSticks(stick1, stick2);
        this.label = label;
    }

    /**
     * Execute the sticks-pickup
     * @throws InterruptedException In case a thread running philosopher gets interrupted while
     * sleeping/waiting
     */
    public void grabSticks() throws InterruptedException {
        label.setIcon(new ImageIcon(Paths.get(System.getProperty("user.dir"),
                HUNGRY_PIC).toString()));

        //#####################################################################################
        System.out.printf("Phil num %d tries to grab stick num %d\n", this.serialNum, sticks[0].getIndex());
        //#####################################################################################

        sticks[0].grab();

        //#####################################################################################
        System.out.printf("Phil num %d grabs stick num %d\n", this.serialNum, sticks[0].getIndex());
        System.out.printf("Phil num %d tries to grab stick num %d\n", this.serialNum, sticks[1].getIndex());
        //#####################################################################################

        sticks[1].grab();

        //#####################################################################################
        System.out.printf("Phil num %d grabs stick num %d\n", this.serialNum, sticks[1].getIndex());
        //#####################################################################################
    }

    /**
     * Execute sticks-put-down
     */
    public void putSticksDown() {

        sticks[0].putDown();

        //#####################################################################################
        System.out.printf("Phil num %d puts down stick num %d\n", this.serialNum, sticks[0].getIndex());
        //#####################################################################################

        sticks[1].putDown();

        //#####################################################################################
        System.out.printf("Phil num %d puts down stick num %d\n", this.serialNum, sticks[1].getIndex());
        //#####################################################################################
    }

    /**
     * Execute eating
     * @param label The image to represent the philosopher when he eats
     * @throws InterruptedException In case a thread running philosopher gets interrupted while
     * sleeping/waiting
     */
    public void eat(JLabel label) throws InterruptedException {
        label.setIcon(new ImageIcon(Paths.get(System.getProperty("user.dir"), EATING_PIC).toString()));

        //#####################################################################################
        System.out.printf("Phil num %d is eating\n", this.serialNum);
        //#####################################################################################

        Thread.sleep(DiningPhilosophers.getRandInt());

        //#####################################################################################
        System.out.printf("Phil num %d stopped eating\n", this.serialNum);
        //#####################################################################################
    }

    /**
     * Execute thinking
     * @throws InterruptedException In case a thread running philosopher gets interrupted while
     * sleeping/waiting
     */
    public void think() throws InterruptedException {

        label.setIcon(new ImageIcon(Paths.get(System.getProperty("user.dir"), THINKING_PIC).toString()));

        //#####################################################################################
        System.out.printf("Phil num %d is thinking\n", this.serialNum);
        //#####################################################################################

        Thread.sleep(DiningPhilosophers.getRandInt());

        //#####################################################################################
        System.out.printf("Phil num %d stopped thinking\n", this.serialNum);
        //#####################################################################################
    }

    private static Stick[] initializeSticks(Stick stick1, Stick stick2) {
        return stick1.getIndex() < stick2.getIndex() ?
                new Stick[]{stick1, stick2} : new Stick[]{stick2, stick1};
    }

    /**
     * Run this philosopher (forever)
     */
    @Override
    public void run() {
        try {
            while (true){
                grabSticks();
                eat(label);
                putSticksDown();
                think();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}