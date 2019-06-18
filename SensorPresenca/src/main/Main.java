package main;

import java.util.Scanner;

import console.Console;
import sensor.Sensor;

public class Main {

	public static void main(String[] args) {
		Console console = Console.getInstance();
		Sensor sensor = new Sensor();
		while (true) {
			
			int read = console.readInt("1 - connect\n2 - send presence detection");
			
			if (read == 1) {
				//TODO if is connected continue
				sensor.setServer(console.readString("Server IP:"));
				sensor.connect();
			} else if (read == 2) {
				sensor.sendPresence();
			}
		}
	}
}
