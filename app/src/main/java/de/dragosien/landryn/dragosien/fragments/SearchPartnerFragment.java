package de.dragosien.landryn.dragosien.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;

import de.dragosien.landryn.dragosien.R;
import de.dragosien.landryn.dragosien.objects.Dragon;
import de.dragosien.landryn.dragosien.objects.PartnerSearchResponse;

public class SearchPartnerFragment extends Fragment {

   private View view;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      // Inflate the layout for this fragment
      view = inflater.inflate(R.layout.fragment_search_partner, container, false);
      view.findViewById(R.id.button_search_partner).setOnClickListener(searchClickListener);
      return view;
   }

   private View.OnClickListener searchClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
         Dragon dragon = getDragonFromUI();
         new SearchForPartnerTask(dragon).execute();
      }
   };

   private Dragon getDragonFromUI() {
      View view = getView();
      Dragon dragon = new Dragon();

      dragon.gender = ((EditText) view.findViewById(R.id.et_value_sex)).getText().toString();
      dragon.position = ((EditText) view.findViewById(R.id.et_value_dragball_position)).getText().toString();
      dragon.mainColor = ((EditText) view.findViewById(R.id.et_value_main_color)).getText().toString();
      dragon.secondColor = ((EditText) view.findViewById(R.id.et_value_secondary_color)).getText().toString();
      dragon.strength = Integer.parseInt(((EditText) view.findViewById(R.id.et_value_strength)).getText().toString());
      dragon.agility = Integer.parseInt(((EditText) view.findViewById(R.id.et_value_agility)).getText().toString());
      dragon.firepower = Integer.parseInt(((EditText) view.findViewById(R.id.et_value_firepower)).getText().toString());
      dragon.willpower = Integer.parseInt(((EditText) view.findViewById(R.id.et_value_willpower)).getText().toString());
      dragon.intelligence = Integer.parseInt(((EditText) view.findViewById(R.id.et_value_intelligence)).getText().toString());

      return dragon;
   }

   private void showSearchResults(PartnerSearchResponse response) {
      ((TextView) view.findViewById(R.id.search_result_view)).setText(response.json);
   }

   //todo make separate class, use message bus
   private class SearchForPartnerTask extends AsyncTask<Void, Integer, PartnerSearchResponse> {

      private static final String BASE_URL = "http://borsti.bplaced.net/vermittlung/api.php?dragon=";
      private static final String DELIMITER = "%7C";
      private Dragon dragon;

      public SearchForPartnerTask(Dragon dragon) {
         this.dragon = dragon;
      }

      @Override
      protected PartnerSearchResponse doInBackground(Void... params) {
         StringBuilder builder = new StringBuilder();
         HttpClient client = new DefaultHttpClient();
         HttpGet httpGet = new HttpGet(getRequestUrl(dragon));

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

         return PartnerSearchResponse.fromJsonString(builder.toString());
      }

      private URI getRequestUrl(Dragon dragon) {
         String urlString = BASE_URL
               + dragon.gender + DELIMITER
               + dragon.mainColor + DELIMITER
               + dragon.secondColor + DELIMITER
               + dragon.strength + DELIMITER
               + dragon.agility + DELIMITER
               + dragon.firepower + DELIMITER
               + dragon.willpower + DELIMITER
               + dragon.intelligence + DELIMITER
               + dragon.position;
         return URI.create(urlString);
      }

      @Override
      protected void onPostExecute(PartnerSearchResponse response) {
         //todo use bus to populate result
         showSearchResults(response);
      }
   }

}
