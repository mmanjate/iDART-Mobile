package mz.org.fgh.idartlite.base;

import android.util.Base64;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;

import java.util.HashMap;
import java.util.Map;

public class RESTServiceHandler {

    private Map<String, String> mHeaders;
    //private User user;

    public RESTServiceHandler() {
        //this.user = user;

        mHeaders = buildAuthHeaders();
    }

    public void addHeader(String key, String value) {
        mHeaders.put(key,value);
    }

    public <T> void objectRequest(String url, int method, Map<String, Object> params, Class clazz, Response.Listener<T> response, Response.ErrorListener error) {
        String tag_json_arry = "object_req";

        ObjectRequest objectRequest;

        if (params != null) {
            objectRequest = new ObjectRequest<T>(method, url, params, clazz, response, error);
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

    public Map<String, String> buildAuthHeaders(){

        Map<String, String> headerMap = new HashMap<>();
        String credentials = null;//this.user.getUserName() + ":" + this.user.getPassword();
        String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        headerMap.put("Authorization", "Basic " + base64EncodedCredentials);
        return headerMap;
    }
}
