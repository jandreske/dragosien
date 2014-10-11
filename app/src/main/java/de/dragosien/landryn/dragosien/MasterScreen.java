package de.dragosien.landryn.dragosien;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import de.dragosien.landryn.dragosien.fragments.DragballTableFragment;
import de.dragosien.landryn.dragosien.fragments.LiveTickerFragment;
import de.dragosien.landryn.dragosien.fragments.SearchPartnerFragment;

public class MasterScreen extends ActionBarActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      ViewPager masterViewPager = new ViewPager(this);
      masterViewPager.setId(R.id.pager);
      setContentView(masterViewPager);
      ActionBar bar = getSupportActionBar();
      bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
      bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
      TabsAdapter tabsAdapter = new TabsAdapter(this, masterViewPager);

      //add all the nice tabs
      tabsAdapter.addTab(bar.newTab().setText(getString(R.string.tab_partners)), SearchPartnerFragment.class, null);
      tabsAdapter.addTab(bar.newTab().setText(getString(R.string.tab_tables)), DragballTableFragment.class, null);
      tabsAdapter.addTab(bar.newTab().setText(getString(R.string.tab_ticker)), LiveTickerFragment.class, null);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.master_screen, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      // handling of actionbar item clicks
      int id = item.getItemId();
      if (id == R.id.action_settings) {
         return true;
      }
      return super.onOptionsItemSelected(item);
   }
}
