package bakeryApplication;

import dao.AdminService;
import dto.Product;
import dto.User;

import java.util.List;
import java.util.Scanner;

public class AdminMainApp {
    static AdminService admin = new AdminService();
    static Scanner sc1 = new Scanner(System.in);

    public static void main(User user) {

        System.out.println("This is admin page " + user.getUserName());
        System.out.println("1. ADD PRODUCTS");
        System.out.println("2. REMOVE PRODUCTS");
        System.out.println("3. UPDATE PRODUCTS");
        System.out.println("4. SEARCH PRODUCT BY ID");
        System.out.println("5. DISPLAY ALL PRODUCTS");
        System.out.println("6. SIGN OUT");
        System.out.println("7. EXIT");
        int choice = sc1.nextInt();

        switch (choice) {
            case 1:
                System.out.println("ENTER PRODUCT NAME");
                String name = sc1.next();
                System.out.println("ENTER PRODUCT PRICE");
                double price = sc1.nextDouble();
                System.out.println("ENTER PRODUCT QUANTITY");
                int qty = sc1.nextInt();
                Product product = new Product(name, price, qty);
                int n = admin.addProduct(product);
                System.out.println(n + " RECORD ADDED!");
                break;

            case 2:
                System.out.println("ENTER THE PRODUCT ID YOU WANT TO REMOVE");
                int productId = sc1.nextInt();
                int m = admin.removeProduct(productId);
                System.out.println(m + " RECORD DELETED!");
                break;

            case 3:
                System.out.println("ENTER THE PRODUCT ID YOU WANT TO UPDATE");
                int id = sc1.nextInt();
                System.out.println("ENTER THE UPDATED PRODUCT NAME");
                String names = sc1.next();
                System.out.println("ENTER THE UPDATED PRODUCT PRICE");
                double prices = sc1.nextDouble();
                System.out.println("ENTER THE UPDATED QUANTITY");
                int qtys = sc1.nextInt();
                Product newProduct = new Product(id, names, prices, qtys);
                int o = admin.updateProduct(newProduct);
                System.out.println(o + " PRODUCT UPDATED!");
                break;

            case 4:
                System.out.println("ENTER THE PRODUCT NAME YOU WANT TO SEARCH");
                String pname = sc1.next();
                List<Product> productList = admin.displayProductByName(pname);
                System.out.println("Id \t\t Name \t\t Price \t\t Type");
                System.out.println("=========================================");
                for (Product p : productList) {
                    System.out.println(p.getProductId() + "\t\t" + p.getProductName() + "\t\t" + p.getProductPrice() + "\t\t" + p.getProductQty());
                }
                System.out.println("===========================================");
                break;

            case 5:
                List<Product> productLists = admin.displayAllProduct();
                System.out.println("Id \t\t Name \t\t Price \t\t Qty");
                System.out.println("=========================================");
                for (Product pl : productLists) {
                    System.out.println(pl.getProductId() + "\t\t" + pl.getProductName() + "\t\t" + pl.getProductPrice() + "\t\t" + pl.getProductQty());
                }
                System.out.println("===========================================");
                break;

            case 6:
                String[] arguments = {};
                MainApp.main(arguments);
                break;

            case 7:
                System.exit(0);
                break;

            default:
                System.out.println("INVALID INPUT!");

        }
        main(user);
    }
}