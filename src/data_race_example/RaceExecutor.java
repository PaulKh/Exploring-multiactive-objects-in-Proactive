package data_race_example;

import org.objectweb.proactive.Body;
import org.objectweb.proactive.RunActive;
import org.objectweb.proactive.annotation.multiactivity.*;
import org.objectweb.proactive.api.PAActiveObject;
import org.objectweb.proactive.core.mop.StubObject;
import org.objectweb.proactive.multiactivity.MultiActiveService;
import slave_master_example.utilities.SpeedGenerator;

import java.io.Serializable;

/**
 * Created by pkhvoros on 6/18/15.
 */
@DefineGroups({
        @Group(name = "start", selfCompatible = true)
})
@DefinePriorities({
        @PriorityHierarchy({
                @PrioritySet({"start"})
        })
})
@DefineThreadConfig(threadPoolSize = 5, hardLimit = true)
public class RaceExecutor implements RunActive,Serializable {
    @Override
    public void runActivity(Body body) {
        MultiActiveService service = new MultiActiveService(body);
        while (body.isActive()) {
            service.multiActiveServing();
        }
    }
    @MemberOf("start")
    public void start(DataHolder dataHolder, int value){
        int time = SpeedGenerator.generateRandomSpeed();
        try {
            Thread.sleep(time);
            ((DataHolder) dataHolder).write(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void someCall(){}
}
