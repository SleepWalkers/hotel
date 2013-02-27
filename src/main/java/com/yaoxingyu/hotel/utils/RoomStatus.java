package com.yaoxingyu.hotel.utils;

public enum RoomStatus {

	lived(0),

	free(1),

	booked(2);

	private int value;

	private RoomStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
