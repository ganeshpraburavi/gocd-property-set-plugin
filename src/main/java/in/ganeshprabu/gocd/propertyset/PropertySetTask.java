package in.ganeshprabu.gocd.propertyset;

import com.thoughtworks.go.plugin.api.annotation.Extension;
import com.thoughtworks.go.plugin.api.response.validation.ValidationResult;
import com.thoughtworks.go.plugin.api.task.Task;
import com.thoughtworks.go.plugin.api.task.TaskConfig;
import com.thoughtworks.go.plugin.api.task.TaskExecutor;
import com.thoughtworks.go.plugin.api.task.TaskView;
import in.ganeshprabu.gocd.propertyset.utils.Pair;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static org.apache.commons.lang3.StringUtils.trim;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Extension
public class PropertySetTask implements Task {

    @Override
    public TaskConfig config() {
        TaskConfig taskConfig = new TaskConfig();
        taskConfig.addProperty("");
        return taskConfig;
    }

    @Override
    public TaskExecutor executor() {
        return new PropertySetExecutor();
    }

    @Override
    public TaskView view() {
        return new TaskView() {
            @Override
            public String displayValue() {
                return "Property Set Task";
            }

            @Override
            public String template() {
                try {
                    return IOUtils.toString(getClass().getResourceAsStream("/views/task.template.html"), "UTF-8");
                } catch (IOException e) {
                    e.printStackTrace();
                    return "Error happened during rendering - " + e.getMessage();
                }
            }
        };
    }

    public static List<Pair<String, String>> getPropertySet(String propertySets) throws JSONException {
        JSONArray keyValuePairs = new JSONArray(propertySets);
        List<Pair<String, String>> result = new ArrayList<Pair<String,String>>();
        for (int i = 0; i < keyValuePairs.length(); i++) {
            JSONObject keyValue = (JSONObject) keyValuePairs.get(i);
            String key = trim(keyValue.getString("key"));
            String value = trim(keyValue.getString("value"));
            result.add(new Pair<>(key, value));
        }

        return result;
    }

    @Override
    public ValidationResult validate(TaskConfig taskConfig) {
        return null;
    }
}