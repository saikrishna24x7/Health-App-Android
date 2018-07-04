package com.example.saikrishna.healthapplication.service;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class ServiceApiClient {
    public static void getResponse(Context context, String url, int method, JSONObject requestObject, final ServiceApiCallback callback) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(method, url, requestObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        callback.onSuccessResponse(response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        String body = "Error";
//                        if (error != null && error.networkResponse.data != null)
//                        {
//                            try
//                            {
//                                body = new String(error.networkResponse.data, "UTF-8");
//                            }
//                            catch (Exception e)
//                            {
//                                e.printStackTrace();
//                            }
//                        }
                        callback.onFailureResponse(body);
                    }
                }
        );

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        ServiceApiQueue.getInstance(context).addToRequestQueue(jsonObjReq);
    }
}
