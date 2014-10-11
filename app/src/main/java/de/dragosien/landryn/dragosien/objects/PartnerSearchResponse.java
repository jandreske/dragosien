package de.dragosien.landryn.dragosien.objects;

public class PartnerSearchResponse {

   //todo parse egg and partner from json
   public Dragon dragon;

   //todo remove, only for testing
   public String json;

   public static PartnerSearchResponse fromJsonString(String jsonstring)  {
      PartnerSearchResponse response = new PartnerSearchResponse();
      response.json = jsonstring;
      return response;
   }

}
