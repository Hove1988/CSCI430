/*******************************************************************
 * 
 * Project 1: Warehouse, Inventory Tester
 * File: InventoryTest.java
 * 
 * Author: Jacob Haapoja
 * Group Number: 2
 * Instructor: Dr. Ramnath Sarnath
 * Class: CSCI 430
 * 
 *******************************************************************/

import ProjectOne.src.*;

public class InventoryTest{
    public static void main(String[] args) {
        Product p1 = new Product("Hammer", 20, 5, 2.5);
        Product p2 = new Product("Box of Nails: 100 count", 200, 1, 0.25);

        Product pro;

        Inventory inventory = Inventory.instance();

        inventory.insertProduct(p1);
        inventory.insertProduct(p2);

        Iterator<Product> p= inventory.getProducts();
        while (p.hasNext()){
            system.out.println(p.next().toString());
        }


        int nailID = inventory.searchInventory("Box of Nails: 100 count").getPID();
        int hammerID = inventory.searchInventory("Hammer").getPID();

        int cid1 = 1;
            
        system.out.println(inventory.toString());

        inventory.searchInventory("Box of Nails: 100 count").setQuant(0);
        inventory.searchInventory("Hammer").setQuant(0);

        Wait w1 = new Wait(cid1, 2),
             w2 = new Wait(cid1, 5);


        inventory.searchInventory(hammerID).addWait(w1);
        inventory.searchInventory(nailID).addWait(w2);

        system.out.println(inventory.toString());

        Iterator<Wait> waits = inventory.searchInventory(hammerID).getwaits();

        while(waits.hasNext()){
            inventory.searchInventory(hammerID).fulfillWait(waits.Next());
        }

        system.out.println(inventory.toString());
    }
}