import cetralized_case.Master;
import cetralized_case.Slave;
import org.objectweb.proactive.ActiveObjectCreationException;
import org.objectweb.proactive.api.PAActiveObject;
import org.objectweb.proactive.core.ProActiveException;
import org.objectweb.proactive.core.node.Node;
import org.objectweb.proactive.core.node.NodeException;
import org.objectweb.proactive.extensions.gcmdeployment.PAGCMDeployment;
import org.objectweb.proactive.gcmdeployment.GCMApplication;
import org.objectweb.proactive.gcmdeployment.GCMVirtualNode;

import java.util.Map;

/**
 * Created by pkhvoros on 3/4/15.
 */

class Main{
    private static GCMApplication gcmApplication = null;
    public static void main(String[] args){
        initTechnicalService();
        performComputation();
    }
    private static void initTechnicalService(){
        try {
            gcmApplication = PAGCMDeployment.loadApplicationDescriptor(
                    Master.class.getResource("/GCMA.xml"));
        }
        catch (ProActiveException e1) {
            e1.printStackTrace();
        }
        gcmApplication.startDeployment();
        gcmApplication.waitReady();
//        Map<String, GCMVirtualNode> vn = gcma.getVirtualNodes();
//        Node node = vn.getANode();
    }
    private static void performComputation(){
        Master master = null;
        try {
            GCMVirtualNode vn = gcmApplication.getVirtualNode("Master");
            master = PAActiveObject.newActive(Master.class, null, vn.getANode());
            master.prepareAction(gcmApplication);
            master.startAction();
//            master.collectStatistics();
        } catch (ActiveObjectCreationException e) {
            e.printStackTrace();
        } catch (NodeException e) {
            e.printStackTrace();
        }
    }

}