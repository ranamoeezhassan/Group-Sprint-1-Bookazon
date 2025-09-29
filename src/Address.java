public abstract class Address {
    protected String line1;
    protected String line2;
    protected String city;
    protected State state;
    protected String zip;
    protected String country;

    public Address(String line1, String line2, String city, State state, String zip, String country) {
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    public String getLine1(){
        return line1;
    }

    public void setLine1(String line1){
        this.line1 = line1;
    }

    public String getLine2(){
        return line2;
    }

    public void setLine2(String line2){
        this.line2 = line2;
    }

    public String getCity(){
        return city;
    }

    public void setCity(String city){
        this.city = city;
    }

    public State getState(){
        return state;
    }

    public void setState(State state){
        this.state = state;
    }

    public String getZip(){
        return zip;
    }

    public void setZip(String zip){
        this.zip = zip;
    }

    public String getCountry(){
        return country;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public String getFullAddress(){
        return line1 + ", " + line2 + ", " + city + ", " + state + ", " + zip + ", " + country;
    }
}
