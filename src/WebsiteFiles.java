import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public enum WebsiteFiles{
    CSS("play.css"),
    JAVASCIPT("play.js");

    private String fileName;

    private WebsiteFiles(String f){
        this.fileName = f;
    }

    public String file2String() throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.fileName), "UTF-8"));

        String fileString = new String();
        String tmp;

        while ((tmp = reader.readLine()) != null){
            fileString += tmp + "\r\n";
        }

        reader.close();

        return fileString;
    }

    public static WebsiteFiles getFile(String file){

        for (WebsiteFiles f : WebsiteFiles.values())
            if (file.equals(f.fileName))
                return f;
        return null;
    }

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
