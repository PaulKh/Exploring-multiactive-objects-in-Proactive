package test;

import org.apache.log4j.ConsoleAppender;
import org.objectweb.proactive.ActiveObjectCreationException;
import org.objectweb.proactive.api.PAActiveObject;
import org.objectweb.proactive.core.node.NodeException;
import org.objectweb.proactive.core.util.wrapper.IntWrapper;
import org.objectweb.proactive.extensions.masterworker.ProActiveMaster;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pkhvoros on 3/4/15.
 */
public class Master {
    private boolean isCollectingStatistics = false;
    private final int numberOfSlaves = 5;
    private List<Slave> slaves = new ArrayList<Slave>();
    public void startAction() {
        try {
            for (int i = 0; i < numberOfSlaves; i++) {
                Slave slave = PAActiveObject.newActive(Slave.class, null);
                slaves.add(slave);
                makeComputation(slave);
            }
        } catch (ActiveObjectCreationException | NodeException e) {
            e.printStackTrace();
        }
        for (Slave slave:slaves)
            PAActiveObject.terminateActiveObject(slave, false);
    }
    private void makeComputation(Slave slave){
        boolean b = slave.compute(new IntWrapper(1000)).getBooleanValue();
        if (!isCollectingStatistics) {
            collectStatistics();
            isCollectingStatistics = true;
        }
        if (b) {
            for (Slave slave1 : slaves) {
                if (slave1.needsHelp().getBooleanValue()) {
                    slave.helpAnotherSlave(slave1);
                }
            }
        }
    }
    private void collectStatistics(){
        boolean isStillWorking = true;
        while(isStillWorking){
            isStillWorking = false;
            try {
                Thread.sleep(5);
                System.out.println("Amount of work left = ");
                for (Slave slave:slaves){
                    int workLeft = slave.amountOfWorkLeft().getIntValue();
                    System.out.print(workLeft + " ");
                    if (workLeft > 0)
                        isStillWorking = true;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
