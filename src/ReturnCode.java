public enum ReturnCode {

    //TODO: ajouter les status
    OK(200, ""),
    SEE_OTHER(303, ""),
    BAD_REQUEST(400, ""),
    NOT_FOUND(404, ""),
    METHOD_NOT_ALLOWED(405, ""),
    LENGTH_REQUIERED(411, ""),
    NOT_IMPLEMENTED(501, ""),
    HTTP_VERSION_NOT_SUPPORTED(505, "");

    private int code;
    private String status;

    private ReturnCode(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }
}