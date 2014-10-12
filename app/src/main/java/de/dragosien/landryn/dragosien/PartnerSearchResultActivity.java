package de.dragosien.landryn.dragosien;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import de.dragosien.landryn.dragosien.objects.Dragon;


public class PartnerSearchResultActivity extends ActionBarActivity {

   private static final String DRAGON_BASE_URL = "http://www.dragosien.de/dragon/";
   private static final String OWNER_BASE_URL = "http://www.dragosien.de/user/";

   public static void callMe(Fragment fragment, Dragon dragon) {
      Intent intent = new Intent(fragment.getActivity(), PartnerSearchResultActivity.class);
      intent.putExtra("dragon", dragon);
      fragment.startActivity(intent);
   }

   private Dragon dragon;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_partner_search_result);
      dragon = (Dragon) getIntent().getSerializableExtra("dragon");
      updateUI();
   }

   private void updateUI() {
      TextView tvName = (TextView) findViewById(R.id.tv_dragon_name);
      tvName.setText(dragon.name);
      tvName.setTag(DRAGON_BASE_URL + dragon.name);
      tvName.setOnClickListener(linkListener);
      tvName.setTextColor(getResources().getColor(R.color.blue));

      TextView tvOwner = (TextView) findViewById(R.id.tv_dragon_owner);
      tvOwner.setText(dragon.owner);
      tvOwner.setTag(OWNER_BASE_URL + dragon.owner);
      tvOwner.setOnClickListener(linkListener);
      tvOwner.setTextColor(getResources().getColor(R.color.blue));
   }

   private View.OnClickListener linkListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
         String url = v.getTag().toString();
         Intent intent = new Intent(Intent.ACTION_VIEW);
         intent.setData(Uri.parse(url));
         startActivity(intent);
      }
   };
}
