# URI Parser

A Java implementation of a URI parser that validates and parses URIs according to a subset of RFC 3986 specification.

## Overview

This project implements a comprehensive URI parser that can handle various URI components including schemes, user information, hosts (both IPv4 addresses and regular names), paths, and query parameters. The parser follows a well-defined grammar and provides robust validation for different URI formats.

## Features

- **Scheme Validation**: Supports alphanumeric schemes starting with a letter
- **IPv4 Address Parsing**: Handles IPv4 addresses with proper octet validation (0-255)
- **Regular Name Hosts**: Supports domain names and other valid host formats
- **User Information**: Parses user credentials with proper encoding support
- **Path Handling**: Processes URI paths with validation
- **Query Parameters**: Supports query string parsing with proper encoding
- **Percent Encoding**: Handles URL-encoded characters correctly
- **Comprehensive Validation**: Rejects malformed URIs according to specification

## Grammar

The parser implements the following URI grammar:

```
URI           = scheme ":" hierarchical [ "?" query ]
hierarchical  = "//" authority path
scheme        = ALPHA *( ALPHA / DIGIT )
authority     = [ userinfo "@" ] host
userinfo      = *( pchar / ":" )
host          = IPv4address / reg-name
IPv4address   = dec-octet "." dec-octet "." dec-octet "." dec-octet
dec-octet     = ["0" ["0"]] DIGIT      ; 000-009 with optional leading zeros
              / ["0"] "1"-"9" DIGIT    ; 010-099
              / "1" DIGIT DIGIT        ; 100-199
              / "2" "0"-"4" DIGIT      ; 200-249
              / "25" "0"-"5"           ; 250-255
reg-name      = *pchar
path          = *( "/" *pchar )        ; begins with "/" or is empty
query         = *( pchar / "&" / "=" )
pchar         = unreserved / pct-encoded
unreserved    = ALPHA / DIGIT / "."
pct-encoded   = "%" HEXDIGIT HEXDIGIT
```

## Usage

### Basic Usage

```java
// Create a URI parser
UriParser parser = UriParserFactory.create("https://user:pass@example.com:8080/path?query=value");

// Parse the URI
Uri uri = parser.parse();

if (uri != null) {
    System.out.println("Scheme: " + uri.getScheme());
    System.out.println("User Info: " + uri.getUserInfo());
    System.out.println("Host: " + uri.getHost().toString());
    System.out.println("Path: " + uri.getPath());
    System.out.println("Query: " + uri.getQuery());
}
```

### Examples

#### Valid URIs
```java
// Simple HTTP URI
UriParserFactory.create("http://example.com").parse();

// URI with user information
UriParserFactory.create("https://user:password@example.com").parse();

// URI with IPv4 address
UriParserFactory.create("http://192.168.1.1/path").parse();

// URI with query parameters
UriParserFactory.create("https://example.com/search?q=java&type=code").parse();

// URI with percent encoding
UriParserFactory.create("http://example.com/path%20with%20spaces").parse();
```

#### Invalid URIs
```java
// Invalid scheme (starts with digit)
UriParserFactory.create("9http://example.com").parse(); // returns null

// Invalid IPv4 address
UriParserFactory.create("http://256.1.1.1").parse(); // returns null

// Invalid percent encoding
UriParserFactory.create("http://example.com/path%ZZ").parse(); // returns null
```

## Project Structure

```
src/
├── uri/
│   ├── Uri.java                    # Main URI interface
│   ├── Host.java                   # Host interface
│   ├── IPv4Address.java            # IPv4 address interface
│   ├── UriParser.java              # Parser interface
│   ├── UriParserFactory.java       # Factory for creating parsers
│   ├── implementation/
│   │   ├── UriImplementation.java
│   │   ├── HostImplementation.java
│   │   ├── IPv4AddressImplementation.java
│   │   └── UriParserImplementation.java
│   └── tests/
│       └── SimpleTests.java        # Comprehensive test suite
```

## Testing

The project includes comprehensive unit tests covering:

- **Scheme validation**: Various valid and invalid scheme formats
- **IPv4 address parsing**: Edge cases including leading zeros and out-of-range values
- **Host validation**: Regular names with various characters and encodings
- **User information**: Different userinfo formats with and without passwords
- **Path handling**: Empty paths, complex paths with encoding
- **Query parsing**: Various query formats and edge cases
- **Error handling**: Malformed URIs and invalid characters

Run tests using your preferred Java testing framework (JUnit).

## Technical Details

### Key Components

1. **UriParserFactory**: Creates parser instances for given URI strings
2. **UriParser**: Main parsing interface that converts strings to Uri objects
3. **Uri**: Represents a parsed URI with methods to access components
4. **Host**: Base interface for host components
5. **IPv4Address**: Specialized host type for IPv4 addresses with octet access

### Validation Rules

- Schemes must start with a letter and contain only letters and digits
- IPv4 addresses must have valid octets (0-255) with proper dot separation
- User information supports various characters including percent-encoded values
- Paths can be empty or start with "/" followed by valid path characters
- Query strings support key-value pairs with proper encoding

### Error Handling

The parser returns `null` for invalid URIs rather than throwing exceptions, allowing for graceful error handling in applications.

## Requirements

- Java 8 or higher
- JUnit for running tests


## Contributing

Contributions are welcome! Please feel free to submit issues or pull requests.
