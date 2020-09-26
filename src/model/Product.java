package model;

public class Product {
    private int id;
    String naam;
    boolean uitgeleend;

    public Product(String naam){
        this.id = id;
        this.naam = naam;
        uitgeleend = false;
    }

    public void setUitgeleend(boolean uitgeleend) {
        this.uitgeleend = uitgeleend;
    }

    public boolean isUitgeleend() {
        return uitgeleend;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public String toString(){
        String ret =  " ID: " + id + " Naam: " + naam + " Type: " + getClass().getSimpleName();
        if(uitgeleend){
            ret += " | Niet beschikbaar";
        } else {
            ret += " | Beschikbaar";
        }
        return ret;
    }
}
