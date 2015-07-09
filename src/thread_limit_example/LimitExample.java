package thread_limit_example;

import deadlock_example.FirstActiveObject;
import org.objectweb.proactive.Body;
import org.objectweb.proactive.RunActive;
import org.objectweb.proactive.annotation.multiactivity.*;
import org.objectweb.proactive.api.PAActiveObject;
import org.objectweb.proactive.core.mop.StubObject;
import org.objectweb.proactive.multiactivity.MultiActiveService;

import java.io.Serializable;

/**
 * Created by pkhvoros on 6/18/15.
 */
@DefineGroups({
        @Group(name = "run", selfCompatible = true)
})
@DefinePriorities({
        @PriorityHierarchy({
                @PrioritySet({"run"})
        })
})
@DefineThreadConfig(threadPoolSize = 10, hardLimit = false)
public class LimitExample implements RunActive,Serializable {
    @Override
    public void runActivity(Body body) {
        MultiActiveService service = new MultiActiveService(body);
        while (body.isActive()) {
            service.multiActiveServing();
        }
    }
    @MemberOf("run")
    public int run(int n){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  n != 0 ? ((LimitExample)PAActiveObject.getStubOnThis()).run(n - 1) : 0;
    }
}