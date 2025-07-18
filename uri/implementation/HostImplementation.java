package uri.implementation;

import uri.Host;

// TODO implement this class or another implementation of Host
public class HostImplementation implements Host {
	public final String host;

	public HostImplementation(String host) {
		// TODO implement this
		if (!isValidRegName(host)) {
			throw new IllegalArgumentException("Invalid reg-name host");
		}
		this.host = host;
	}

	@Override
	public String toString() {
		// TODO implement this

		return decodePercentEncoded(host);

	}
	private String decodePercentEncoded(String input) {
		StringBuilder decoded = new StringBuilder();
		for (int i = 0; i < input.length(); ) {
			char c = input.charAt(i);
			if (c == '%' && i + 2 < input.length()) {
				String hex = input.substring(i + 1, i + 3);
				try {
					int val = Integer.parseInt(hex, 16);
					char decodedChar = (char) val;
					if (decodedChar == '/' || decodedChar == '\\') return null; // disallow slashes
					decoded.append(decodedChar);
					i += 3;
				} catch (NumberFormatException e) {
					return null;
				}
			} else {
				decoded.append(c);
				i++;
			}
		}
		return decoded.toString();
	}

	private boolean isValidRegName(String host) {
		String decoded = decodePercentEncoded(host);
		if (decoded == null || decoded.isEmpty()) return false;

		String[] labels = decoded.split("\\.");
		for (String label : labels) {
			if (label.isEmpty()) return false; // prevents double dots
			if (!label.matches("[a-zA-Z0-9-]+")) return false;
			if (label.startsWith("-") || label.endsWith("-")) return false;
			if (label.contains("_")) return false;
		}
		return true;
	}

}
