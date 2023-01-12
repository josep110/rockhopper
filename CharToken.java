class CharToken extends Token{
    String type;
    String repr;
    int length;

    CharToken(String type, String repr, int length){
        this.type = type;
        this.repr = repr;
        this.length = length;
    }
}
