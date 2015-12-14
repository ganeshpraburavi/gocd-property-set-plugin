package in.ganeshprabu.gocd.propertyset.utils;

import java.util.HashMap;
import java.util.Map;

public class ExecEnvironment {

    HashMap<String,String> environmentVariables = new HashMap<String,String>();

    public ExecEnvironment() {
        environmentVariables.putAll(System.getenv());
    }

    public ExecEnvironment putAll(Map<String, String> extraEnv){
        environmentVariables.putAll(extraEnv);
        return this;
    }

    public String get(String name) {
        return environmentVariables.get(name);
    }

    public boolean has(String name) {
        return environmentVariables.containsKey(name) && !get(name).equals("");
    }

    public boolean isAbsent(String name) {
        return !has(name);
    }

    public String triggeredUser() {
        return get("GO_TRIGGER_USER");
    }
}
