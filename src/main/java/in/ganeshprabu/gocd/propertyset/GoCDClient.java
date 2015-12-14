package in.ganeshprabu.gocd.propertyset;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import in.ganeshprabu.gocd.propertyset.utils.ExecEnvironment;

import java.util.Map;

public class GoCDClient {
    private String hostname;
    private int port;
    private String username;
    private String password;
    private ExecEnvironment execEnvironment;
    private String goPipelineName;
    private String goPipelineCounter;
    private String goStageName;
    private String goStageCounter;
    private String goJobName;

    public GoCDClient(String hostname, int port, ExecEnvironment execEnvironment) {
        this.hostname = hostname;
        this.port = port;
        this.execEnvironment = execEnvironment;
        this.username = execEnvironment.get("GO_USER");
        this.password = execEnvironment.get("GO_PASSWORD");
        this.goPipelineName = execEnvironment.get("GO_PIPELINE_NAME");
        this.goPipelineCounter = execEnvironment.get("GO_PIPELINE_COUNTER");
        this.goStageName = execEnvironment.get("GO_STAGE_NAME");
        this.goStageCounter = execEnvironment.get("GO_STAGE_COUNTER");
        this.goJobName = execEnvironment.get("GO_JOB_NAME");
    }

    public String getHttpHostWithPort(String hostname, int port) {
        return String.format("http://%s:%d", hostname, port);
    }

    public String getPropertySetPath(String propertyName) {
        return String.format("%s/go/properties/%s/%s/%s/%s/%s/%s", getHttpHostWithPort(hostname, port), goPipelineName, goPipelineCounter, goStageName, goStageCounter, goJobName, propertyName);
    }

    public Boolean setProperty(String key, String value) {
        try {
            Unirest.post(getPropertySetPath(key)).field("value", value).basicAuth(username, password).asString();
        } catch (UnirestException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
