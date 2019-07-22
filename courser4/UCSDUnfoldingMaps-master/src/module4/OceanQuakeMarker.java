package module4;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

/** Implements a visual marker for ocean earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Abraham Ferrero
 *
 */
public class OceanQuakeMarker extends EarthquakeMarker {
	
	public OceanQuakeMarker(PointFeature quake) {
		super(quake);
		
		// setting field in earthquake marker
		isOnLand = false;
	}
	
	int drawing = 0;
	@Override
	public void drawEarthquake(PGraphics pg, float x, float y) {
		// Drawing a centered square for Ocean earthquakes
		// DO NOT set the fill color.  That will be set in the EarthquakeMarker
		// class to indicate the depth of the earthquake.
		// Simply draw a centered square.
		
		// HINT: Notice the radius variable in the EarthquakeMarker class
		// and how it is set in the EarthquakeMarker constructor
		
		if(getMagnitude() < 5.5 && getMagnitude() >= 5 ) {
			pg.rect(x,y,10,10);
			}
			if(getMagnitude() < 5) {
				pg.rect(x,y,7,7);
				}

			if(getMagnitude() >=5.5) {
				pg.rect(x,y,13,13);
			}
		// TODO: Implement this method
		
	}
	


	

}
