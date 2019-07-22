package module6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.geo.Location;
import parsing.ParseFeed;
import processing.core.PApplet;

/** An applet that shows airports (and routes)
 * on a world map.  
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMap extends PApplet {
	
	UnfoldingMap map;
	private List<Marker> airportList1;
	private List<Marker> airportList2;
	List<Marker> routeList;
	private CommonMarker lastSelected;
	private CommonMarker lastClicked;
	
	public void setup() {
		// setting up PAppler
		size(800,600, OPENGL);
		
		// setting up map and default events
		map = new UnfoldingMap(this, 50, 50, 750, 550);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");
		
		// list for markers, hashmap for quicker access when matching with routes
		airportList1 = new ArrayList<Marker>();
		airportList2 = new ArrayList<Marker>();
		HashMap<Integer, Location> airports = new HashMap<Integer, Location>();
		
		// create markers from features
		for(PointFeature feature : features) {
			String country = (String)feature.getProperty("country");
			//SHOWING ONLY AIRPORTS, ROUTES AND FLIGHTS IN SPAIN
			if(country.contains("Spain")) {
			AirportMarker m = new AirportMarker(feature);
			
			m.setRadius(5);
			airportList1.add(m);
			
			// put airport in hashmap with OpenFlights unique id for key
			airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
			}
		
		}
		
		for(PointFeature feature : features) {
			String country = (String)feature.getProperty("country");
			//SHOWING ONLY AIRPORTS, ROUTES AND FLIGHTS IN SPAIN
			if(country.contains("Spain")) {
			AirportMarker m = new AirportMarker(feature);
			m.setHidden(true);
			m.setRadius(5);
			airportList2.add(m);
			
			// put airport in hashmap with OpenFlights unique id for key
			airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
			}
		
		}
		
		
		// parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		for(ShapeFeature route : routes) {
			
			// get source and destination airportIds
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			
			// get locations for airports on route
			if(airports.containsKey(source) && airports.containsKey(dest)) {
				route.addLocation(airports.get(source));
				route.addLocation(airports.get(dest));
			}
			
			SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());
			//Hidden by default unless clicked:
			sl.setHidden(true);
			
			//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
			routeList.add(sl);
		}
		
		
		
		//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		map.addMarkers(routeList);
		map.addMarkers(airportList1);
		map.addMarkers(airportList2);
		
	}
	
	public void draw() {
		background(0);
		map.draw();
		
	}
	@Override
	public void mouseMoved()
	{
		// clear the last selection
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		
		}
		selectMarkerIfHover(airportList1);
		//loop();
	}
	
	// If there is a marker selected 
	private void selectMarkerIfHover(List<Marker> markers)
	{
		// Abort if there's already a marker selected
		if (lastSelected != null) {
			return;
		}
		
		for (Marker m : markers) 
		{
			CommonMarker marker = (CommonMarker)m;
			if (marker.isInside(map,  mouseX, mouseY)) {
				lastSelected = marker;
				marker.setSelected(true);
				return;
			}
		}
	}
	
	public void mouseClicked() {
		if (lastClicked != null) {
			unhideAirports();
			hideRoutes();
			hideMarker2();
			lastClicked = null;
		}
		else if (lastClicked == null) 
		{
			checkAirportsForClick();
			
		}
			}
	
	private void unhideAirports() {
		for(Marker marker : airportList1) {
			marker.setHidden(false);
		}
	}
	
	private void hideRoutes() {
		for(Marker m : routeList) {
			m.setHidden(true);
		}
	}
	
	private void hideMarker2() {
		for(Marker marker2 : airportList2) {
			marker2.setHidden(true);
		}
	}
	
	private void checkAirportsForClick() {
		if (lastClicked != null) return;
		//When something clicked:
		for(Marker marker : airportList1) {
			for(Marker m : routeList) {
				String arrRoute = (String)m.getProperty("arrival");
				String depRoute = (String)m.getProperty("departure");
				String AirCode = (String)marker.getProperty("code");
				//for the airport selected
				if (!marker.isHidden() && marker.isInside(map, mouseX, mouseY)) {
					if(AirCode.contains(arrRoute)) {
					 		//Si el aeropuerto es el destino, muestra ruta
							m.setHidden(false);
							for(Marker marker2 : airportList2) {
								String arrRoute2 = (String)marker2.getProperty("code");
								if(arrRoute2.contains(depRoute)) {
									marker2.setHidden(false);
								}
							}
						}
					 	
					}
				//for the rest of the map:
					else {
						
						lastClicked = (AirportMarker)marker;
						marker.setHidden(true);
						}
					}
			}
		}
	
}
