import cetralized_case.Master;
import org.objectweb.proactive.ActiveObjectCreationException;
import org.objectweb.proactive.api.PAActiveObject;
import org.objectweb.proactive.core.node.NodeException;

/**
 * Created by pkhvoros on 3/4/15.
 */

class Main{
    public static void main(String[] args){
        performComputation();
    }
    private static void performComputation(){
        Master master = null;
        try {
            master = PAActiveObject.newActive(Master.class, null);
            master.prepareAction();
            master.startAction();
//            master.collectStatistics();
        } catch (ActiveObjectCreationException e) {
            e.printStackTrace();
        } catch (NodeException e) {
            e.printStackTrace();
        }
    }

}