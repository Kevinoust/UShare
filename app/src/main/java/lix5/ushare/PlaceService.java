package lix5.ushare;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class PlaceService {
    private String API_KEY;

    public PlaceService(String apikey) {
        this.API_KEY = apikey;
    }

    public List<Place> findPlaces(double latitude, double longitude) {
        String urlString = makeUrl(latitude, longitude);
        ArrayList<Place> arrayList = new ArrayList<>();
        try {
            String json = getJSON(urlString);
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("results");
            for (int i = 0; i < array.length(); i++) {
                try {
                    Place place = Place.getNearPlaceJsonToPlace((JSONObject) array.get(i));
                    arrayList.add(place);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return arrayList;
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return arrayList;
    }

    public PlaceDistance findPlacesInfo(String start_placeID, String end_placeID) {
        String urlString = makeUrlDistanceService(start_placeID, end_placeID);
        PlaceDistance placeInfo = new PlaceDistance();
        try {
            String json = getJSON(urlString);
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("rows");
            try {
                placeInfo = PlaceDistance.getDistanceJsonToPlace((JSONObject) array.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return placeInfo;
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return placeInfo;
    }

    public String makeUrl(double latitude, double longitude) {
        StringBuilder urlString = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");

        urlString.append("&location=");
        urlString.append(Double.toString(latitude));
        urlString.append(",");
        urlString.append(Double.toString(longitude));
        urlString.append("&radius=1500");
        urlString.append("&key=" + API_KEY);
        return urlString.toString();
    }

    public String makeUrlDistanceService(String start_placeId, String end_placeID) {
        StringBuilder urlString = new StringBuilder("https://maps.googleapis.com/maps/api/distancematrix/json?units=metric");

        urlString.append("&origins=");
        urlString.append("place_id:").append(start_placeId);
        urlString.append("&destinations=");
        urlString.append("place_id:").append(end_placeID);
        urlString.append("&key=" + API_KEY);
        return urlString.toString();
    }

    protected String getJSON(String url) {
        return getUrlContents(url);
    }

    private String getUrlContents(String theUrl) {
        StringBuilder content = new StringBuilder();
        Thread a = new Thread(() -> {
            try {
                URL url = new URL(theUrl);
                URLConnection urlConnection = url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()), 8);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line + "\n");
                }
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        a.start();
        try {
            a.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
