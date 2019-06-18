package main;

import java.util.Scanner;

import sensor.Sensor;

public class Main {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		Sensor sensor = new Sensor();
		while (true) {
		
			System.out.println("1 - connect\n2 - send presence detection");
			int read = s.nextInt();
			
			if (read == 1) {
				//TODO if is connected continue
				System.out.println("Server IP:");
				s.nextLine();
				String ip = s.nextLine();
				System.out.println("ip: " + ip);
				sensor.setServer(ip);
				sensor.connect();
			} else if (read == 2) {
				sensor.sendPresence();
			}
		}
	}
}
