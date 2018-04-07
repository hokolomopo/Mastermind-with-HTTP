public enum FileType {
    HTML("text/html"),
    PNG("image/png");

    private String contentType;

    private FileType(String contentType) {
        this.contentType  = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public static FileType getCorrespondingFileType(String contentType) throws BadFileException{
        for(FileType type : FileType.values()){
            if(type.contentType.equals(contentType))
                return type;
        }
        throw new BadFileException();
    }
}
