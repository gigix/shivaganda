package io.ona.ziggy.service;

import io.ona.ziggy.domain.Response;
import io.ona.ziggy.domain.ResponseStatus;
import io.ona.ziggy.repository.AllSettings;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import static io.ona.ziggy.util.Log.logInfo;
import static io.ona.ziggy.util.Log.logWarn;
import static java.text.MessageFormat.format;

public class HTTPAgent {
    private final DefaultHttpClient httpClient;
    private AllSettings allSettings;

    public HTTPAgent(AllSettings allSettings) {
        this.allSettings = allSettings;
        httpClient = new DefaultHttpClient();
    }

    public Response<String> fetch(String uri) {
        try {
            setCredentials(allSettings.fetchRegisteredReporter(), allSettings.fetchReporterPassword());
            HttpGet httpGet = new HttpGet(uri);
            HttpResponse response = httpClient.execute(httpGet);
            String responseContent = IOUtils.toString(response.getEntity().getContent());
            return new Response<String>(ResponseStatus.success, responseContent);
        } catch (Exception e) {
            logWarn(e.toString());
            return new Response<String>(ResponseStatus.failure, null);
        }
    }

    public Response<String> postJSONRequest(String uri, String json) {
        try {
            logInfo("Posting submissions");
            setCredentials(allSettings.fetchRegisteredReporter(), allSettings.fetchReporterPassword());
            HttpPost httpPost = new HttpPost(uri);
            StringEntity entity = new StringEntity(json);
            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(entity);
            httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");

            HttpResponse response = httpClient.execute(httpPost);
            logInfo(format("Done posting submissions, response status code: {0}", response.getStatusLine()));
            String responseContent = IOUtils.toString(response.getEntity().getContent());
            return new Response<String>(ResponseStatus.success, responseContent);
        } catch (Exception e) {
            logWarn(e.toString());
            return new Response<String>(ResponseStatus.failure, null);
        }
    }

    private void setCredentials(String userName, String password) {
        httpClient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY),
                new UsernamePasswordCredentials(userName, password));
    }
}
