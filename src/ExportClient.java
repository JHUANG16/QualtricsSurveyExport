import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Junjie on 6/20/17.
 */
public class ExportClient {
    private String Survey_Id = "SV_ehzZe7AQGt592yF";
    private String Api_Token = "RJWX26XbQ3ghvgIfwgtIGPP4ZHZTOXwTTx5Z17YU";
    private String DataCenterId = "co1";

    private final HttpClient httpClient;
    private final ObjectMapper mapper;

    public ExportClient(){
        httpClient = HttpClientBuilder.create().build();
        mapper = new ObjectMapper();
    }

    public void Start() throws IOException, URISyntaxException, InterruptedException{
        URI uri = new URIBuilder()
                .setScheme("https")
                .setHost(DataCenterId + ".qualtrics.com")
                .setPath("/API/v3/responseexports")
                .build();

        HttpPost post = new HttpPost();
        post.setHeader("X-API-TOKEN", Api_Token);
        post.setHeader("Accept","application/json");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("format","csv"));
        params.add(new BasicNameValuePair("surveyId", Survey_Id));
        post.setEntity(new UrlEncodedFormEntity(params));

        JsonNode responseJson = SendResponseEngineRequest(post);
        String exportId = responseJson.get("result").get("exportId").asText();

    }

    private JsonNode SendResponseEngineRequest(HttpRequestBase httpRequest) throws IOException{
        HttpResponse response = httpClient.execute(httpRequest);
        String body = EntityUtils.toString(response.getEntity());
        return mapper.readTree(body);
    }

}
