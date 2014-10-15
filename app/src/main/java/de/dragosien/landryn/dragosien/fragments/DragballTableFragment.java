package de.dragosien.landryn.dragosien.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import de.dragosien.landryn.dragosien.R;

public class DragballTableFragment extends Fragment {

   private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dragball_table, container, false);
       new DownLoadDragballTablesAsyncTask().execute();
       return view;
    }

   //todo make separate class, use message bus
   private class DownLoadDragballTablesAsyncTask extends AsyncTask<Void, Integer, String> {

      private static final String DRAGBALL_TABLE_URL = "http://www.magieland-online.de/Dragosien/Tabellen/tabellen_api.php";

      public DownLoadDragballTablesAsyncTask() {
      }

      @Override
      protected String doInBackground(Void... params) {
         StringBuilder builder = new StringBuilder();
         HttpClient client = new DefaultHttpClient();
         HttpGet httpGet = new HttpGet(DRAGBALL_TABLE_URL);

         try {
            HttpResponse response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
               HttpEntity entity = response.getEntity();
               InputStream content = entity.getContent();
               BufferedReader reader = new BufferedReader(new InputStreamReader(content));
               String line;
               while ((line = reader.readLine()) != null) {
                  builder.append(line);
               }
            }
         } catch (IOException e) {
            //todo error handling
         }

         return builder.toString();
      }

      @Override
      protected void onPostExecute(String response) {
         //todo use bus to populate result
         showDragballTables(response);
      }
   }

   private void showDragballTables(String response) {
      WebView webview = (WebView) view.findViewById(R.id.wvDragballTable);
      webview.loadDataWithBaseURL(null, response, "text/html", "utf-8", null);
   }


}
