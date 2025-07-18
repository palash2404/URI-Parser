package uri.implementation;

import uri.Host;
import uri.Uri;

// TODO implement this class or another implementation of Uri
public class UriImplementation implements Uri {
	private final String scheme;
	private final String user;
	private final Host host;
	private final String path;
	private final String query;

	public UriImplementation(String scheme, String user, Host host, String path, String query){
		this.scheme = scheme;
		this.user = user;
		this.host = host;
		this.path= (path !=null)? path:"";
		this.query=query;
	}
	

	@Override
	public String getScheme() {
		// TODO implement this
		return scheme;
	}

	@Override
	public String getQuery() {
		// TODO implement this
		return query;
	}

	@Override
	public String getUserInfo() {
		// TODO implement this
		return user;
	}

	@Override
	public Host getHost() {
		// TODO implement this
		return host;
	}

	@Override
	public String getPath() {
		// TODO implement this
		return path;
	}

}
