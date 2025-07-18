
package uri.implementation;

import uri.Uri;
import uri.UriParser;
import uri.Host;
import uri.IPv4Address;

// TODO implement this class or another implementation of UriParser
public class UriParserImplementation implements UriParser {
	private final String input;

	public UriParserImplementation(String input){
		this.input = input;
	}

	@Override
	public Uri parse() {
		// TODO implement this
		//get Scheme
		int schemeEnd = input.indexOf("://");
		if(schemeEnd==-1){
			return null;
		}
		String scheme = input.substring(0, schemeEnd);
		if (scheme.isEmpty() || !Character.isLetter(scheme.charAt(0)) || !scheme.matches("[a-zA-Z][a-zA-Z0-9]*")) {
            return null;
        }

		String rest = input.substring(schemeEnd+3);
		//Get Authority
		int Path = rest.indexOf('/');
		int Query= rest.indexOf('?');

		int end = rest.length();
		if(Path !=-1&&(Query!=-1 || Path<Query)){
			end= Path;

		}
		else if (Query !=-1){
			end = Query;
		}
		String auth = rest.substring(0, end);
		String PathQuery= rest.substring(end);
        //Get user and host

		String userInfo = null;
		
		String HostString;

		int Index = auth.indexOf('@');
		if(Index != -1){
			userInfo = auth.substring(0, Index);

			if (userInfo != null) {
            if (userInfo.startsWith(":")) return null;
            if (userInfo.contains(" ")) return null;
            if (!isValidUserInfo(userInfo)) return null;

        }
			

	HostString = auth.substring(Index+1);
			
		}else{
			HostString = auth;
		}

		//get path and query
		String path = null;
		String query =null;

		int Mark = PathQuery.indexOf('?');
		if(Mark!=-1){
			path = PathQuery.substring(0, Mark);
			query = PathQuery.substring( Mark + 1);
			
		}else if(!PathQuery.isEmpty()){
			path = PathQuery;
		}
		if(path == null) path = "";
		if (query != null && !isValidQuery(query)) return null;

		//Host oj
        if (HostString == null /*|| HostString.isEmpty()*/) return null;
		Host hostobj = null;
		if (!HostString.isEmpty()) {
    try {
        if (HostString.matches("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$")) {
            hostobj = new IPv4AddressImplementation(HostString);
        } else {
            hostobj = new HostImplementation(HostString);
        }
    } catch (IllegalArgumentException e) {
        return null;
    }
}


		return new UriImplementation(scheme, userInfo, hostobj, path, query);
	}

private boolean isValidUserInfo(String userInfo) {
    int len = userInfo.length();
    for (int i = 0; i < len; i++) {
        char c = userInfo.charAt(i);
        if (Character.isLetterOrDigit(c) ||
            c == '-' || c == '.' || c == '_' || c == '~' ||
            c == '!' || c == '$' || c == '&' || c == '\'' || c == '(' ||
            c == ')' || c == '*' || c == '+' || c == ',' || c == ';' ||
            c == '=' || c == ':') {
            continue;
        }
        if (c == '%') {
            if (i + 2 >= len) return false;
            if (!isHexDigit(userInfo.charAt(i + 1)) || !isHexDigit(userInfo.charAt(i + 2))) return false;
            i += 2;
        } else {
            return false;
        }
    }
    return true;
}

	private boolean isValidQuery(String query) {
		int len = query.length();
		for (int i = 0; i < len; i++) {
			char c = query.charAt(i);
			if (Character.isLetterOrDigit(c) || c == '.' || c == '&' || c == '=') {
				continue;
			}
			if (c == '%') {
				if (i + 2 >= len) return false;
				if (!isHexDigit(query.charAt(i + 1)) || !isHexDigit(query.charAt(i + 2))) return false;
				i += 2;
			} else {
				return false;
			}
		}
		return true;
	}

	private boolean isHexDigit(char c) {
		return (c >= '0' && c <= '9') ||
		       (c >= 'A' && c <= 'F') ||
		       (c >= 'a' && c <= 'f');
	}

}