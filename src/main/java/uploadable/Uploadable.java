package uploadable;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Map;

import com.google.gson.Gson;

import retrofit.converter.GsonConverter;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.http.QueryMap;

import uploadable.util.Base64;
import uploadable.UploadFile;
import uploadable.models.Status;
import uploadable.models.Result;

public interface Uploadable {

    public class Builder {

      private static final String HEADER_AUTHORIZATION = "Authorization";
      private RestAdapter.LogLevel logLevel = RestAdapter.LogLevel.NONE;

      private String endpoint = "https://api.streamable.com";
      private String username;
      private String password;

      private RestAdapter.LogLevel getLogLevel() {
        return logLevel;
      }

      public Builder setLogLevel(RestAdapter.LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
      }

      public String getEndpoint() {
        return endpoint;
      }

      public Builder setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
      }

      public Builder setCredentials(String username, String password) {
        this.username = username;
        this.password = password;
        return this;
      }

      private String getEncodedAuthorization() {
        if (username != null && password != null){
          String creds = String.format("%s:%s", username, password);
          try {
            return Base64.encodeToString(creds.getBytes("UTF-8"), Base64.NO_WRAP);
          } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
          }
        } else {
          return null;
        }
      }

      private RequestInterceptor getRequestInterceptor() {
        return new RequestInterceptor() {
          @Override
          public void intercept(RequestFacade request) {
            String auth = getEncodedAuthorization();
            if (auth != null) {
              request.addHeader(HEADER_AUTHORIZATION, String.format("Basic %s", auth));
            }
          }
        };
      }

      private RestAdapter buildRestAdapter() {
        return new RestAdapter.Builder()
          .setEndpoint(getEndpoint())
          .setRequestInterceptor(getRequestInterceptor())
          .setLogLevel(getLogLevel())
          .build();
      }

      public Uploadable build() {
        RestAdapter restAdapter = buildRestAdapter();
        return restAdapter.create(Uploadable.class);
      }

    }

    @Multipart
    @POST("/upload")
    Result uploadVideo(
        @Part("file") UploadFile file,
        @QueryMap Map<String, String> options);

    @GET("/import")
    Result importVideo(
      @Query("url") URL url,
      @QueryMap Map<String, String> options);

    @GET("/videos/{shortcode}")
    Status checkStatus(
      @Path("shortcode") String shortcode);

}