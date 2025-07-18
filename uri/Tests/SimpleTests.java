package uri.Tests;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import uri.Uri;
import uri.Host;
import uri.IPv4Address;
import uri.UriParserFactory;

/**
 * This class provides a very simple example of how to write tests for this project.
 * You can implement your own tests within this class or any other class within this package.
 * Tests in other packages will not be run and considered for completion of the project.
 */
public class SimpleTests {

	/**
	 * Helper function to determine if the given host is an instance of {@link IPv4Address}.
	 *
	 * @param host the host
	 * @return {@code true} if the host is an instance of {@link IPv4Address}
	 */
	public boolean isIPv4Address(Host host) {
		return host instanceof IPv4Address;
	}

	/**
	 * Helper function to retrieve the byte array representation of a given host which must be an instance of
	 * {@link IPv4Address}.
	 *
	 * @param host the host
	 * @return the byte array representation of the IPv4 address
	 */
	public byte[] getIPv4Octets(Host host) {
		if (!isIPv4Address(host))
			throw new IllegalArgumentException("host must be an IPv4 address");
		return ((IPv4Address) host).getOctets();
	}

	

	@Test
	public void testIPv4AddressSimple() {
		Host host = UriParserFactory.create("scheme://1.2.3.4").parse().getHost();
		assertTrue("host must be an IPv4 address", isIPv4Address(host));
	}


	// Basic URI Parsing Tests
	


	@Test
	public void testIncompleteScheme() {
		assertNull("Incomplete scheme should fail to parse", UriParserFactory.create("s").parse());
	}

	@Test
	public void testMissingScheme() {
		assertNull(UriParserFactory.create("//host/path").parse());
	}

	@Test
	public void testNoColon() {
		assertNull(UriParserFactory.create("scheme").parse());
	}

	// Scheme Tests
	@Test
	public void testSimpleScheme() {
		Uri uri4 = UriParserFactory.create("http://host.com").parse();
		assertNotNull("Valid URI should parse", uri4);
		assertEquals("Scheme should be http", "http", uri4.getScheme());
	}

	@Test
	public void testAlphanumericScheme() {
		Uri uri = UriParserFactory.create("x123://host.com").parse();
		assertNotNull("Scheme with letters and digits should parse", uri);
		assertEquals("Scheme should be 'x123'", "x123", uri.getScheme());
	}

	@Test
	public void testSingleLetterScheme() {
		Uri uri = UriParserFactory.create("h://host.com").parse();
		assertNotNull("Single-letter scheme should be valid", uri);
		assertEquals("Scheme should be 'h'", "h", uri.getScheme());
	}

	@Test
	public void testUppercaseScheme() {
		Uri uri = UriParserFactory.create("scheme://host.com").parse();
		assertNotNull("Uppercase scheme should parse", uri);
		assertEquals("Scheme should be 'scheme'", "scheme", uri.getScheme());
	}

	@Test
	public void testSchemeEndingInDigit() {
		Uri uri = UriParserFactory.create("ftp1://host.com").parse();
		assertNotNull("Scheme ending with digit should be valid", uri);
		assertEquals("Scheme should be 'ftp1'", "ftp1", uri.getScheme());
	}

	@Test
	public void testVeryLongScheme() {
		Uri uri = UriParserFactory.create("schemeloooooooooooooooooooooooong://host.com").parse();
		assertNotNull("Very long scheme should be valid", uri);
		assertEquals("Scheme should match", "schemeloooooooooooooooooooooooong", uri.getScheme());
	}

	@Test
	public void testSchemeStartingWithDigit() {
		Uri uri = UriParserFactory.create("9http://host.com").parse();
		assertNull("Scheme starting with a digit should be invalid", uri);
	}

	@Test
	public void testSchemeWithSpecialChar() {
		Uri uri = UriParserFactory.create("ht+tp://host.com").parse();
		assertNull("Scheme with '+' is invalid", uri);
	}

	@Test
	public void testSchemeMissingColon() {
		Uri uri = UriParserFactory.create("http//host.com").parse();
		assertNull("Scheme missing colon should be invalid", uri);
	}

	@Test
	public void testEmptyScheme() {
		Uri uri = UriParserFactory.create("://host.com").parse();
		assertNull("Empty scheme should be invalid", uri);
	}

	@Test
	public void testSchemeWithSpace() {
		Uri uri = UriParserFactory.create("ht tp://host.com").parse();
		assertNull("Scheme with space should be invalid", uri);
	}

	// UserInfo Tests

 @Test
public void userInfoOOne() {
assertNull(UriParserFactory.create("scheme://:password@example.com").parse());
}

@Test
public void userInfoTwo() {
Uri two = UriParserFactory.create("scheme://info-_~.:pass@example.com").parse();
assertEquals("info-_~.:pass", two.getUserInfo());
}

@Test
public void three() {
Uri three = UriParserFactory.create("scheme://info%20name:pass@example.com").parse();
assertEquals("info%20name:pass", three.getUserInfo());
}

@Test
public void four() {
assertNull(UriParserFactory.create("scheme://info name:pass@example.com").parse());
}

@Test
public void userInfofive() {
Uri five = UriParserFactory.create("scheme://info:pass:word@example.com").parse();
assertEquals("info:pass:word", five.getUserInfo());
}

@Test
public void hasUserInfoWithPassword() {
Uri six = UriParserFactory.create("scheme://info:pass@youtube.com/watch?v=dQw4w9WgXcQ").parse();
assertEquals("info:pass", six.getUserInfo());
}
@Test
public void testUserInfoWithEncodedAtSign() {
    Uri uri = UriParserFactory.create("scheme://user%40name@host.com").parse();
    assertNotNull("Userinfo with percent-encoded @ should be valid", uri);
    assertEquals("user%40name", uri.getUserInfo());
}
@Test
public void testUserInfoAllPercentEncoded() {
    Uri uri = UriParserFactory.create("scheme://%75%73%65%72@host.com").parse();
    assertNotNull("Fully percent-encoded userinfo should parse", uri);
    assertEquals("%75%73%65%72", uri.getUserInfo()); // "user" when decoded
}
@Test
public void testUserInfoWithAllowedSpecialChars() {
    Uri uri = UriParserFactory.create("scheme://!$&'()*+,;=user@host.com").parse();
    assertNotNull("Userinfo with allowed special chars should parse", uri);
    assertEquals("!$&'()*+,;=user", uri.getUserInfo());
}
@Test
public void testUserInfoWithEncodedSpace() {
    Uri uri = UriParserFactory.create("scheme://user%20name@host.com").parse();
    assertNotNull("Userinfo with encoded space should parse", uri);
    assertEquals("user%20name", uri.getUserInfo());
}
@Test
public void testUserInfoWithTrailingColon() {
    Uri uri = UriParserFactory.create("scheme://user:@host.com").parse();
    assertNotNull("Trailing colon in userinfo should be valid", uri);
    assertEquals("user:", uri.getUserInfo());
}

@Test
public void testVeryLongUserInfo() {
    String longUser = "user" + "x".repeat(1000);
    Uri uri = UriParserFactory.create("scheme://" + longUser + "@host.com").parse();
    assertNotNull("Very long userinfo should parse", uri);
    assertEquals(longUser, uri.getUserInfo());
}


	@Test
	public void testSimpleUserInfo() {
		Uri user = UriParserFactory.create("scheme://alice@host.com").parse();
		assertNotNull("Uri with simple userinfo should parse", user);
		assertEquals("userinfo wrong","alice", user.getUserInfo());
	}

	@Test
	public void testUserInfoWithColon() {
		Uri userColon = UriParserFactory.create("scheme://bob:secret@host.com").parse();
		assertNotNull(userColon);
		assertEquals("User with colon is valid", "bob:secret", userColon.getUserInfo());
	}

	@Test
	public void testEmptyUserInfo() {
		Uri empty = UriParserFactory.create("scheme://@host.com").parse();
		assertNotNull("URI with empty userinfo should parse", empty);
		assertEquals("Empty userinfo should return empty string", "", empty.getUserInfo());
	}



	@Test
	public void testUserInfoMultipleColons() {
		Uri multi = UriParserFactory.create("scheme://a:b:c@host.com").parse();
		assertNotNull("URI with multiple colons in userinfo should parse", multi);
		assertEquals("Userinfo should contain all colons", "a:b:c", multi.getUserInfo());
	}

	@Test
	public void testLongUserInfo() {
		Uri longy = UriParserFactory.create("scheme://verylonguserinfooooooothatisstillvalid@host.com").parse();
		assertNotNull("Very long but valid userinfo should be parsed", longy);
		assertEquals("Userinfo should match input", "verylonguserinfooooooothatisstillvalid", longy.getUserInfo());
	}

	

	@Test
	public void testUserInfoWithInvalidChar() {
		Uri chara = UriParserFactory.create("scheme://user^name@host.com").parse();
		assertNull("Userinfo with invalid character should be rejected", chara);
	}
	


	@Test
	public void testUserInfoEndsWithPercent() {
		Uri uri = UriParserFactory.create("scheme://user%@host.com").parse();
		assertNull("Userinfo ending with '%' should be invalid", uri);
	}

	@Test
	public void testUserInfoWithSpace() {
		Uri uri = UriParserFactory.create("scheme://user name@host.com").parse();
		assertNull("Userinfo with space should be invalid", uri);
	}

	@Test
	public void testUserInfoWithNewline() {
		Uri uri = UriParserFactory.create("scheme://user\nname@host.com").parse();
		assertNull("Userinfo with newline should be invalid", uri);
	}

	@Test
	public void testUserInfoWithTab() {
		Uri uri = UriParserFactory.create("scheme://user\tname@host.com").parse();
		assertNull("Userinfo with tab should be invalid", uri);
	}

	@Test
	public void testDoubleAt() {
		Uri doubly = UriParserFactory.create("scheme://user@@host.com").parse();
		assertNull("Double @ should make URI invalid", doubly);
	}

	// Host Tests - IPv4
	/*@Test
	public void testIPv4AddressSimple() {
		Host host = UriParserFactory.create("scheme://1.2.3.4").parse().getHost();
		assertTrue("host must be an IPv4 address", isIPv4Address(host));
	}*/

@Test
public void Test0001(){
	Uri zero = UriParserFactory.create("scheme://user@001.001.001.001/path?x=1").parse();

	assertEquals("ouput should be 1.1.1.1", "1.1.1.1", zero.getHost().toString());


}
@Test 
public void Test010(){
	Uri one = UriParserFactory.create("scheme://user@178.010.012.244").parse();

	assertEquals("Should be 178.10.12.244", "178.10.12.244", one.getHost().toString());
}
@Test
public void Test027(){
	Uri two = UriParserFactory.create("scheme://user@020.034.076.088").parse();

	assertEquals("20.34.76.88", two.getHost().toString());
}
@Test
public void TestThree(){
	Uri three = UriParserFactory.create("scheme://user@120.134.100.122").parse();

	assertEquals("120.134.100.122", three.getHost().toString());
}

@Test
public void Testfour(){
	Uri four = UriParserFactory.create("scheme://222.222.222.222").parse();

	assertEquals("222.222.222.222", four.getHost().toString());
}
@Test
public void ipAddStartsWithRangeTwoFourBroken() {
assertNull( UriParserFactory.create("scheme://277.234.140.200").parse());
}

@Test
public void TestSix(){
	Uri six = UriParserFactory.create("scheme://250.234.254.255").parse();

	assertEquals("250.234.254.255", six.getHost().toString());
}
@Test
public void Seven() {
assertNull(UriParserFactory.create("scheme://257.215.245.155").parse());
}

@Test
public void Eight(){
	Uri eight = UriParserFactory.create("scheme://0.0.0.0").parse();
	assertEquals("error", "0.0.0.0", eight.getHost().toString());
}


@Test
public void TestIPv4(){
	Uri IPv4 = UriParserFactory.create("scheme://127.0.0.1/path?x=1").parse();

	assertNotNull("Should be valid IPv4",IPv4.getHost());
	assertNotNull("should have a valid query",IPv4.getQuery());
}
@Test
public void TestUser_IPv4(){
	Uri US_IPv4 = UriParserFactory.create("scheme://user@192.168.0.1").parse();

    assertEquals("user", US_IPv4.getUserInfo());
    assertEquals("192.168.0.1", US_IPv4.getHost().toString());
}

@Test
public void testIPv4WithLeadingZeros2() {
    Uri uri = UriParserFactory.create("scheme://192.168.001.001").parse();
    assertNotNull("IPv4 with leading zeros should be valid", uri);
    assertEquals("192.168.1.1", uri.getHost().toString()); // assuming parser normalizes
}

	@Test
public void testIPv4AllZeros() {
    Uri uri = UriParserFactory.create("scheme://0.0.0.0").parse();
    assertNotNull("IPv4 all zeros should be valid", uri);
    assertEquals("0.0.0.0", uri.getHost().toString());
}

@Test
public void testIPv4BroadcastAddress() {
    Uri uri = UriParserFactory.create("scheme://255.255.255.255").parse();
    assertNotNull("Broadcast address should be valid", uri);
    assertEquals("255.255.255.255", uri.getHost().toString());
}

@Test
public void testIPv4FirstOctetZero() {
    Uri uri = UriParserFactory.create("scheme://0.1.2.3").parse();
    assertNotNull("IPv4 with first octet zero should be valid", uri);
    assertEquals("0.1.2.3", uri.getHost().toString());
}

@Test
public void testIPv4LastOctetZero() {
    Uri uri = UriParserFactory.create("scheme://192.168.1.0").parse();
    assertNotNull("IPv4 ending in .0 should be valid", uri);
    assertEquals("192.168.1.0", uri.getHost().toString());
}

@Test
public void testIPv4MiddleOctetZero() {
    Uri uri = UriParserFactory.create("scheme://192.0.1.1").parse();
    assertNotNull("IPv4 with middle octet 0 should be valid", uri);
    assertEquals("192.0.1.1", uri.getHost().toString());
}

@Test
public void testIPv4WithLeadingAndTrailingSpaces() {
    Uri uri = UriParserFactory.create("scheme:// 192.168.1.1").parse();
    assertNull("IPv4 with leading/trailing spaces should be invalid", uri);
}

@Test
public void testIPv4WithTabs() {
    Uri uri = UriParserFactory.create("scheme://192.168.\t1.1").parse();
    assertNull("IPv4 with tab in address should be invalid", uri);
}

@Test
public void testIPv4WithNewline() {
    Uri uri = UriParserFactory.create("scheme://192.168.1\n.1").parse();
    assertNull("IPv4 with newline in address should be invalid", uri);
}

@Test
public void testIPv4WithEmbeddedSpaces() {
    Uri uri = UriParserFactory.create("scheme://192. 168.1.1").parse();
    assertNull("IPv4 with internal space should be invalid", uri);
}

  @Test
    public void testIPv4OutOfRange() {
        Uri uri = UriParserFactory.create("scheme://256.100.100.100").parse();
        assertNull("IPv4 with octet > 255 should be invalid", uri);
    }
    

	@Test
	public void testIPv4WithPath() {
		Uri IPv4 = UriParserFactory.create("scheme://127.0.0.1/path?x=1").parse();
		assertNotNull("Should be valid IPv4",IPv4.getHost());
		assertNotNull("should have a valid query",IPv4.getQuery());
	}

	@Test
	public void testUserWithIPv4() {
		Uri usIPv4 = UriParserFactory.create("scheme://user@192.168.0.1").parse();
		assertEquals("user", usIPv4.getUserInfo());
		assertEquals("192.168.0.1", usIPv4.getHost().toString());
	}

	@Test
	public void testIPv4WithLeadingZeros() {
		Uri uri = UriParserFactory.create("scheme://192.168.001.001").parse();
		assertNotNull("IPv4 with leading zeros should parse", uri);
		assertEquals("192.168.1.1", uri.getHost().toString());
	}

	@Test
	public void testHostIsIPv4AddressInstance() {
		Uri uri = UriParserFactory.create("scheme://8.8.8.8").parse();
		assertNotNull("Valid IPv4 should parse", uri);
		Host host = uri.getHost();
		assertTrue("Host should be an instance of IPv4Address", host instanceof IPv4Address);
	}

	@Test
	public void testInvalidIPv4OutOfRange() {
		assertNull(UriParserFactory.create("scheme://300.1.1.1").parse());
	}


	@Test
	public void testIPv4WithNegativeNumber() {
		Uri uri = UriParserFactory.create("scheme://192.-1.0.1").parse();
		assertNull("IPv4 with negative number should be invalid", uri);
	}

	@Test
	public void testIPv4WithDoubleDot() {
		Uri uri = UriParserFactory.create("scheme://1..3.4").parse();
		assertNull("IPv4 with empty octet (double dot) should be invalid", uri);
	}

	@Test
	public void testIPv4WithSymbol() {
		Uri uri = UriParserFactory.create("scheme://192.168.@.1").parse();
		assertNull("IPv4 with special symbol should be invalid", uri);
	}

	@Test
	public void testIPv4WithLeadingDot() {
		Uri uri = UriParserFactory.create("scheme://.1.2.3.4").parse();
		assertNull("IPv4 with leading dot should be invalid", uri);
	}
	@Test
public void testIPv4Empty() {
    Uri uri = UriParserFactory.create("scheme://").parse();
    assertNotNull("URI should parse but host should be null", uri);
    assertNull("Empty host should return null", uri.getHost());
}



	@Test
    public void testRegNameWithConsecutiveDots() {
        Uri uri = UriParserFactory.create("scheme://example..com").parse();
        assertNull("Reg-name with internal consecutive dots should be invalid", uri);
    }

    @Test
    public void testRegNameStartsWithDash() {
        Uri uri = UriParserFactory.create("scheme://-host.com").parse();
        assertNull("Reg-name starting with dash should be invalid", uri);
    }

    @Test
    public void testRegNameEndsWithDash() {
        Uri uri = UriParserFactory.create("scheme://host-.com").parse();
        assertNull("Reg-name ending with dash should be invalid", uri);
    }

    @Test
    public void testRegNameWithUnderscore() {
        Uri uri = UriParserFactory.create("scheme://host_name.com").parse();
        assertNull("Reg-name with underscore should be invalid", uri);
    }

    @Test
    public void testRegNameWithPercentEncodedSlash() {
        Uri uri = UriParserFactory.create("scheme://host%2Fname.com").parse();
        assertNull("Reg-name with percent-encoded slash should be invalid", uri);
    }


    @Test
    public void testRegNameWithDotOnlySubdomain() {
        Uri uri = UriParserFactory.create("scheme://.com").parse();
        assertNull("Reg-name with dot-only subdomain should be invalid", uri);
    }


    @Test
    public void testRegNameWithUnicodeCharacter() {
        Uri uri = UriParserFactory.create("scheme://münchen.de").parse();
        assertNull("Reg-name with Unicode characters should be invalid unless punycode is supported", uri);
    }

    @Test
    public void testIPv4StyleButInvalidRegName() {
        Uri uri = UriParserFactory.create("scheme://123.456.789.0").parse();
        assertNull("Numeric reg-name formatted like IPv4 but invalid should be rejected", uri);
    }

	@Test
	public void testSchemeWithHost() {
		assertNotNull(UriParserFactory.create("scheme://unisaar.de").parse().getHost());
	}


	@Test
	public void testNumericRegName() {
		Uri uri = UriParserFactory.create("scheme://123456").parse();
		assertNotNull("Numeric reg-name should parse as host", uri);
		assertEquals("123456", uri.getHost().toString());
	}

	@Test
	public void testAlphanumericRegName() {
		Uri uri = UriParserFactory.create("scheme://a1b2c3.com").parse();
		assertNotNull("Alphanumeric reg-name should parse", uri);
		assertEquals("a1b2c3.com", uri.getHost().toString());
	}

	@Test
	public void testDotsOnlyRegName() {
		Uri uri = UriParserFactory.create("scheme://...").parse();
		assertNotNull("Reg-name with only dots should parse", uri);
		assertEquals("...", uri.getHost().toString());
	}

	@Test
	public void testVeryLongRegName() {
		String longHost = "averyveryveryveryveryverylongdomainname.com";
		Uri uri = UriParserFactory.create("scheme://" + longHost).parse();
		assertNotNull("Very long reg-name should parse", uri);
		assertEquals(longHost, uri.getHost().toString());
	}

	@Test
	public void testInvalidCharInRegName() {
		Uri uri = UriParserFactory.create("scheme://ho^st.com").parse();
		assertNull("Reg-name with invalid char should not parse", uri);
	}

	@Test
	public void testEmptyRegNameWithUserInfo() {
		Uri uri = UriParserFactory.create("scheme://user@/path").parse();
		assertNull("URI with empty host after userinfo should be invalid", uri);
	}

	@Test
	public void testRegNameEndsWithPercent() {
		Uri uri = UriParserFactory.create("scheme://host%.com").parse();
		assertNull("Reg-name ending with '%' should not parse", uri);
	}

	@Test
	public void testRegNameWithUnescapedSpace() {
		Uri uri = UriParserFactory.create("scheme://host name.com").parse();
		assertNull("Reg-name with unescaped space should not parse", uri);
	}

	@Test
	public void testRegNameWithInvalidPercentEncoding() {
		Uri uri = UriParserFactory.create("scheme://host%ZZ.com").parse();
		assertNull("Reg-name with invalid percent encoding should not parse", uri);
	}

	@Test
	public void testRegNameWithNewline() {
		Uri uri = UriParserFactory.create("scheme://host\nname.com").parse();
		assertNull("Reg-name with newline should be invalid", uri);
	}

	@Test
	public void testRegNameWithTab() {
		Uri uri = UriParserFactory.create("scheme://host\tname.com").parse();
		assertNull("Reg-name with tab character should be invalid", uri);
	}

	@Test
	public void testRegNameWithCurlyBrace() {
		Uri uri = UriParserFactory.create("scheme://host{name.com").parse();
		assertNull("Reg-name with '{' should be invalid", uri);
	}

	@Test
	public void testRegNameWithPipe() {
		Uri uri = UriParserFactory.create("scheme://host|name.com").parse();
		assertNull("Reg-name with '|' should be invalid", uri);
	}

	// Empty Host Tests
	@Test
	public void testUriWithoutHost() {
		assertNull(UriParserFactory.create("scheme://info@").parse().getHost());
	}

	@Test
	public void testIPv4WithEmptyHost() {
		Uri uri = UriParserFactory.create("scheme://").parse();
		assertNotNull("URI should parse", uri);
		assertNull("IPv4 host should be null when missing", uri.getHost());
	}

	// Path Tests
	

	@Test
	public void testEmptyPath() {
		Uri noPath = UriParserFactory.create("scheme://host").parse();
		assertEquals("Path should be empty", "", noPath.getPath());
	}

	

	@Test
	public void testUriWithPathAndQuery() {
		Uri path = UriParserFactory.create("scheme://prog2@unisaar.de/info?cookies=true").parse();
		assertNotNull("It should have a path", path.getPath());
		assertNotNull("It should be a valid query", path.getQuery());
	}

	// Query Tests
	@Test
	public void testWithQuery() {
		Uri query = UriParserFactory.create("scheme://info@uni.de/info?").parse();
		assertNotNull("Uri should have Query", query);
		assertEquals("should be empty", "" , query.getQuery());
	}

	

	@Test
	public void testEmptyQuery() {
		Uri empty = UriParserFactory.create("scheme://host/path?").parse();
		assertEquals("", empty.getQuery());
	}

	

	@Test
	public void testQuerySingleCharacter() {
		Uri uri = UriParserFactory.create("scheme://host/path?a").parse();
		assertNotNull(uri);
		assertEquals("a", uri.getQuery());
	}

	@Test
	public void testQueryOnlyEquals() {
		Uri uri = UriParserFactory.create("scheme://host/path?=").parse();
		assertNotNull(uri);
		assertEquals("=", uri.getQuery());
	}

	@Test
	public void testQueryOnlyAmpersand() {
		Uri uri = UriParserFactory.create("scheme://host/path?&").parse();
		assertNotNull(uri);
		assertEquals("&", uri.getQuery());
	}

	@Test
	public void testQueryWithOnlyAmpersands() {
		Uri uri = UriParserFactory.create("scheme://host/path?&&&").parse();
		assertNotNull(uri);
		assertEquals("&&&", uri.getQuery());
	}

	@Test
	public void testQueryUnmatchedEquals() {
		Uri uri = UriParserFactory.create("scheme://host/path?=value").parse();
		assertNotNull(uri);
		assertEquals("=value", uri.getQuery());
	}

	@Test
	public void testQueryInvalidPercentEncoding() {
		Uri uri = UriParserFactory.create("scheme://host/path?value=%GG").parse();
		assertNull(uri);
	}

	@Test
	public void testQueryEndsWithPercent() {
		Uri uri = UriParserFactory.create("scheme://host/path?value=%").parse();
		assertNull(uri);
	}

	@Test
	public void testQueryWithUnescapedSpace() {
		Uri uri = UriParserFactory.create("scheme://host/path?key=va lue").parse();
		assertNull(uri);
	}

	@Test
	public void testQueryWithNewline() {
		Uri uri = UriParserFactory.create("scheme://host/path?key=val\nue").parse();
		assertNull(uri);
	}

	@Test
	public void testQueryMixedValidAndInvalid() {
		Uri uri = UriParserFactory.create("scheme://host/path?key=1&%GG&x=2").parse();
		assertNull(uri);
	}


}
