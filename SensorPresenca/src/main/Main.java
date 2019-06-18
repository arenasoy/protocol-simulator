package main;

import java.util.Scanner;

import sensor.Sensor;

public class Main {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		Sensor sensor = new Sensor();
		sensor.setServer(s.nextLine());
		new Sensor().execute();
	}
}
