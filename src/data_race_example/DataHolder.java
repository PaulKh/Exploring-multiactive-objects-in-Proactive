package data_race_example;

import deadlock_example.FirstActiveObject;
import org.objectweb.proactive.Body;
import org.objectweb.proactive.RunActive;
import org.objectweb.proactive.annotation.multiactivity.MemberOf;
import org.objectweb.proactive.api.PAActiveObject;
import org.objectweb.proactive.core.mop.StubObject;
import org.objectweb.proactive.multiactivity.MultiActiveService;

import java.io.Serializable;

/**
 * Created by pkhvoros on 6/18/15.
 */
public class DataHolder implements RunActive,Serializable {
    private int value;
    @Override
    public void runActivity(Body body) {
        MultiActiveService service = new MultiActiveService(body);
        while (body.isActive()) {
            service.multiActiveServing();
        }
    }
    public void write(int value){
        this.value = value;
    }
    public int read(){return value;}
}
