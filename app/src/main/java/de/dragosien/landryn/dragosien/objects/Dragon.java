package de.dragosien.landryn.dragosien.objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Dragon implements Serializable {

   //todo creating enums for position, shortcode, colors

   public String name;
   public Gender gender;
   public String owner;
   public int strength;
   public int agility;
   public int firepower;
   public int willpower;
   public int intelligence;
   public DragballPosition position;
   public String mainColor;
   public String secondColor;

   public enum Gender {
      MALE("m", "männlich"), FEMALE("w", "weiblich");

      private String shortcode;
      private String display;

      private Gender(String shortcode, String display) {
         this.shortcode = shortcode;
         this.display = display;
      }

      public String toString() {
         return display;
      }

      public String shortCode() {
         return shortcode;
      }

      public static Gender fromString(String gender) {
         if (gender.equals("m")) return MALE;
         if (gender.equals("w")) return FEMALE;
         throw new RuntimeException("Invalid Gender: " + gender);
      }
   }

   //todo strings to strings, maybe own class?
   public enum DragballPosition {
      AA("AA", "Angriff Außen"), AM("AM", "Angriff Mitte"), MA("MA", "Mitte Außen"), VM("VM", "Verteidigung Mitte"), VA("VA", "Verteidigung Außen"), T("T", "Tor");

      private String shortcode;
      private String display;

      private DragballPosition(String shortcode, String display) {
         this.shortcode = shortcode;
         this.display = display;
      }

      public String shortCode() {
         return shortcode;
      }

      public String toString() {
         return display;
      }

      public static DragballPosition fromString(String position) {
         if (position.equals("AA")) return AA;
         if (position.equals("AM")) return AM;
         if (position.equals("MA")) return MA;
         if (position.equals("VA")) return VA;
         if (position.equals("VM")) return VM;
         if (position.equals("T")) return T;
         throw new RuntimeException("Invalid Dragball Position: " + position);
      }
   }

   //todo make constants (in PartnerSeachResponse?)
   public static Dragon fromPartnerSearchResponse(PartnerSearchResponse response) {
      Dragon dragon = new Dragon();
      try {
         JSONObject jsonObject = new JSONObject(response.json);
         dragon.name = jsonObject.getString("name");
         dragon.strength = jsonObject.getInt("kraft");
         dragon.agility = jsonObject.getInt("geschick");
         dragon.firepower = jsonObject.getInt("feuerkraft");
         dragon.willpower = jsonObject.getInt("willenskraft");
         dragon.intelligence = jsonObject.getInt("intelligenz");
         dragon.mainColor = jsonObject.getString("hf");
         dragon.secondColor = jsonObject.getString("nf");
         dragon.owner = jsonObject.getString("besitzer");
      } catch (JSONException e) {
         //todo error handling
      }
      return dragon;
   }

   //todo constants
   public static Dragon getEggfromPartnerSearchResponse(PartnerSearchResponse response) {
      Dragon egg = new Dragon();
      try {
         JSONObject jsonObject = new JSONObject(response.json);egg.name = jsonObject.getString("name");
         egg.strength = jsonObject.getInt("ei_kraft");
         egg.agility = jsonObject.getInt("ei_geschick");
         egg.firepower = jsonObject.getInt("ei_feuerkraft");
         egg.willpower = jsonObject.getInt("ei_willenskraft");
         egg.intelligence = jsonObject.getInt("ei_intelligenz");
         egg.mainColor = jsonObject.getString("ei_hf");
         egg.secondColor = jsonObject.getString("ei_nf");
      } catch (JSONException e) {
         //todo error handling
      }
      return egg;
   }
}