package mz.org.fgh.idartlite.rest.helper;

import android.os.StrictMode;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import mz.org.fgh.idartlite.base.application.IdartLiteApplication;
import mz.org.fgh.idartlite.base.rest.BaseRestService;
import mz.org.fgh.idartlite.model.User;

public class RESTServiceHandler {

    private Map<String, String> mHeaders;
    private User user;

    public RESTServiceHandler() {
        //this.user = user;

        mHeaders = buildAuthHeaders();
    }

    public void addHeader(String key, String value) {
        mHeaders.put(key, value);
    }

    public <T> void objectRequest(String url, int method, JSONObject input, Class clazz, Response.Listener<T> response, Response.ErrorListener error) {
        String tag_json_arry = "object_req";

        ObjectRequest objectRequest;

        if (input != null) {
            objectRequest = new ObjectRequest<T>(method, url, input, clazz, response, error);
            objectRequest.setHeaders(mHeaders);
        } else {
            objectRequest = new ObjectRequest<T>(method, url, null, clazz, response, error);
            objectRequest.setHeaders(mHeaders);
        }

        IdartLiteApplication.getInstance().addToRequestQueue(objectRequest, tag_json_arry);

    }

    public void imageRequest(String url, ImageView imageView, int icon_loading, int icon_error) {
        ImageLoader imageLoader = IdartLiteApplication.getInstance().getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(imageView, icon_loading, icon_error));
    }

    public Map<String, String> buildAuthHeaders() {

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer " + getJWTPermission("postgres", "postgres"));
        return headerMap;

    }

    public void setUser(User user) {
        this.user = user;
    }

    public static boolean getServerStatus(String url) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        boolean result = false;
        int code = 200;
        try {
            URL siteURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.connect();

            code = connection.getResponseCode();
            connection.disconnect();
            if (code == 200) {
                result = true;
            } else {
                result = true;
            }
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    public static String getJWTPermission(String username, String pass) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection connection = null;
        String url = BaseRestService.baseUrl + "/rpc/login";
        String parameters = "{\"username\":\"" + username + "\",\"pass\":\"" + pass + "\"}";

        String result = "";
        int code = 200;
        try {
            URL siteURL = new URL(url);
            connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(parameters);
            writer.flush();
            connection.setConnectTimeout(3000);
            connection.connect();
            Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

            for (int c; (c = in.read()) >= 0; ) {
                result = result.concat(String.valueOf((char) c));
            }

            result = result.replace("[{", "{");
            result = result.replace("}]", "}");
            if (result.contains("{")) {
                JSONObject jsonObj = new JSONObject(result);
                try {
                    result = jsonObj.get("token").toString();
                } catch (Exception e) {
                    connection.disconnect();
                    e.printStackTrace();
                }
            }
            code = connection.getResponseCode();
            connection.disconnect();
            if (code == 200) {
                return result;
            } else {
                return null;
            }
        } catch (Exception e) {
            if (connection != null)
                connection.disconnect();
            return null;
        }
    }
}
