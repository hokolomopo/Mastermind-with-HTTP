public enum HTTPOption {
    //Todo: tous les mettre
    ALLOW("Allow"),
    CONTENT_TYPE("Content-Type"),
    DATE("Date"),
    CONTENT_LENGTH("Content-Length");

    private String name;

    private HTTPOption(String name){
        this.name = name;
    }

    public static HTTPOption getCorrespondingOption(String name) throws BadOptionException{
        for(HTTPOption option : HTTPOption.values()){
            if(option.name == name)
                return option;
        }
        throw new BadOptionException();
    }

    public String getName() {
        return name;
    }
}
