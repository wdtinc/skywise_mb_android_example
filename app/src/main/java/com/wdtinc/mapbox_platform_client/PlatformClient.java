package com.wdtinc.mapbox_platform_client;


import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public final class PlatformClient {

    private static final String CONTOURED_RADAR_ANALYSIS_FRAMES_REQ = "/v2/products/d4fce90f-8ee7-472c-8d02-51058870a751/frames.json";

    private static List<MapFrame.Meta> toFrames(JSONArray jsonArray) {
        final List<MapFrame.Meta> frameMetas = new ArrayList<>(jsonArray.length());
        JSONObject nextJsonObj;
        for(int i = 0; i < jsonArray.length(); ++i) {
            try {
                nextJsonObj = jsonArray.getJSONObject(i);
                frameMetas.add(new MapFrame.Meta(nextJsonObj.getString("id"), nextJsonObj.getString("validTime")));
            } catch (ParseException | JSONException e) {
                Log.e(PlatformClient.class.getCanonicalName(), e.getMessage());
            }
        }
        return frameMetas;
    }


    interface FramesCallback {
        void onResult(List<MapFrame.Meta> frameMetas);
    }


    private final AsyncHttpClient asyncClient;
    private final String baseUrl;
    private final Context context;

    public PlatformClient(String baseUrl, Context context, String username, String password) {
        this.baseUrl = baseUrl;
        this.context = context;
        asyncClient = new AsyncHttpClient();
        asyncClient.setBasicAuth(username, password);
    }

    public void getAnalysisFrames(int frameCount, final FramesCallback cb) {
        if(frameCount < 1) {
            throw new IllegalArgumentException("frameCount must be positive");
        }

        final RequestParams reqParams = new RequestParams();
        reqParams.put("limit", frameCount);
        final Header headers[] = new Header[0];

        asyncClient.get(context, getAbsoluteUrl(CONTOURED_RADAR_ANALYSIS_FRAMES_REQ), headers, reqParams, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                cb.onResult(toFrames(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(PlatformClient.class.getCanonicalName(), errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.e(PlatformClient.class.getCanonicalName(), errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(PlatformClient.class.getCanonicalName(), throwable.toString());
            }
        });
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return baseUrl + relativeUrl;
    }
}
