import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * class representing a header of the HTTP protocol
 */
public class HTTPHeader
{

    private HTTPOption option;
    private String value;

    public HTTPHeader(HTTPOption option, String value)
    {
        this.option = option;
        this.value = value;
    }

    /**
     * constructor taking as argument a string corresponding to a header as given by the http protocol
     *
     * @param header a string formatted this way: "header_type: header_value"
     * @throws BadHeaderException in case the header string is not valid
     */
    public HTTPHeader(String header) throws BadHeaderException
    {
        try
        {

            StringTokenizer token = new StringTokenizer(header, ":");

            option = HTTPOption.getCorrespondingOption(token.nextToken());

            value = token.nextToken();
            value = value.trim(); // remove spaces at the beginning

            // continue to read while there are tokens because the value may contain ':'
            String tmp;
            try
            {
                while (true)
                {
                    tmp = token.nextToken();
                    value += ':';
                    value += tmp;
                }
            }
            catch (NoSuchElementException e)
            {
                // end of loop
            }
        }
        catch (BadOptionException | NoSuchElementException e)
        {
            throw new BadHeaderException();
        }
    }

    public HTTPOption getOption()
    {
        return option;
    }

    public String getValue()
    {
        return value;
    }
}
