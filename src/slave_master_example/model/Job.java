package slave_master_example.model;

import java.io.Serializable;

/**
 * Created by pkhvoros on 3/9/15.
 */
public class Job implements Serializable{
    private int amountOfWork;
    private boolean hasStarted = false;

    public Job(int amountOfWork) {
        this.amountOfWork = amountOfWork;
    }

    public int getAmountOfWork() {
        return amountOfWork;
    }

    public boolean hasStarted() {
        return hasStarted;
    }

    public void setHasStarted(boolean hasStarted) {
        this.hasStarted = hasStarted;
    }

    public void setAmountOfWork(int amountOfWork) {
        this.amountOfWork = amountOfWork;
    }
}
