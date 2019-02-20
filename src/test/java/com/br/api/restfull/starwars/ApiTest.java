package com.br.api.restfull.starwars;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ApiTest {

	public static void main(String[] args) throws UnirestException, JSONException {
		
		//https://swapi.co/api/planets/?search=
		
            String planetaApi = Unirest.get("https://swapi.co/api/planets/?search={name}")
            		.routeParam("name", "Naboo")
                    .asJson()
                    .getBody()
                    .toString();
            
            JSONObject devMovies = new JSONObject(planetaApi);
            
            String nextPag = devMovies.get("next").toString();
            
            String count = devMovies.get("count").toString();
            
            JSONArray arrFilmes = devMovies.getJSONArray("results");
            
            //JSONObject result = devMovies.getJSONObject("results");
            
            //JSONArray arrFilmes = devMovies.getJSONArray("films");
            
            for (int i=0; i < arrFilmes.length(); i++) {
                
                JSONObject f = arrFilmes.getJSONObject(i);
                
                JSONArray fms = f.getJSONArray("films");
                 
                //System.out.println("id: " + f.getInt("id"));
                System.out.println("name: " + f.getString("name"));
                System.out.println("climate: " + f.getString("climate"));
                System.out.println("terrain: " + f.getString("terrain"));
            }
            
	}

}
