package utilities;

import java.util.Random;

/**
 * Created by pkhvoros on 3/4/15.
 */
public class SpeedGenerator {
    private static final int minSpeed = 10;
    private static final int maxSpeed = 100;
    public static int generateRandomSpeed() {
        Random rand = new Random();
        int randomNum = rand.nextInt((maxSpeed - minSpeed) + 1) + minSpeed;
        return randomNum;
    }
}
