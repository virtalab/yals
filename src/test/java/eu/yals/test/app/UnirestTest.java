package eu.yals.test.app;

import eu.yals.constants.App;
import eu.yals.test.TestUtils;
import kong.unirest.BodyPart;
import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;

import java.lang.reflect.Field;

/**
 * Tests, where we run application same ways in {@link eu.yals.test.ui.UITest}
 * and test by doing requests using {@link kong.unirest.Unirest}
 *
 * @since 2.5.1
 */
@Slf4j
public class UnirestTest {
    protected static final String TEST_URL = TestUtils.getTestUrl();
    public static String TAG = "[Unirest]";

    @BeforeClass
    public static void setUp() {
    }

    public void logRequestAndResponse(HttpRequest request, HttpResponse response, String tag) {
        TAG = tag;
        logRequest(request);
        logResponse(response);
    }

    private void logRequest(HttpRequest request) {
        StringBuilder reqLog = new StringBuilder("Request: ").append(App.NEW_LINE);
        reqLog.append(String.format("Request URL: %s", request.getUrl())).append(App.NEW_LINE);
        reqLog.append(String.format("Request method: %s", request.getHttpMethod().name())).append(App.NEW_LINE);
        if (request.getBody().isPresent()) {
            try {
                reqLog.append(String.format("Request body: %s", getRequestBody(request))).append(App.NEW_LINE);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                reqLog.append(String.format("Request body: %s", "<failed to retrieve>")).append(App.NEW_LINE);
            }
        }
        if (request.getHeaders().size() > 0) {
            reqLog.append("Request headers: ").append(App.NEW_LINE);
            for (kong.unirest.Header header : request.getHeaders().all()) {
                reqLog.append(String.format("%s: %s", header.getName(), header.getValue())).append(App.NEW_LINE);
            }
        }
        log.info(String.format("%s %s", TAG, reqLog.toString()));
    }

    private void logResponse(HttpResponse response) {
        StringBuilder respLog = new StringBuilder("Response: ").append(App.NEW_LINE);
        respLog.append(String.format("Response status code: %s", response.getStatus())).append(App.NEW_LINE);
        respLog.append(String.format("Response body: %s", response.getBody())).append(App.NEW_LINE);
        if (response.getHeaders().size() > 0) {
            respLog.append("Response headers: ").append(App.NEW_LINE);
            for (kong.unirest.Header header : response.getHeaders().all()) {
                respLog.append(String.format("%s: %s", header.getName(), header.getValue())).append(App.NEW_LINE);
            }
        }
        log.info(String.format("%s %s", TAG, respLog.toString()));
    }

    private Object getRequestBody(HttpRequest request) throws NoSuchFieldException, IllegalAccessException {
        Field f = request.getClass().getDeclaredField("body");
        f.setAccessible(true);
        Object bodyPart = f.get(request);
        if (bodyPart instanceof BodyPart) {
            return ((BodyPart) bodyPart).getValue();
        } else {
            throw new NoSuchFieldException("body is not instance of " + BodyPart.class.getSimpleName());
        }
    }
}
