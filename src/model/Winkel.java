package model;

import jdk.nashorn.internal.ir.SwitchNode;

import java.io.*;
import java.util.ArrayList;

public class Winkel {

    String naam;
    ArrayList<Product>producten;


    public Winkel(String naam) {
        if (naam == null || naam.trim().isEmpty()) throw new IllegalArgumentException("Naam mag niet leeg zijni");
        this.producten = new ArrayList<>();
    }

    public void addProduct(Product product){
        if(producten.contains(product)) throw new IllegalArgumentException("Product bestaat al in winkel");
        if(containsProduct(product)) throw new IllegalArgumentException("Product bestaat al in winkel!");
        int size = producten.size();
        product.setId(size);
        System.out.println("id: " + size);
        this.producten.add(product);
    }

    public boolean containsProduct(Product product){
        for(Product p : producten){
            if(p.getNaam().equals(product.naam) && p.getClass() == product.getClass()){
                return true;
            }
        }
        return false;
    }

    public boolean existingProduct(String type){
        ArrayList<Character>existingproducts = new ArrayList<>();
        char movie = 'M';
        char game = 'G';
        char cd = 'C';
        existingproducts.add(movie);
        existingproducts.add(game);
        existingproducts.add(cd);
        for (Character c:existingproducts){
            if(c.equals(type.charAt(0))){
                return true;
            }
        }
        return false;
    }

    public Product getProductById(int id){
        if(id > producten.size()) throw new IllegalArgumentException("Bij id '" + id + "' hoort geen product");
        for(Product p : producten){
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }

    public void deleteProductonId(int id){
        if(id > producten.size()) throw new IllegalArgumentException("Bij id '" + id + "' hoort geen product");
        for(Product p : producten){
            if(p.getId() == id){
                producten.remove(p);
            }
        }
    }

    public double getPrice(int productid, int days) {
        double price = 0;
        Product p = getProductById(productid);
        if(p instanceof Movie){
            price = 5;
            int daysLeft = days - 3;
            if (daysLeft > 0) {
                price += (daysLeft * 2);
            }
        } else if(p instanceof Game){
            price = days * 3;
        } else if(p instanceof Cd){
            price = days * 1.5;
        }
        return price;
    }

    public int totaalAantalProducten(){
        return producten.size();
    }

    public ArrayList getFilms(){
        ArrayList<Product>films = new ArrayList<>();
        for(Product p : producten){
            if(p instanceof Movie){
                films.add(p);
            }
        }
        return films;
    }

    public ArrayList getGames(){
        ArrayList<Product>games = new ArrayList<>();
        for(Product p : producten){
            if(p instanceof Game){
                games.add(p);
            }
        }
        return games;
    }

    public ArrayList getCDs(){
        ArrayList<Product>cds = new ArrayList<>();
        for(Product p : producten){
            if(p instanceof Cd){
                cds.add(p);
            }
        }
        return cds;
    }

    public String ArraytoStringOverview(ArrayList arrayList){
        String ret = "";
        for(Object p : arrayList){
            ret += p.toString() + "\n";
        }
        return ret;
    }

    public String toString(){
        String ret =  "Totaal aantal producten: " + totaalAantalProducten();
        for(Product p : producten){
            ret+= "\n" + p.toString();
        }
        return ret;
    }

    public void writeToFile()
            throws IOException {
        ArrayList films = getFilms();
        ArrayList games = getGames();
        ArrayList cds = getCDs();

        String filmoverview = ArraytoStringOverview(films);
        String gamesoverview = ArraytoStringOverview(games);
        String cdsoverview = ArraytoStringOverview(cds);
        String overview = "Films (" + films.size() + ")\n" + filmoverview + "\nGames (" + games.size() + ")\n" + gamesoverview + "\nCD's (" + cds.size() + ")\n" + cdsoverview;
        String allproducts = this.toString();
        try (PrintStream out = new PrintStream(new FileOutputStream("shop.txt"))) {
            out.print(allproducts);
            out.print("\n\n\n");
            out.print(overview);
        }
    }


}
