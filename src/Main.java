import data_race_example.MainDataRace;
import deadlock_example.FirstActiveObject;
import slave_master_example.cetralized_case.Master;
import org.objectweb.proactive.ActiveObjectCreationException;
import org.objectweb.proactive.api.PAActiveObject;
import org.objectweb.proactive.core.ProActiveException;
import org.objectweb.proactive.core.node.NodeException;
import org.objectweb.proactive.extensions.gcmdeployment.PAGCMDeployment;
import org.objectweb.proactive.gcmdeployment.GCMApplication;
import org.objectweb.proactive.gcmdeployment.GCMVirtualNode;
import thread_limit_example.LimitExample;

/**
 * Created by pkhvoros on 3/4/15.
 */

class Main{
    private static GCMApplication gcmApplication = null;
    public static void main(String[] args){
        runConcurrentReadWriteExample();
    }
    private static void runLimitExample(){
        initTechnicalService(LimitExample.class, "/GCMALimitExample.xml");
        limitExample();
    }
    private static void runMasterSlaveExample(){
        initTechnicalService(Master.class, "/GCMASlave.xml");
        masterSlaveExample();
    }
    private static void runDeadlockExample(){
        initTechnicalService(FirstActiveObject.class, "/GCMADeadlock.xml");
        deadlockExample();
    }
    private static void runConcurrentReadWriteExample(){
        initTechnicalService(MainDataRace.class, "/GCMAConcurrency.xml");
        concurrencyExample();
    }
    private static void initTechnicalService(Class className, String resourceURL){
        try {
            gcmApplication = PAGCMDeployment.loadApplicationDescriptor(
                    className.getResource(resourceURL));
        }
        catch (ProActiveException e1) {
            e1.printStackTrace();
        }
        gcmApplication.startDeployment();
        gcmApplication.waitReady();
    }
    private static void masterSlaveExample(){
        Master master = null;
        try {
            GCMVirtualNode vn = gcmApplication.getVirtualNode("Master");
            master = PAActiveObject.newActive(Master.class, null, vn.getANode());
            master.prepareAction(gcmApplication);
            master.startAction();
            master.collectStatistics();
        } catch (ActiveObjectCreationException e) {
            e.printStackTrace();
        } catch (NodeException e) {
            e.printStackTrace();
        }
    }
    private static void deadlockExample(){
        FirstActiveObject firstExample;
        try {
            GCMVirtualNode vn = gcmApplication.getVirtualNode("FirstActiveObject");
            firstExample = PAActiveObject.newActive(FirstActiveObject.class, null, vn.getANode());
            firstExample.start(gcmApplication);
        } catch (ActiveObjectCreationException e) {
            e.printStackTrace();
        } catch (NodeException e) {
            e.printStackTrace();
        }
    }
    private static void limitExample(){
        LimitExample firstExample;
        try {
            GCMVirtualNode vn = gcmApplication.getVirtualNode("LimitExample");
            firstExample = PAActiveObject.newActive(LimitExample.class, null, vn.getANode());
            firstExample.run(20);
        } catch (ActiveObjectCreationException e) {
            e.printStackTrace();
        } catch (NodeException e) {
            e.printStackTrace();
        }
    }
    private static void concurrencyExample(){
        MainDataRace firstExample;
        try {
            GCMVirtualNode vn = gcmApplication.getVirtualNode("MainDataRace");
            firstExample = PAActiveObject.newActive(MainDataRace.class, null, vn.getANode());
            System.out.print("here = " +firstExample.run(gcmApplication));
        } catch (ActiveObjectCreationException e) {
            e.printStackTrace();
        } catch (NodeException e) {
            e.printStackTrace();
        }
    }
}