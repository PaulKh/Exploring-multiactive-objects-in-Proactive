package shared_field;

import org.objectweb.proactive.annotation.multiactivity.*;

/**
 * Created by pkhvoros on 6/12/15.
 */
@DefineGroups({
        @Group(name = "first_run", selfCompatible = true)
})
@DefinePriorities({
        @PriorityHierarchy({
                @PrioritySet({"first_run"})
        })
})
@DefineThreadConfig(threadPoolSize = 5, hardLimit = true)
public class AOMain {
}
