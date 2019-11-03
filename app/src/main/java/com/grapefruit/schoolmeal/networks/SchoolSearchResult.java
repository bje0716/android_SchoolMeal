package com.grapefruit.schoolmeal.networks;

import android.os.AsyncTask;
import android.view.View;

import com.google.gson.Gson;
import com.grapefruit.schoolmeal.datas.SchoolRequest;
import com.grapefruit.schoolmeal.databinding.ActivitySchoolSearchBinding;
import com.grapefruit.schoolmeal.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SchoolSearchResult extends AsyncTask<String, String, String> {

    public interface OnDoneSchoolSearchCallback {
        void processFinish(JSONObject object);
    }

    private OnDoneSchoolSearchCallback callback;
    private ActivitySchoolSearchBinding binding;
    private SchoolRequest request;

    public SchoolSearchResult(ActivitySchoolSearchBinding binding, SchoolRequest request, OnDoneSchoolSearchCallback callback) {
        this.request = request;
        this.callback = callback;
        this.binding = binding;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        binding.progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject object = new JSONObject(s);
            callback.processFinish(object);
            binding.progress.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), new Gson().toJson(request));
        Request request = new Request.Builder()
                .url(Constants.DEFAULT_URL + strings[0] + Constants.SCHOOL_SEARCH)
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
}
