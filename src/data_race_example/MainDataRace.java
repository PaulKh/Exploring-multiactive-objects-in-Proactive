package data_race_example;

import deadlock_example.FirstActiveObject;
import deadlock_example.SecondActiveObject;
import org.objectweb.proactive.ActiveObjectCreationException;
import org.objectweb.proactive.Body;
import org.objectweb.proactive.RunActive;
import org.objectweb.proactive.annotation.multiactivity.MemberOf;
import org.objectweb.proactive.api.PAActiveObject;
import org.objectweb.proactive.core.mop.StubObject;
import org.objectweb.proactive.core.node.NodeException;
import org.objectweb.proactive.gcmdeployment.GCMApplication;
import org.objectweb.proactive.gcmdeployment.GCMVirtualNode;
import org.objectweb.proactive.multiactivity.MultiActiveService;

import java.io.Serializable;

/**
 * Created by pkhvoros on 6/18/15.
 */
public class MainDataRace implements RunActive,Serializable {
    @Override
    public void runActivity(Body body) {
        MultiActiveService service = new MultiActiveService(body);
        while (body.isActive()) {
            service.multiActiveServing();
        }
    }
    @MemberOf("start")
    public int run(GCMApplication gcmApplication){
        GCMVirtualNode vnRaceExecutor = gcmApplication.getVirtualNode("RaceExecutor");
        GCMVirtualNode vnDataHolder = gcmApplication.getVirtualNode("DataHolder");
//        System.out.println("run start" + vn);
        try {
            DataHolder dataHolder = PAActiveObject.newActive(DataHolder.class, null, vnDataHolder.getANode());
            for (int i = 0; i < 5; i++){
                RaceExecutor raceExecutor = PAActiveObject.newActive(RaceExecutor.class, null, vnRaceExecutor.getANode());
                raceExecutor.start(dataHolder, i);
            }
            return dataHolder.read();
        } catch (ActiveObjectCreationException e) {
            e.printStackTrace();
        } catch (NodeException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
