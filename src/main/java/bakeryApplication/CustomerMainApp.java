package bakeryApplication;

import dao.CustomerService;
import dao.ServiceImpl;
import dto.Order;
import dto.Product;
import dto.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import java.util.Scanner;

public class CustomerMainApp {
    static Scanner sc1=new Scanner(System.in);
    static CustomerService service=new CustomerService();

    public static void main(User user){

        System.out.println("WELCOME "+ user.getUserName()+"!");
        System.out.println("-------------------------");
        System.out.println("SELECT OPERATION");
        System.out.println("1. DISPLAY ALL PRODUCTS");
        System.out.println("2. PLACE ORDER");
        System.out.println("3. CANCEL ORDER");
        System.out.println("4. DISPLAY ALL ORDERS");
        System.out.println("5. SIGN OUT");
        int choice=sc1.nextInt();

        switch (choice){
            case 1:
                displayAllProducts();
                break;

            case 2:
                placeOrder(user);
                break;

            case 3:
                cancelOrder(user);
                break;

            case 4:
                displayAllOrder(user);
                break;
            case 5 :
                user = null ;
                return;
            default:
                System.out.println("INVALID INPUT ");
        }

        main(user);
    }


    private static void displayAllProducts() {
        List<Product> productList = service.displayAllProduct() ;
        int i=1;
        System.out.println("S.NO. \t NAME  \t  PRICE ");
        System.out.println("=========================================");
        for (Product p : productList)
        {
            System.out.println(i+"\t  "+ p.getProductName() +"\t  "+p.getProductPrice());
            i++;
        }
        System.out.println("===========================================");
    }

    private static void placeOrder(User user) {

        Order ord = new Order(user);

        do {
            System.out.println("ENTER PRODUCT NAME ");
            String productName = sc1.next();
            System.out.println("ENTER ORDER QTY ");
            int orderQty = sc1.nextInt();
            Product product = new Product(productName, orderQty);
            ord.addProduct(product);

            System.out.println("ADD MORE PRODUCT (Y/N)");
            char ch = sc1.next().charAt(0);
            if (ch == 'n' || ch == 'N')
                break;

        }while (true);

        boolean status = false;
        try {
            status = service.placeOrder(ord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (status)
            System.out.println("ORDER PLACED !!");
        else
            System.out.println("ORDER NOT PLACED !!");
    }

    private static void cancelOrder(User user) {
        boolean status=true;
        displayAllOrder(user);
        System.out.println("SELECT ORDER");
        int oId= sc1.nextInt();
        for(Product p: service.displayAllProduct(oId)){
            System.out.println(p.getProductId()+" "+p.getProductName());
        }
        System.out.println("ENTER YOUR PRODUCT ID WHICH YOU WANT TO CANCEL");
        int pd=sc1.nextInt();
        Order o=new Order(oId,pd);
        status=service.cancelOrder(o);
        if(status)
            System.out.println("ORDER CANCELLED!");
        else
            System.out.println("ORDER NOT CANCELLED!");
    }

    private static void displayAllOrder(User user) {
        for (Order o :service.displayAllOrders(user)){
            System.out.println(o.getOrderId());
        }
    }

}
