/**
 * enum holding the different possible header field
 */
public enum HTTPOption {
    ACCEPT("Accept"),
    ACCEPT_CHARSET("Accept-Charset"),
    ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_LANGUAGE("Accept-Language"),
    ACCEPT_RANGES("Accept-ranges"),
    AGE("Age"),
    ALLOW("Allow"),
    AUTHORIZATION("Authorization"),
    CACHE_CONTROL("Cache-Control"),
    CONNECTION("Connection"),
    CONTENT_ENCODING("Content-Encoding"),
    CONTENT_LANGUAGE("Content-Language"),
    CONTENT_LENGTH("Content-Length"),
    CONTENT_LOCATION("Content-Location"),
    CONTENT_MD5("Content-MD5"),
    CONTENT_RANGE("Content-Range"),
    CONTENT_TYPE("Content-Type"),
    COOKIE("Cookie"),
    DATE("Date"),
    ETAG("ETag"),
    EXPECT("Expect"),
    EXPIRES("Expires"),
    FROM("From"),
    HOST("Host"),
    IF_MATCH("If-Match"),
    IF_MODIFIED_SINCE("If-Modified-Since"),
    IF_NONE_MATCH("If-None-Match"),
    IF_RANGE("If-Range"),
    IF_UNMODIFIED_SINCE("If-Unmodified-Since"),
    LAST_MODIFIED("Last-Modified"),
    LOCATION("Location"),
    MAX_FORWARDS("Max-Forwards"),
    ORIGIN("Origin"),
    PRAGMA("Pragma"),
    PROXY_AUTHENTICATE("Proxy-Authenticate"),
    PROXY_AUTHORIZATION("Proxy-Authorization"),
    RANGE("Range"),
    REFERER("Referer"),
    RETRY_AFTER("Retry-After"),
    SERVER("Server"),
    SET_COOKIE("Set-Cookie"),
    TE("TE"),
    TRAILER("Trailer"),
    TRANSFER_ENCODING("Transfer-Encoding"),
    UPGRADE("Upgrade"),
    UPGRADE_INSECURE_REQUESTS("Upgrade-Insecure-Requests"),
    USER_AGENT("User-Agent"),
    VARY("Vary"),
    VIA("Via"),
    WARNING("Warning"),
    WWW_AUTHENTICATE("WWW-Authenticate");


    private String name;

    private HTTPOption(String name) {
        this.name = name;
    }

    /**
     * get the HTTPOption which name match the name given in argument
     * @param name a string holding the name of the searched HTTPOption
     * @return the HTTPOption which name match the name given in argument
     * @throws BadOptionException in case no HTTPOption match the name given in argument
     */
    public static HTTPOption getCorrespondingOption(String name) throws BadOptionException {
        for (HTTPOption option : HTTPOption.values()) {
            if (option.name.equals(name))
                return option;
        }
        throw new BadOptionException();
    }

    public String getName() {
        return name;
    }
}
