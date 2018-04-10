/**
 * enumeration that holds the different possible types of the body of a HTTP request
 * used in mastermind project
 */

public enum FileType {
    HTML("text/html ;charset=utf-8", true),
    PNG("image/png", false),
    URL("application/x-www-form-urlencoded", true);

    private String contentType;
    private boolean isString;

    private FileType(String contentType, boolean isString) {
        this.contentType = contentType;
        this.isString = isString;
    }

    public String getContentType() {
        return contentType;
    }

    public boolean isString() {
        return isString;
    }

    /**
     * return the FileType object whose name correspond to the string ien in argument
     *
     * @param contentType the name of the searched FileType
     * @return
     * @throws BadFileException thrown in case no FileType match the given name
     */
    public static FileType getCorrespondingFileType(String contentType) throws BadFileException {
        for (FileType type : FileType.values()) {
            if (type.contentType.equals(contentType))
                return type;
        }
        throw new BadFileException();
    }
}
