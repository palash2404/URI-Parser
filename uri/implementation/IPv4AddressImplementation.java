package uri.implementation;

import uri.IPv4Address;

// TODO implement this class or another Implementation of IPv4Address
public class IPv4AddressImplementation extends HostImplementation implements IPv4Address {

	private final byte[] octets;

	public IPv4AddressImplementation(String host) {
		super(host);


		if (host == null || host.matches(".*\\s+.*")) {
        throw new IllegalArgumentException("Invalid IPv4: contains whitespace");
    }

		String[] parts = host.split("\\.");
		if(parts.length!=4){
			throw new IllegalArgumentException("Invalid IPv4");
		}
		octets = new byte[4];

		for (int i =0 ; i <4; i++){
			String part = parts[i];

        if (!part.matches("\\d+")) {
            throw new IllegalArgumentException("Invalid IPv4: non-decimal characters");
        }
			try {
				int val = Integer.parseInt(part);
				if(val <0 || val >255){
					throw new IllegalArgumentException("Invalid IPv4");
				}
				octets[i]=(byte) val;
			}catch (NumberFormatException e){
				throw new IllegalArgumentException("Invalid IPv4");
			}

		}
		// TODO implement this
		
	}

	@Override
	public byte[] getOctets() {
		// TODO implement this
		return octets.clone();
	}

	@Override
	public String toString() {
		// TODO implement this
		return (octets[0] & 0xFF)+ "." + (octets[1] & 0xFF)+ "." + (octets[2] & 0xFF)+ "." + (octets[3] & 0xFF);
	}

}
