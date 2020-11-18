package mz.org.fgh.idartlite.rest.service;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.helper.RESTServiceHandler;
import mz.org.fgh.idartlite.service.drug.DrugService;
import mz.org.fgh.idartlite.service.drug.IDiseaseTypeService;
import mz.org.fgh.idartlite.service.drug.IDrugService;
import mz.org.fgh.idartlite.service.drug.IFormService;
import mz.org.fgh.idartlite.service.territory.CountryService;
import mz.org.fgh.idartlite.service.territory.DistrictService;
import mz.org.fgh.idartlite.service.territory.ICountryService;
import mz.org.fgh.idartlite.service.territory.IDistrictService;
import mz.org.fgh.idartlite.service.territory.IProvinceService;
import mz.org.fgh.idartlite.service.territory.ProvinceService;

public class RestTerritoryService extends BaseService {

    private static final String TAG = "RestDrugService";
    private static ICountryService countryService;
    private static IProvinceService provinceService;
    private static IDistrictService districtService;

    public RestTerritoryService(Application application, User currentUser) {
        super(application, currentUser);

     //   countryService = new CountryService(application,currentUser);
    }

    public static void restGetAllCountries() {

        String url = BaseService.baseUrl + "/country?code=eq.01";
        countryService = new CountryService(getApp(),null);


            getRestServiceExecutor().execute(() -> {

                RESTServiceHandler handler = new RESTServiceHandler();
                handler.addHeader("Content-Type", "Application/json");

                handler.objectRequest(url, Request.Method.GET, null, Object[].class, new Response.Listener<Object[]>() {
                    @Override
                    public void onResponse(Object[] countrys) {

                        if (countrys.length > 0) {
                            for (Object country : countrys) {
                                try {
                                    Log.i(TAG, "onResponse: " + country);
                                  if(!countryService.checkCountry(country)){
                                       countryService.saveOnCountry(country);
                                   }else{
                                       Log.i(TAG, "onResponse: "+country+" Ja Existe");
                                  }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }finally {
                                    continue;
                                }
                            }
                        }else
                            Log.w(TAG, "Response Sem Info." + countrys.length);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Response", generateErrorMsg(error));
                    }
                });
            });
    }

    public static void restGetAllProvinces() {

        String url = BaseService.baseUrl + "/province";

        provinceService = new ProvinceService(getApp(),null);

        getRestServiceExecutor().execute(() -> {

            RESTServiceHandler handler = new RESTServiceHandler();
            handler.addHeader("Content-Type", "Application/json");

            handler.objectRequest(url, Request.Method.GET, null, Object[].class, new Response.Listener<Object[]>() {
                @Override
                public void onResponse(Object[] provinces) {

                    if (provinces.length > 0) {
                        for (Object province : provinces) {
                            try {
                                Log.i(TAG, "onResponse: " + province);
                                   if(!provinceService.checkProvince(province)){
                                provinceService.saveOnProvince(province);
                                   }else{
                                      Log.i(TAG, "onResponse: "+province+" Ja Existe");
                                   }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }finally {
                                continue;
                            }
                        }
                    }else
                        Log.w(TAG, "Response Sem Info." + provinces.length);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Response", generateErrorMsg(error));
                }
            });
        });
    }

    public static void restGetAllDistricts() {

        String url = BaseService.baseUrl + "/district";

        districtService = new DistrictService(getApp(),null);

        getRestServiceExecutor().execute(() -> {

            RESTServiceHandler handler = new RESTServiceHandler();
            handler.addHeader("Content-Type", "Application/json");

            handler.objectRequest(url, Request.Method.GET, null, Object[].class, new Response.Listener<Object[]>() {
                @Override
                public void onResponse(Object[] districts) {

                    if (districts.length > 0) {
                        for (Object district : districts) {
                            try {
                                Log.i(TAG, "onResponse: " + district);
                                  if(!districtService.checkDistrict(district)){
                                districtService.saveOnDistrict(district);
                                  }else{
                                    Log.i(TAG, "onResponse: "+district+" Ja Existe");
                                 }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }finally {
                                continue;
                            }
                        }
                    }else
                        Log.w(TAG, "Response Sem Info." + districts.length);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Response", generateErrorMsg(error));
                }
            });
        });
    }
}
