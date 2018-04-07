import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class HTTPHeader {

    private HTTPOption option;
    private String value;

    public HTTPHeader(HTTPOption option, String value){
        this.option = option;
        this.value = value;
    }

    public HTTPHeader(String header) throws BadHeaderException {
        try {
            StringTokenizer token = new StringTokenizer(header, ":");
            this.option = HTTPOption.getCorrespondingOption(token.nextToken());
            this.value = token.nextToken();
        }
        catch(BadOptionException | NoSuchElementException e){
            throw new BadHeaderException();
        }
    }

    public HTTPOption getOption() {
        return option;
    }

    public String getValue() {
        return value;
    }
}