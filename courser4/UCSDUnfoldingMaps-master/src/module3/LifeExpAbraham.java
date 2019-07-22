package module3;
//Java utilities libraries
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;


import java.util.Map;




//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.providers.EsriProvider;

public class LifeExpAbraham extends PApplet{
	UnfoldingMap map;
	Map<String, Float> lifeExpByCountry;
	List<Feature> countries;
	List<Marker> countryMarkers;
	
	private Map<String, Float> loadLifeExpectancyFromCSB(String fileName){
		Map<String, Float> lifeExpMap = new HashMap<String, Float>();
		String[] rows = loadStrings(fileName);
		for (String row : rows){
			String[] columns = row.split(",");
			if(!lifeExpMap.containsKey(row)){
				float value = Float.parseFloat(columns[5]);
				lifeExpMap.put(columns[4], value);
			}
		}
		return lifeExpMap;
	}
	
	private void shadeCountries(){
		for(Marker marker : countryMarkers){
			String countryId = marker.getId();
			//if the country is in list:
			if(lifeExpByCountry.containsKey(countryId)){
				float lifeExp = lifeExpByCountry.get(countryId);
				/*map method below converts lifeExp data ranges 40-90 to 
				 color rgb ranges 10-255:*/
				int colorLevel = (int) map(lifeExp, 40, 90, 10, 255);
				/*color in rgb as an int depending on colorLevel result:
				 * low color gives you 255ish red, and else really blue.*/
				marker.setColor(color(255-colorLevel, 100, colorLevel));
			}
			else{
				marker.setColor(color(150,150,150));
			}
		}
	}
	
	public void setup(){
		size(800,600,OPENGL);
		map = new UnfoldingMap(this,50,50,700,500, new EsriProvider.WorldStreetMap());
		MapUtils.createMouseEventDispatcher(this, map);
		lifeExpByCountry = loadLifeExpectancyFromCSB("data/LifeExpectancyWorldBankModule3.csv");
		countries = GeoJSONReader.loadData(this, "data/countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		map.addMarkers(countryMarkers);
		shadeCountries();
	}
	
	public void draw(){
		map.draw();
	}
}

