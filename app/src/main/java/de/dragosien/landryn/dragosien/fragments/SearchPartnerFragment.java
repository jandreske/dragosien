package de.dragosien.landryn.dragosien.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import de.dragosien.landryn.dragosien.PartnerSearchResultActivity;
import de.dragosien.landryn.dragosien.R;
import de.dragosien.landryn.dragosien.objects.Dragon;
import de.dragosien.landryn.dragosien.objects.PartnerSearchResponse;

public class SearchPartnerFragment extends Fragment {

   private View view;
   private ProgressDialog progress;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      // Inflate the layout for this fragment
      view = inflater.inflate(R.layout.fragment_search_partner, container, false);
      view.findViewById(R.id.button_search_partner).setOnClickListener(searchClickListener);
      progress = new ProgressDialog(getActivity());
      return view;
   }

   @Override
   public void onPause() {
      progress.dismiss();
      super.onPause();
   }

   private View.OnClickListener searchClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
         progress.setCancelable(false);
         progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
         progress.setMessage(getString(R.string.searching_for_partner));
         progress.show();
         Dragon dragon = getDragonFromUI();
         //todo move asynctask to result and call result which then calls the task
         new SearchForPartnerTask(dragon).execute();
      }
   };

   private Dragon getDragonFromUI() {
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
      progress.dismiss();
      Dragon dragon = Dragon.fromPartnerSearchResponse(response);
      Dragon egg = Dragon.getEggfromPartnerSearchResponse(response);
      PartnerSearchResultActivity.callMe(this, dragon, egg);
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
