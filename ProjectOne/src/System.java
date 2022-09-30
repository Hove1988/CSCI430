/*******************************************************************
 * 
 * Project 1: Warehouse, System implementation
 * File: System.java
 * 
 * Author: Nathan O'Connor, Jake Happoja, Blake Hoosline, Joseph Hoversten
 * Group Number: 2
 * Instructor: Dr. Ramnath Sarnath
 * Class: CSCI 430
 * 
 * Based On: Library.java by Dr. Ramnath Sarnath
 * 
 *******************************************************************/
package ProjectOne.src;

import java.util.*;
import java.io.*;
public class System implements Serializable {
  private static final long serialVersionUID = 1L;
  public static final int BOOK_NOT_FOUND  = 1;
  public static final int BOOK_NOT_ISSUED  = 2;
  public static final int BOOK_HAS_HOLD  = 3;
  public static final int BOOK_ISSUED  = 4;
  public static final int HOLD_PLACED  = 5;
  public static final int NO_HOLD_FOUND  = 6;
  public static final int OPERATION_COMPLETED= 7;
  public static final int OPERATION_FAILED= 8;
  public static final int NO_SUCH_MEMBER = 9;
  private Inventory inventory;
  private ClientList clientList;
  private InvoiceHistory invoiceHistory;
  private ShipmentHistory shipmentHistory;
  private static System system;
  private System() {
    inventory = Inventory.instance();
    clientList = ClientList.instance();
    invoiceHistory = InvoiceHistory.instance();
    shipmentHistory = ShipmentHistory.instance();
  }
  public static System instance() {
    if (system == null) {
      ClientIdServer.instance(); // instantiate all singletons
      return (system = new System());
    } else {
      return system;
    }
  }
  public Product addProduct(String description, int quantity, float price, float wholesalePrice) {
    Product product = new Product(description, quantity, price, wholesalePrice);
    if (inventory.insertProduct(product)) {
      return (product);
    }
    return null;
  }
  public Client addClient(String name, String address, String phone) {
    Client client = new Client(name, address);
    if (clientList.insertClient(client)) {
      return (client);
    }
    return null;
  }

  public Iterator getProducts() {
      return inventory.getProducts();
  }

  public Iterator getClients() {
      return clientList.getClients();
  }
  public static System retrieve() {
    try {
      FileInputStream file = new FileInputStream("LibraryData");
      ObjectInputStream input = new ObjectInputStream(file);
      input.readObject();
      ClientIdServer.retrieve(input);
      return system;
    } catch(IOException ioe) {
      ioe.printStackTrace();
      return null;
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
      return null;
    }
  }
  public static  boolean save() {
    try {
      FileOutputStream file = new FileOutputStream("LibraryData");
      ObjectOutputStream output = new ObjectOutputStream(file);
      output.writeObject(system);
      output.writeObject(ClientIdServer.instance());
      return true;
    } catch(IOException ioe) {
      ioe.printStackTrace();
      return false;
    }
  }
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(system);
    } catch(IOException ioe) {
      System.out.println(ioe);
    }
  }
  private void readObject(java.io.ObjectInputStream input) {
    try {
      input.defaultReadObject();
      if (system == null) {
        system = (System) input.readObject();
      } else {
        input.readObject();
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  public String toString() {
    return inventory + "\n" + clientList;
  }
}
