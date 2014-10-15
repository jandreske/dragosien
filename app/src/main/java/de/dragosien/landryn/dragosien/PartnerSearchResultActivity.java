package de.dragosien.landryn.dragosien;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import de.dragosien.landryn.dragosien.objects.Dragon;


public class PartnerSearchResultActivity extends ActionBarActivity {

   private static final String DRAGON_BASE_URL = "http://www.dragosien.de/dragon/";
   private static final String OWNER_BASE_URL = "http://www.dragosien.de/user/";

   public static void callMe(Fragment fragment, Dragon dragon, Dragon egg) {
      Intent intent = new Intent(fragment.getActivity(), PartnerSearchResultActivity.class);
      intent.putExtra("dragon", dragon);
      intent.putExtra("egg", egg);
      fragment.startActivity(intent);
   }

   private Dragon dragon;
   private Dragon egg;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_partner_search_result);
      dragon = (Dragon) getIntent().getSerializableExtra("dragon");
      egg = (Dragon) getIntent().getSerializableExtra("egg");
      updateUI();
   }

   private void updateUI() {
      //dragon name, with link to profile
      TextView tvName = (TextView) findViewById(R.id.tv_dragon_name);
      tvName.setText(dragon.name);
      tvName.setTag(DRAGON_BASE_URL + dragon.name);
      tvName.setOnClickListener(linkListener);

      //dragon owner, with link to profile
      TextView tvOwner = (TextView) findViewById(R.id.tv_dragon_owner);
      tvOwner.setText(dragon.owner);
      tvOwner.setTag(OWNER_BASE_URL + dragon.owner);
      tvOwner.setOnClickListener(linkListener);

      //dragon values
      ((TextView) findViewById(R.id.tv_strength_value)).setText(Integer.toString(dragon.strength));
      ((TextView) findViewById(R.id.tv_agility_value)).setText(Integer.toString(dragon.agility));
      ((TextView) findViewById(R.id.tv_firepower_value)).setText(Integer.toString(dragon.firepower));
      ((TextView) findViewById(R.id.tv_willpower_value)).setText(Integer.toString(dragon.willpower));
      ((TextView) findViewById(R.id.tv_intelligence_value)).setText(Integer.toString(dragon.intelligence));

      //egg values
      ((TextView) findViewById(R.id.tv_strength_value_egg)).setText(Integer.toString(egg.strength));
      ((TextView) findViewById(R.id.tv_agility_value_egg)).setText(Integer.toString(egg.agility));
      ((TextView) findViewById(R.id.tv_firepower_value_egg)).setText(Integer.toString(egg.firepower));
      ((TextView) findViewById(R.id.tv_willpower_value_egg)).setText(Integer.toString(egg.willpower));
      ((TextView) findViewById(R.id.tv_intelligence_value_egg)).setText(Integer.toString(egg.intelligence));

      //colors egg
      ((TextView) findViewById(R.id.tv_main_color_value_egg)).setText(egg.mainColor);
      ((TextView) findViewById(R.id.tv_secondary_color_value_egg)).setText(egg.secondColor);

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
