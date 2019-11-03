package com.grapefruit.schoolmeal.networks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.grapefruit.schoolmeal.databinding.ActivityMainBinding;
import com.grapefruit.schoolmeal.datas.MealRequest;
import com.grapefruit.schoolmeal.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MealDataParse extends AsyncTask<String, String, String> {

    public interface OnDoneMealFetchDataCallback {
        void processFinish(JSONObject object);
    }

    private ActivityMainBinding binding;
    private MealRequest request;
    private OnDoneMealFetchDataCallback callback;

    public MealDataParse(ActivityMainBinding binding, MealRequest request, OnDoneMealFetchDataCallback callback) {
        this.binding = binding;
        this.request = request;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), new Gson().toJson(request));
        Request request = new Request.Builder()
                .url(Constants.DEFAULT_URL + strings[0] + Constants.SCHOOL_MEAL)
                .addHeader("Cookie", strings[1])
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("mealData", s);
        try {
            JSONObject object = new JSONObject(s);
            callback.processFinish(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
