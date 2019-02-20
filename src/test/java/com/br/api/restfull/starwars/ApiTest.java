package com.br.api.restfull.starwars;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ApiTest {

	public static void main(String[] args) throws UnirestException, JSONException {
		
		listarPlanetaApiSwapi();
            
	}
	
	private static void listarPlanetaApiSwapi(){
		
		String urlApiSwapi = "https://swapi.co/api/planets/";
		
		boolean hasNextPage = true;
		try {
			
			while(hasNextPage){
				
				String planetasApi = Unirest.get(urlApiSwapi)
				        .asJson()
				        .getBody()
				        .toString();
				
				JSONObject planetaJson = new JSONObject(planetasApi);
				
				urlApiSwapi = planetaJson.get("next").toString();
				
				hasNextPage = hasMorePage(urlApiSwapi);
				
				JSONArray results = planetaJson.getJSONArray("results");
				
				for(int i = 0; i < results.length(); i++ ){
					JSONObject resultJsonObject = results.getJSONObject(i);
					
					System.out.println("name: " + i + " - "+  resultJsonObject.getString("name"));
				}
			}
			
		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static boolean hasMorePage(String nextPag){
		return nextPag.isEmpty() ? false : true;
	}

}
