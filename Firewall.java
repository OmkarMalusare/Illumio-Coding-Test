import java.util.*;
import java.io.*;

public class Firewall {

	private String path;

	// Constructor
	public Firewall(String path) {

		this.path = path;
	}

	/**
	 * To check whether the packet is accepted or not
	 * 
	 * @param direction
	 * @param protocol
	 * @param port
	 * @param ip_address
	 * @return boolean
	 */
	public boolean accept_packet(String direction, String protocol, int port, String ip_address) {
        
		HashSet<String> allowedDirections = new HashSet<String>();
		allowedDirections.add("inbound");
		allowedDirections.add("outbound");

		HashSet<String> allowedProtocol = new HashSet<String>();
		allowedProtocol.add("tcp");
		allowedProtocol.add("udp");

		return (allowedDirections.contains(direction) && allowedProtocol.contains(protocol) && checkvalidPort(port)
				&& checkIP(ip_address));
	}

	/**
	 * To check if a port is valid
	 * 
	 * @param port
	 * @return boolean
	 */
	public boolean checkvalidPort(int port) {

		// when port is a range of integers i.e String
		/*
		 * if (port.contains("-")) { String[] splittedPorts = port.split("-");
		 * int startPort = Integer.parseInt(splittedPorts[0]); int endPort =
		 * Integer.parseInt(splittedPorts[1]); return ((startPort >= 1 &&
		 * startPort <= 65535) && (endPort >= 1 && endPort <= 65535) &&
		 * startPort < endPort); }
		 */

		// when port is an integer
		return (port >= 1 && port <= 65535);
	}

	/**
	 * @param ip_address
	 * @return boolean
	 */
	public boolean checkIP(String ip_address) {
		// when there is a range of ip addresses
		if (ip_address.contains("-")) {
			String[] splitIP = ip_address.split("-");
			String startPort = splitIP[0];
			String endPort = splitIP[1];
			return (validate(startPort) && validate(endPort) && compareIP(startPort, endPort));
		}
		// when there is a single ip address
		return validate(ip_address);
	}

	/**
	 * To check the validity of an ip_address
	 * 
	 * @param ip_address
	 * @return boolean
	 */
	public boolean validate(String ip_address) {

		if (ip_address.length() == 0 || ip_address == null)
			return false;
		// to check if ip_address contains a dot at the end
		if (ip_address.charAt(ip_address.length() - 1) == '.')
			return false;

		String[] groups = ip_address.split("\\.");
		// To check if ip_address has 4 octets
		if (groups.length != 4)
			return false;
		for (String str : groups) {
			int value = Integer.parseInt(str);
			if ((value < 0) || (value > 255)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * To compare the two IP addresses
	 * 
	 * @param startPort
	 * @param endPort
	 * @return boolean
	 */
	public boolean compareIP(String startPort, String endPort) {

		String[] start = startPort.split("\\.");
		String[] end = endPort.split("\\.");
		for (int i = 0; i < start.length; i++) {
			int startInt = Integer.parseInt(start[i]);
			int endInt = Integer.parseInt(end[i]);
			if (startInt == endInt)
				continue;
			if (startInt < endInt)
				return true;
			return false;
		}
		// when the ip addresses will be exactly same
		return false;

	}

	/**
	 * This is the main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Firewall fw = new Firewall("/path/to/fw.csv");
		// Looked up the code to parse a file line by line on the internet
		try {
			BufferedReader br = new BufferedReader(new FileReader("/path/to/fw.csv"));

			String line = null;
			while ((line = br.readLine()) != null) {
				//split each line of the csv file by "," and further store the splitted values in an array
				String[] eachLine = line.split(",");
				String direction = eachLine[0];
				String protocol = eachLine[1];
				int port = Integer.parseInt(eachLine[2]);
				String ip_address = eachLine[3];
				//to determine the fate of the packet
				if (fw.accept_packet(direction, protocol, port, ip_address))
					System.out.println("Accepted");
				else
					System.out.println("Blocked");
			}

			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
