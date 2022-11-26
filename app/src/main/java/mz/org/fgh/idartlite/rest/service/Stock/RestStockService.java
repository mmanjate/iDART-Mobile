package mz.org.fgh.idartlite.rest.service.Stock;

import android.app.Application;
import android.util.ArrayMap;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.UUID;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.rest.BaseRestService;
import mz.org.fgh.idartlite.base.rest.ServiceWatcher;
import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.listener.rest.RestResponseListener;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.helper.RESTServiceHandler;
import mz.org.fgh.idartlite.service.clinic.ClinicService;
import mz.org.fgh.idartlite.service.clinic.IClinicService;
import mz.org.fgh.idartlite.service.drug.DrugService;
import mz.org.fgh.idartlite.service.drug.IDrugService;
import mz.org.fgh.idartlite.service.stock.IStockService;
import mz.org.fgh.idartlite.service.stock.StockService;

import static mz.org.fgh.idartlite.util.DateUtilities.getUtilDateFromString;

public class RestStockService extends BaseRestService {

    public static final String TAG = "RestStockService";
    private static IStockService stockService;
    private static IDrugService drugService;
    private static IClinicService clinicService;

    public RestStockService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public static void restPostStockCenter(Clinic clinic) throws SQLException{

        String url = BaseRestService.baseUrl + "/stockcenter?on_conflict=id";
        clinicService = new ClinicService(BaseService.getApp(), null);
        stockService = new StockService(getApp(), null);

        if (clinic == null)
            clinic = clinicService.getAllClinics().get(0);

        try {
            Clinic finalClinic = clinic;
            getRestServiceExecutor().execute(() -> {

                RESTServiceHandler handler = new RESTServiceHandler();

                try {

                    Map<String, Object> stockcenter = new ArrayMap<>();
                    stockcenter.put("id", finalClinic.getRestId());
                    stockcenter.put("stockcentername", finalClinic.getClinicName());
                    stockcenter.put("preferred", false);
                    stockcenter.put("clinicuuid", finalClinic.getUuid());

                    Gson g = new Gson();
                    String restObject = g.toJson(stockcenter);

                    handler.addHeader("Content-Type", "application/json");
                    JSONObject jsonObject = new JSONObject(restObject);
                    handler.objectRequest(url, Request.Method.POST, jsonObject, Object[].class, new Response.Listener<TreeMap<String, Object>>() {

                        @Override
                        public void onResponse(TreeMap<String, Object> response) {
                            Log.d(TAG, "onResponse: StockCenter enviado : " + response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "onErrorResponse: Erro no POST :" + generateErrorMsg(error));
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

  /*  public static void restGetStock(Clinic clinic) throws SQLException {
        getStock(clinic,null);
    }

    public static void restGetStock(Clinic clinic, ServiceWatcher watcher) throws SQLException {
        getStock(clinic, watcher);
    }*/


    public static void getStock(RestResponseListener listener,long offset, long limit) throws SQLException {

        stockService = new StockService(getApp(), null);
        clinicService = new ClinicService(BaseService.getApp(), null);

     //   if (clinic == null)
          Clinic clinic = clinicService.getAllClinics().get(0);

      // Clinic finalClinic = clinic;
   //     String url = BaseRestService.baseUrl + "/stock?select=*,stocklevel(*)&stockcenter=eq." + clinic.getRestId() + "&expirydate=gt.TODAY()";
          String url;
        if (limit > 0) {
            url = BaseRestService.baseUrl + "/stock?select=*,stocklevel(*)&stockcenter=eq." + clinic.getRestId() + "&expirydate=gt.TODAY()" +
                    "&offset=" + offset +
                    "&limit=" + limit;
        }else {
            url = BaseRestService.baseUrl + "/stock?select=*,stocklevel(*)&stockcenter=eq." + clinic.getRestId() + "&expirydate=gt.TODAY()";
        }
        Log.d(TAG, "-===========================================================================Offset " + offset + "  ====== Limit " + limit );

        List<Stock> newStock = new ArrayList<>();
        getRestServiceExecutor().execute(() -> {
            RESTServiceHandler handler = new RESTServiceHandler();
            handler.addHeader("Content-Type", "Application/json");
            handler.objectRequest(url, Request.Method.GET, null, Object[].class, new Response.Listener<Object[]>() {
                @Override
                public void onResponse(Object[] stocks) {

                    if (stocks.length > 0) {

                        int counter = 0;

                        for (Object stock : stocks) {
                            Log.i(TAG, "onResponse: " + stock);
                            try {
                                LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) stock;
                                if (itemresult != null) {
                                    Stock localStock = getNewLocalStock(itemresult, clinic);
                                    if (localStock != null) {
                                        stockService.saveOrUpdateViaRest(localStock);
                                        newStock.add(localStock);
                                        counter++;
                                    }
                                } else {
                                    Log.e(TAG, "onResponse: " + stock + " Nao tem referencia no servidor central");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                continue;
                            }
                        }
                        listener.doOnResponse(BaseRestService.REQUEST_SUCESS, newStock);
                    } else {
                        listener.doOnResponse(REQUEST_NO_DATA, null);
                        Log.w(TAG, "Response Sem Info." + stocks.length);
                    }
                }
            },  error -> {
                listener.doOnResponse(generateErrorMsg(error), null);
                Log.d(TAG, "onErrorResponse: Erro no GET :" + generateErrorMsg(error));
            });
        });
    }

    public static void restPostStock(Stock localStock, ServiceWatcher watcher, RestResponseListener listener) {
        doRestPostStock(localStock, watcher, listener);
    }

    public static void restPostStock(Stock localStock) {
        doRestPostStock(localStock, null, null);
    }

    private static void doRestPostStock(Stock localStock, ServiceWatcher watcher, RestResponseListener listener) {

        String url = BaseRestService.baseUrl + "/stock?select=id";

        stockService = new StockService(getApp(), null);

        try {
            getRestServiceExecutor().execute(() -> {

                RESTServiceHandler handler = new RESTServiceHandler();

                try {
                    Map<String, Object> stock = setSyncStock(localStock);

                    Gson g = new Gson();
                    String restObject = g.toJson(stock);

                    handler.addHeader("Content-Type", "application/json");
                    JSONObject jsonObject = new JSONObject(restObject);
                    handler.objectRequest(url, Request.Method.POST, jsonObject, Object.class, (Response.Listener<Map<String, Object>>) response -> {
                        Log.d(TAG, "onResponse: Stock enviado " + response.toString());
                        try {
                            localStock.setRestId(Integer.parseInt(getValueFromResponse(response, "Location", ".")));
                            localStock.setSyncStatus("S");
                            stockService.saveOrUpdateViaRest(localStock);
                            restPostStockLevel(localStock);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }, error -> Log.d(TAG, "onErrorResponse: Erro no POST :" + error.getCause()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void restPostStockLevel(Stock localStock) {

        String url = BaseRestService.baseUrl + "/stocklevel";

        stockService = new StockService(getApp(), null);

        try {
            getRestServiceExecutor().execute(() -> {

                RESTServiceHandler handler = new RESTServiceHandler();

                try {
                    Map<String, Object> stocklevel = setSyncStocklevel(localStock);

                    Gson g = new Gson();
                    String restObject = g.toJson(stocklevel);

                    handler.addHeader("Content-Type", "application/json");
                    JSONObject jsonObject = new JSONObject(restObject);

                    handler.objectRequest(url, Request.Method.POST, jsonObject, Object.class, new Response.Listener<Map<String, Object>>() {

                        @Override
                        public void onResponse(Map<String, Object> response) {
                            Log.d(TAG, "onResponse: StockLevel enviado " + response.toString());
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

    public static void restGetAndPatchStockLevel(Stock localStock, ServiceWatcher watcher, RestResponseListener listener) {
        doRestGetAndPatchStockLevel(localStock, watcher, listener);
    }

    public static void restGetAndPatchStockLevel(Stock localStock, ServiceWatcher watcher) {
        doRestGetAndPatchStockLevel(localStock, watcher, null);
    }

    public static void restGetAndPatchStockLevel(Stock localStock) {
        doRestGetAndPatchStockLevel(localStock, null, null);
    }

    public static void doRestGetAndPatchStockLevel(Stock stock, ServiceWatcher watcher, RestResponseListener listener) {

        String url = BaseRestService.baseUrl + "/stocklevel?batch=eq." + stock.getRestId();

        getRestServiceExecutor().execute(() -> {

            RESTServiceHandler handler = new RESTServiceHandler();
            handler.addHeader("Content-Type", "Application/json");
            handler.objectRequest(url, Request.Method.GET, null, Object[].class, new Response.Listener<Object[]>() {
                @Override
                public void onResponse(Object[] stocklevels) {

                    if (stocklevels.length > 0) {
                        for (Object stocklevel : stocklevels) {
                            Log.i(TAG, "onResponse: " + stocklevel);
                            try {
                                LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) stocklevel;
                                if (itemresult.get("fullcontainersremaining") != null) {
                               //     stock.setStockMoviment((int) Float.parseFloat(itemresult.get("fullcontainersremaining").toString()) - stock.getStockMoviment());
                                    stock.setSyncStatus("S");
                                   stockService.saveOrUpdateViaRest(stock);
                                    restPatchStockLevel(stock);
                                } else {
                                    Log.e(TAG, "onResponse: " + stock + " Nao tem referencia no servidor central");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                continue;
                            }
                        }
                    } else
                        Log.w(TAG, "Response Sem Info." + stocklevels.length);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Response", generateErrorMsg(error));
                }
            });
        });
    }

    public static void restPatchStockLevel(Stock localStock) {

        String url = BaseRestService.baseUrl + "/stocklevel?batch=eq." + localStock.getRestId();
        try {
            getRestServiceExecutor().execute(() -> {
                RESTServiceHandler handler = new RESTServiceHandler();
                try {
                    Map<String, Object> stocklevel = setSyncStocklevel(localStock);
                    Gson g = new Gson();
                    String restObject = g.toJson(stocklevel);

                    handler.addHeader("Content-Type", "application/json");
                    JSONObject jsonObject = new JSONObject(restObject);
                    handler.objectRequest(url, Request.Method.PATCH, jsonObject, Object.class, new Response.Listener<Map<String, Object>>() {

                        @Override
                        public void onResponse(Map<String, Object> response) {
                            Log.d(TAG, "onResponse: StockLevel Actualizado enviado " + response.toString());
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

    private static Stock getNewLocalStock(LinkedTreeMap<String, Object> itemresult, Clinic clinic) throws SQLException {

        stockService = new StockService(getApp(),null);
        drugService = new DrugService(getApp(),null);

        ArrayList stocklevel = (ArrayList) Objects.requireNonNull(itemresult.get("stocklevel"));
        Drug localDrug = drugService.getDrugByRestID((int) Float.parseFloat(itemresult.get("drug").toString()));
        List<Stock> stockList = stockService.getAllStocksByClinicAndDrug(clinic,localDrug);

        Stock localStock = null;

        for(Stock stock: stockList){
            if(stock.getBatchNumber().equalsIgnoreCase(itemresult.get("batchnumber").toString()))
                return null;
        }

        if (stocklevel.size() != 0) {

            LinkedTreeMap<String, Object> stockAmpunt = (LinkedTreeMap<String, Object>) stocklevel.get(0);
            drugService = new DrugService(BaseService.getApp(), null);
            localStock = new Stock();
            localStock.setRestId((int) Float.parseFloat(itemresult.get("id").toString()));
            localStock.setBatchNumber(itemresult.get("batchnumber").toString());
            localStock.setClinic(clinic);
            localStock.setStockMoviment((int) Float.parseFloat(stockAmpunt.get("fullcontainersremaining").toString()));
            localStock.setDateReceived(getUtilDateFromString(itemresult.get("datereceived").toString(), "yyyy-MM-dd'T'HH:mm:ss"));
            localStock.setDrug(drugService.getDrugByRestID((int) Float.parseFloat(itemresult.get("drug").toString())));
            localStock.setExpiryDate(getUtilDateFromString(itemresult.get("expirydate").toString(), "yyyy-MM-dd'T'HH:mm:ss"));
            localStock.setOrderNumber(itemresult.get("numeroguia").toString());
            localStock.setPrice(0);
            localStock.setPrice(Float.parseFloat(itemresult.get("unitprice").toString()));
            localStock.setShelfNumber((int) Float.parseFloat(itemresult.get("shelfnumber").toString()));
            localStock.setStockAdjustments(0);
            localStock.setSyncStatus(BaseModel.SYNC_SATUS_SENT);
            localStock.setUnitsReceived((int) Float.parseFloat(itemresult.get("unitsreceived").toString()));
            localStock.setUuid(UUID.randomUUID().toString());

            return localStock;
        } else return null;
    }

    private static Map<String, Object> setSyncStock(Stock stock) {

        Map<String, Object> restStock = new ArrayMap<>();
        restStock.put("drug", stock.getDrug().getRestId());
        restStock.put("batchnumber", stock.getBatchNumber());
        restStock.put("datereceived", stock.getDateReceived());
        restStock.put("stockcenter", stock.getClinic().getRestId());
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

    private static Map<String, Object> setSyncStocklevel(Stock stock) {

        Map<String, Object> restStock = new ArrayMap<>();
        restStock.put("batch", stock.getRestId());
        restStock.put("fullcontainersremaining", stock.getStockMoviment());
        restStock.put("loosepillsremaining", 0);

        return restStock;
    }


    private static String getValueFromResponse(Map<String, Object> response, String key, String getItem) {

        String result = (String) response.get(key);

        if (result.contains(getItem))
            return result.substring(result.indexOf(getItem) + 1);
        else
            return "0";
    }

}
