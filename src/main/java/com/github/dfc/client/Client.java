package com.github.dfc.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException {
		DFCClient client = new DFCClient();
		InputStream in = client.reade("/tmp/johnny/11122.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = reader.readLine();
		while(line != null){
			System.out.println(line);
			line = reader.readLine();
		}
		in.close();
		reader.close();
	}
}
