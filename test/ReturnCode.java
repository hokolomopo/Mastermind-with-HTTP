public enum ReturnCode{

    //TODO: ajouter les status
    OK(200, ""),
    seeOther(303, ""),
    badRequest(400, ""),
    notFound(404, ""),
    methodNotAllowed(405, ""),
    lengthRequired(411, ""),
    notImplemented(501, ""),
    HTTPVersionNotSupported(505, "");

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