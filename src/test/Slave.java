package test;

import org.objectweb.proactive.Body;
import org.objectweb.proactive.RunActive;
import org.objectweb.proactive.annotation.multiactivity.*;
import org.objectweb.proactive.core.util.wrapper.BooleanWrapper;
import org.objectweb.proactive.core.util.wrapper.IntWrapper;
import org.objectweb.proactive.extensions.masterworker.interfaces.Task;
import org.objectweb.proactive.extensions.masterworker.interfaces.WorkerMemory;
import org.objectweb.proactive.multiactivity.MultiActiveService;
import utilities.SpeedGenerator;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by pkhvoros on 3/4/15.
 */
@DefineGroups({
     @Group(name = "perform_computation", selfCompatible = false),
     @Group(name = "assign_work", selfCompatible = false),
     @Group(name = "help_slave", selfCompatible = false),
     @Group(name = "getters", selfCompatible = true)
})
@DefinePriorities({
        @PriorityHierarchy({
                @PrioritySet({"getters"}),
                @PrioritySet({"perform_computation"}),
                @PrioritySet({"help_slave"}),
                @PrioritySet({"assign_work"})
        })
})
@DefineRules({
        @Compatible({"perform_computation", "getters"}),
        @Compatible({"assign_work", "getters"}),
        @Compatible({"help_slave", "getters"})
})
@DefineThreadConfig(threadPoolSize = 2, hardLimit = true)


public class Slave implements RunActive,Serializable{
    private int speed;
    private int currentWork = 0;
    @Override
    public void runActivity(Body body) {
        MultiActiveService service = new MultiActiveService(body);
        while (body.isActive()) {
            service.multiActiveServing();
        }
    }

    @MemberOf("perform_computation")
    public BooleanWrapper compute(IntWrapper amountOfWork){
        currentWork = amountOfWork.getIntValue();
        speed = SpeedGenerator.generateRandomSpeed();
        try {
            while(currentWork > 0) {
                currentWork-=speed;
                Thread.sleep(10);
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new BooleanWrapper(true);
    }
    @MemberOf("assign_work")
    public IntWrapper assignDelegatedWork(){
        if (currentWork > 0){
            if (currentWork % 2 == 0) {
                currentWork /= 2;
                return new IntWrapper(currentWork / 2);
            }
            else{
                currentWork /= 2 ;
                return new IntWrapper((currentWork / 2) + 1);
            }
        }
        else return new IntWrapper(0);
    }
    @MemberOf("help_slave")
    public void helpAnotherSlave(Slave slave){
        compute(slave.assignDelegatedWork());
    }
    @MemberOf("getters")
    public BooleanWrapper needsHelp(){
        return new BooleanWrapper(currentWork > 2 * speed);
    }
    @MemberOf("getters")
    public IntWrapper amountOfWorkLeft(){
        return new IntWrapper(currentWork);
    }

}
