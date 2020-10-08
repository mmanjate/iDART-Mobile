package mz.org.fgh.idartlite.service.restService;

import android.app.Application;
import android.util.ArrayMap;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.Map;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.RESTServiceHandler;
import mz.org.fgh.idartlite.service.StockService;

public class RestStockService extends BaseService {

    private static final String TAG = "RestStockService";
    private static StockService stockService;

    public RestStockService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public static void restPostStockCenter(Clinic clinic) {

        String url = BaseService.baseUrl + "/stockcenter?on_conflict=id";

        stockService = new StockService(getApp(), null);

        try {
            getRestServiceExecutor().execute(() -> {

                RESTServiceHandler handler = new RESTServiceHandler();

                try {
                    Map<String, Object> stockcenter = new ArrayMap<>();
                    stockcenter.put("id",clinic.getId());
                    stockcenter.put("stockcentername",clinic.getClinicName());
                    stockcenter.put("preferred",false);
                    stockcenter.put("clinicuuid",clinic.getUuid());

                    Gson g = new Gson();
                    String restObject = g.toJson(stockcenter);

                    handler.addHeader("Content-Type", "application/json");
                    JSONObject jsonObject = new JSONObject(restObject);

                    handler.objectRequest(url, Request.Method.POST, jsonObject, Object[].class, new Response.Listener<Object[]>() {

                        @Override
                        public void onResponse(Object[] response) {
                            Log.d(TAG, "onResponse: StockCenter enviado ");
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "onErrorResponse: Erro no POST :" + error.getMessage());
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void restPostStock(Stock stok) {

        String url = BaseService.baseUrl + "/stock";

        stockService = new StockService(getApp(), null);

        try {
            getRestServiceExecutor().execute(() -> {

                RESTServiceHandler handler = new RESTServiceHandler();

                try {
                    Map<String, Object> stock = setSyncDispense(stok);

                    Gson g = new Gson();
                    String restObject = g.toJson(stock);

                    handler.addHeader("Content-Type", "application/json");
                    JSONObject jsonObject = new JSONObject(restObject);

                    handler.objectRequest(url, Request.Method.POST, jsonObject, Object[].class, new Response.Listener<Object[]>() {

                        @Override
                        public void onResponse(Object[] response) {
                            Log.d(TAG, "onResponse: Stock enviado " + response);
                            try {
                                stok.setSyncStatus("S");
                                stockService.saveOrUpdateStock(stok);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "onErrorResponse: Erro no POST :" + error.getCause());
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Object> setSyncDispense(Stock stock) {

        Map<String, Object> restStock = new ArrayMap<>();
        restStock.put("id",stock.getId());
        restStock.put("drug", stock.getDrug().getId());
        restStock.put("batchnumber", stock.getBatchNumber());
        restStock.put("datereceived", stock.getDateReceived());
        restStock.put("stockcenter", stock.getClinic().getId());
        restStock.put("expirydate", stock.getExpiryDate());
        restStock.put("modified", "T");
        restStock.put("shelfnumber", "0");
        restStock.put("unitsreceived", stock.getQuantity());
        restStock.put("manufacturer", "Manufacturer");
        restStock.put("hasunitsremaining", "");
        restStock.put("unitprice", stock.getPrice());
        restStock.put("numeroguia", stock.getOrderNumber());

        return restStock;
    }

}
