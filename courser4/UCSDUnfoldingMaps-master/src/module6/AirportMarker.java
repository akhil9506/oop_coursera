package module6;

import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routes;
	
	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
		//java.util.HashMap<String, Object> properties = city.getProperties();
		
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.fill(11);
		pg.ellipse(x, y, 5, 5);
		
		
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		//Adding some info as title for our map or when user hovers em (implemented by Abraham Ferrero):
		String titleLength = getName() + ", " + getCity() + ", " + getCountry() ;
		float width = pg.textWidth(titleLength) ;
		pg.fill(255,255,204);
		pg.rect(x+6,y-15,width+7, 23);
		pg.fill(0,0,0);
		pg.text(titleLength,x+10,y);
		
		
		
	}
	
	public String getCity() {
		return (String) getProperty("city");	
		
	}
	
	public String getName() {
		return (String) getProperty("name");	
	}
	
	public String getCountry() {
		return (String) getProperty("country");	
	}
	
}
