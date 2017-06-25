package it.polito.tdp.flight.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento> {
	
	private LocalDateTime time;
	private Airport airportPartenza;
	private Airport airportArrivo;
	private EventType type;

	public enum EventType{
		ARRIVAL, DEPARTURE
	}

	public Evento(LocalDateTime time, Airport airportPartenza,Airport airportArrivo, EventType type) {
		super();
		this.time = time;
		this.airportPartenza = airportPartenza;
		this.airportArrivo = airportArrivo;
		this.type = type;
	}

	/**
	 * @return the time
	 */
	public LocalDateTime getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	
	
	/**
	 * @return the airportPartenza
	 */
	public Airport getAirportPartenza() {
		return airportPartenza;
	}

	/**
	 * @return the airportArrivo
	 */
	public Airport getAirportArrivo() {
		return airportArrivo;
	}

	/**
	 * @return the type
	 */
	public EventType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(EventType type) {
		this.type = type;
	}

	@Override
	public int compareTo(Evento o) {
		return this.time.compareTo(o.time);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.time+" "+this.type+" Arrivo: "+this.airportArrivo+" Partenza: "+this.airportPartenza;
	}
	
	
	

}
