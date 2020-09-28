package UI;

import model.*;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class WinkelUI {



    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(new File("shop.txt"));
        input.useDelimiter("-|\n");
        Winkel winkel = new Winkel("OOO-Winkel");

        while(input.hasNext()) {
            String type = input.next();
            String name = input.next();

            if(type.equalsIgnoreCase("game")) {
                Product newProduct = new Game(name);
                winkel.addProduct(newProduct);
            }else if(type.equalsIgnoreCase("movie")){
                Product newProduct = new Movie(name);
                winkel.addProduct(newProduct);
            }else if(type.equalsIgnoreCase("cd")){
                Product newProduct = new Cd(name);
                winkel.addProduct(newProduct);
        }else{
                break;
            }
    }


        String menu = "1. Add product\n2. Delete product \n3. Show all products\n4. Show product on id\n5. Show rental price\n6. Reserve product\n7. Check availability on id\n\n\n0. Quit";
        int choice = -1;
        while (choice != 0) {
            String choiceString = JOptionPane.showInputDialog(menu);
            choice = Integer.parseInt(choiceString);
            if (choice > 7) {
                JOptionPane.showMessageDialog(null, "Geen geldige keuze!", "Error", JOptionPane.ERROR_MESSAGE);
            }


            if (choice == 1) {
                String title = JOptionPane.showInputDialog("Enter the product title:");
                if (title == null || title.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Title cannot be empty!", "Error!", JOptionPane.ERROR_MESSAGE);
                    throw new IllegalArgumentException("Not a valid title");
                }

                String type = JOptionPane.showInputDialog("Enter the type (M for movie/G for game/C for CD):");
                if (type == null || type.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Type cannot be empty!", "Error!", JOptionPane.ERROR_MESSAGE);
                    throw new IllegalArgumentException("Not a valid type");
                }

                if (!winkel.existingProduct(type)) {
                    JOptionPane.showMessageDialog(null, type + " is not a valid type", "Error!", JOptionPane.ERROR_MESSAGE);
                    throw new IllegalArgumentException("Not a valid type");
                }
                if (type.equals("M")) {
                    try {
                        Product product = new Movie(title);
                        winkel.addProduct(product);
                        JOptionPane.showMessageDialog(null, "Product toegevoegd!");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (type.equals("G")) {
                    try {
                        Product product = new Game(title);
                        winkel.addProduct(product);
                        JOptionPane.showMessageDialog(null, "Product toegevoegd!");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (type.equals("C")) {
                    try {
                        Product product = new Cd(title);
                        winkel.addProduct(product);
                        JOptionPane.showMessageDialog(null, "Product toegevoegd!");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            if (choice == 2) {
                String id = JOptionPane.showInputDialog("Enter the product ID to delete: ");
                try {
                    int productid = Integer.parseInt(id);
                    Product selectedproduct = winkel.getProductById(productid);
                    String check = JOptionPane.showInputDialog(selectedproduct.toString() + "\n Are you sure you want to delete this product? (Y/N)");
                    if (check.equals("Y") || check.equals("y")) {
                        winkel.deleteProductonId(productid);
                        JOptionPane.showMessageDialog(null, "Product deleted!", "info", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Product remains!!", "info", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (choice == 3) {
                ArrayList films = winkel.getFilms();
                ArrayList games = winkel.getGames();
                ArrayList cds = winkel.getCDs();

                String filmoverview = winkel.ArraytoStringOverview(films);
                String gamesoverview = winkel.ArraytoStringOverview(games);
                String cdsoverview = winkel.ArraytoStringOverview(cds);
                String overview = "Films (" + films.size() + ")\n" + filmoverview + "\nGames (" + games.size() + ")\n" + gamesoverview + "\nCD's (" + cds.size() + ")\n" + cdsoverview;

                JOptionPane.showMessageDialog(null, overview, "Products", JOptionPane.INFORMATION_MESSAGE);
            }


            if (choice == 4) {
                String id = JOptionPane.showInputDialog("Enter the product ID");
                try {
                    int idx = Integer.parseInt(id);
                    Product selectedproduct = winkel.getProductById(idx);
                    JOptionPane.showMessageDialog(null, selectedproduct.toString(), "Product " + id, JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (choice == 5) {
                String id = JOptionPane.showInputDialog("Enter the product ID");
                try {
                    int productid = Integer.parseInt(id);
                    Product selectedproduct = winkel.getProductById(productid);
                    String dagen = JOptionPane.showInputDialog(selectedproduct.toString() + "\n How many days?");
                    int days = Integer.parseInt(dagen);
                    double prijs = winkel.getPrice(productid, days);
                    JOptionPane.showMessageDialog(null, prijs + " €", "Product " + id, JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (choice == 6) {
                String id = JOptionPane.showInputDialog("Enter the product ID you want to reserve:");
                try {
                    int productid = Integer.parseInt(id);
                    Product selectedproduct = winkel.getProductById(productid);
                    if (selectedproduct.isUitgeleend()) {
                        JOptionPane.showMessageDialog(null, "Product is already reserved", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        selectedproduct.setUitgeleend(true);
                        JOptionPane.showMessageDialog(null, "Product reserved!", "Product " + id, JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (choice == 7) {
                String id = JOptionPane.showInputDialog("Enter the product ID: ");
                try {
                    int productid = Integer.parseInt(id);
                    Product selectedproduct = winkel.getProductById(productid);
                    if (selectedproduct.isUitgeleend()) {
                        JOptionPane.showMessageDialog(null, "ID: " + selectedproduct.getId() + " Naam: " + selectedproduct.getNaam() + "\nNot available!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "ID: " + selectedproduct.getId() + " Naam: " + selectedproduct.getNaam() + "\nAvailable!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (choice == 0){
                FileWriter writer = new FileWriter("shop.txt");
                ArrayList<Movie> movies = winkel.getFilms();
                ArrayList<Game> games = winkel.getGames();
                ArrayList<Cd> cds = winkel.getCDs();
                for (Movie m: movies) {
                    writer.write("movie-"+m.getNaam()+"\n");
                }
                for (Game g: games) {
                    writer.write("game-"+g.getNaam()+"\n");
                }
                for (Cd c: cds) {
                    writer.write("cd-"+c.getNaam()+"\n");
                }
                writer.close();
            }
        }
    }
}

