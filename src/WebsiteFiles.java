import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Enum that holds the different files of the server (exepct the main HTML page)
 * 
 */
public enum WebsiteFiles {
	CSS("play.css"),
	JAVASCIPT("play.js");
	
	private String fileName;
	
	private WebsiteFiles(String f) {
		this.fileName = f;
	}
	
	/**
	 * Convert a file into a string
	 * 
	 * @return The file conbverted in String
	 * @throws IOException
	 */
	public String file2String() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.fileName), "UTF-8"));
		
		String fileString = new String();
		String tmp;
		
		while((tmp = reader.readLine()) != null) {
			fileString += tmp + "\r\n";
		}
		
		reader.close();
		
		return fileString;
	}
	
    /**
     * return the WebsiteFiles object whose name correspond to the string given as argument
     *
     * @param contentType the name of the searched WebsiteFiles
     * @return the WebsiteFiles object
     */
    public static WebsiteFiles getFile(String file){

        for (WebsiteFiles f : WebsiteFiles.values())
            if (file.equals(f.fileName))
                return f;
        return null;
    }

    
    /**
     * Get HTTP content Type
     * 
     * @return A string representing the HTTP content type
     */
    public String getContentType(){
        switch (this){
            case CSS:
                return FileType.CSS.getContentType();
            case JAVASCIPT:
                return FileType.JS.getContentType();
        }
        return null;
    }


}
