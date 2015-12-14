package in.ganeshprabu.gocd.propertyset;

import in.ganeshprabu.gocd.propertyset.utils.ExecEnvironment;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


import java.util.HashMap;

public class GoCDClientTest {

    private CustomMap<String, String> mockEnv = new CustomMap<String, String>();
    private GoCDClient goCDClient;

    private final String GO_USER = "username";
    private final String GO_PASSWORD = "password";
    private final String GO_PIPELINE_NAME = "pipeline";
    private final String GO_STAGE_NAME = "stage";
    private final String GO_JOB_NAME = "job";
    private final String GO_PIPELINE_COUNTER = "pipelineCounter";
    private final String GO_STAGE_COUNTER = "stageCounter";

    private class CustomMap<K, V> extends HashMap<K, V> {
        public CustomMap<K, V> with(K key, V value) {
            super.put(key, value);
            return this;
        }
    }

    @Before
    public void setUp() throws Exception {
        mockEnv = mockEnv.with("GO_USER", GO_USER)
                .with("GO_PASS", GO_PASSWORD)
                .with("GO_PIPELINE_NAME", GO_PIPELINE_NAME)
                .with("GO_STAGE_NAME", GO_STAGE_NAME)
                .with("GO_JOB_NAME", GO_JOB_NAME)
                .with("GO_PIPELINE_COUNTER", GO_PIPELINE_COUNTER)
                .with("GO_STAGE_COUNTER", GO_STAGE_COUNTER);
        goCDClient = new GoCDClient("localhost", 80, new ExecEnvironment().putAll(mockEnv));
    }

    @Test
    public void shouldReturnHTTPHostWithPort() {
        assertEquals(goCDClient.getHttpHostWithPort("localhost", 80), "http://localhost:80");
    }

    @Test
    public void shouldReturnCorrectPropertSetPath() {
        assertEquals(goCDClient.getPropertySetPath("testing"), "http://localhost:80/go/properties/pipeline/pipelineCounter/stage/stageCounter/job/testing");
    }

}
