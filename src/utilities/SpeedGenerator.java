package utilities;

import java.util.Random;

/**
 * Created by pkhvoros on 3/4/15.
 */
public class SpeedGenerator {
    private static final int minSpeed = 100;
    private static final int maxSpeed = 400;
    public static int generateRandomSpeed() {
        Random rand = new Random();
        int randomNum = rand.nextInt((maxSpeed - minSpeed) + 1) + minSpeed;
        return randomNum;
    }
}
