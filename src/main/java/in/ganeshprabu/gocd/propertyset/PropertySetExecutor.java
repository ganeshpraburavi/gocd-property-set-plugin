package in.ganeshprabu.gocd.propertyset;

import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.response.execution.ExecutionResult;
import com.thoughtworks.go.plugin.api.task.TaskConfig;
import com.thoughtworks.go.plugin.api.task.TaskExecutionContext;
import com.thoughtworks.go.plugin.api.task.TaskExecutor;
import in.ganeshprabu.gocd.propertyset.utils.ExecEnvironment;
import in.ganeshprabu.gocd.propertyset.utils.Pair;
import org.json.JSONException;

import java.util.List;

import static in.ganeshprabu.gocd.propertyset.utils.Constants.*;

public class PropertySetExecutor implements TaskExecutor {

    private Logger log = Logger.getLoggerFor(PropertySetTask.class);

    @Override
    public ExecutionResult execute(TaskConfig config, TaskExecutionContext context) {
        final ExecEnvironment env = new ExecEnvironment();
        env.putAll(context.environment().asMap());
        if (env.isAbsent(GO_USER)) return envNotFound(GO_USER);
        if (env.isAbsent(GO_PASSWORD)) return envNotFound(GO_PASSWORD);

        GoCDClient goCDClient = new GoCDClient("localhost",80, env);

        try {
            List<Pair<String, String>> keyValuePairs = PropertySetTask.getPropertySet(config.getValue(PROPERTY_SET));
            for(Pair<String,String> keyValue: keyValuePairs){
                goCDClient.setProperty(keyValue._1(),keyValue._2());
            }
        } catch (JSONException e) {
            String message = "Failed while parsing configuration";
            log.error(message);
            return ExecutionResult.failure(message, e);
        }

        return ExecutionResult.success("Property (key,values) are set");
    }

    private ExecutionResult envNotFound(String environmentVariable) {
        String message = String.format("%s environment variable not present", environmentVariable);
        log.error(message);
        return ExecutionResult.failure(message);
    }
}
