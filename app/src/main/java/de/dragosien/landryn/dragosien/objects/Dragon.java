package de.dragosien.landryn.dragosien.objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Dragon implements Serializable {

   //todo creating enums for position, gender, colors

   public String name;
   public String gender;
   public String owner;

   public int strength;
   public int agility;
   public int firepower;
   public int willpower;
   public int intelligence;

   public String position;

   public String mainColor;
   public String secondColor;

   //todomake constants (in PartnerSeachResponse?)
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
}