package it.polito.tdp.flight.model;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class AirportsAndDistance {

	private Airport a1;
	private Airport a2;
	private double distance;
	
	public AirportsAndDistance(Airport a1, Airport a2) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		LatLng l1 = new LatLng(a1.getLatitude(),a1.getLongitude());
		LatLng l2 = new LatLng(a2.getLatitude(),a2.getLongitude());
		this.distance = LatLngTool.distance(l1,l2, LengthUnit.KILOMETER);
	}

	/**
	 * @return the a1
	 */
	public Airport getA1() {
		return a1;
	}

	/**
	 * @param a1 the a1 to set
	 */
	public void setA1(Airport a1) {
		this.a1 = a1;
	}

	/**
	 * @return the a2
	 */
	public Airport getA2() {
		return a2;
	}

	/**
	 * @param a2 the a2 to set
	 */
	public void setA2(Airport a2) {
		this.a2 = a2;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return a1.getAirportId()+" "+a2.getAirportId()+" "+distance;
	}
	
	
}
