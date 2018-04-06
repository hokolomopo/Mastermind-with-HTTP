public enum FileType {
    HTML("text/html", true),
    PNG("image/png", false),
    URL("application/x-www-form-urlencoded", true);

    private String contentType;
    private boolean isString;

    private FileType(String contentType, boolean isString) {
        this.contentType  = contentType;
        this.isString = isString;
    }

    public String getContentType() {
        return contentType;
    }

    public boolean isString() {
        return isString;
    }

    public static FileType getCorrespondingFileType(String contentType) throws BadFileException{
        for(FileType type : FileType.values()){
            if(type.contentType.equals(contentType))
                return type;
        }
        throw new BadFileException();
    }
}
