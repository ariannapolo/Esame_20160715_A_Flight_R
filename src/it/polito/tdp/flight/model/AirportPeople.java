package it.polito.tdp.flight.model;

public class AirportPeople implements Comparable<AirportPeople> {
	private Airport airport;
	private int people;
		
	public AirportPeople(Airport airport, int people) {
		super();
		this.airport = airport;
		this.people = people;
	}
	/**
	 * @return the airport
	 */
	public Airport getAirport() {
		return airport;
	}


	/**
	 * @param airport the airport to set
	 */
	public void setAirport(Airport airport) {
		this.airport = airport;
	}

	/**
	 * @return the people
	 */
	public int getPeople() {
		return people;
	}



	/**
	 * @param people the people to set
	 */
	public void setPeople(int people) {
		this.people = people;
	}

	

	@Override
	public int compareTo(AirportPeople o) {
		return -(this.people-o.people);
	}
	
	
}
