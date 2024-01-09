package dao;

import dto.Order;
import dto.Product;
import dto.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerService extends ServiceImpl{
    @Override
    public List<Product> displayAllProduct() {
        String selectQuery = "SELECT product_name , product_price FROM product_info ";
        List<Product> productList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);
            while (rs.next()) {
                String name = rs.getString(1);
                double price = rs.getDouble(2);
                Product pro = new Product(name , price);
                productList.add(pro);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return productList;
    }


    public boolean placeOrder(Order ord) throws SQLException {
        String insertUserProcedure = "{call insertUser(?)}";
        String placeOrderProcedure = "{call placeOrder(? , ? , ?)}";
        CallableStatement cstmt = conn.prepareCall(insertUserProcedure);
        cstmt.setInt(1 , ord.getUser().getUserId());
        cstmt.execute() ;
        ResultSet rs = cstmt.getResultSet();
        int ordId = 0 ;
        while (rs.next())
            ordId = rs.getInt(1);

        cstmt = conn.prepareCall(placeOrderProcedure);

        for (Product p : ord.getProductList()) {
            cstmt.setInt(1, ordId);
            cstmt.setString(2, p.getProductName() );
            cstmt.setInt(3 , p.getProductQty());
            cstmt.execute();
        }
        return true ;
    }

    public boolean cancelOrder(Order ord){
        boolean n=true;
        String query="{call cancelOrder(?,?)}";
        try {
            CallableStatement cstmt=conn.prepareCall(query);
            cstmt.setInt(1,ord.getOrderId());
            cstmt.setInt(2,ord.getProductId());
            n= cstmt.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return n;
    }
    
    public List<Order> displayAllOrders(User user){
        List<Order> orderList=new ArrayList<>();
        String query="SELECT order_id from order_info where user_id="+user.getUserId();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs= stmt.executeQuery(query);
            while (rs.next()){
                int oId=rs.getInt(1);
                Order o1=new Order();
                o1.setOrderId(oId);
                orderList.add(o1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
       
        return orderList;
    }

    @Override
    public List<Product> displayProductByName(String productName) {
        String displayQuery = "SELECT * FROM PRODUCT_INFO WHERE PRODUCT_NAME=?";
        List<Product> productList=new ArrayList<>();
        Product product=new Product();
        try {
            PreparedStatement pstmt = conn.prepareStatement(displayQuery);
            pstmt.setString(2,productName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                productList.add(product);
                product = new Product(rs.getInt(1),rs.getString(2),rs.getDouble(3),rs.getInt(4));
            }

        } catch (SQLException e) {

        }

        return productList;
    }

    public List<Product> displayAllProduct(int oId) {
        List<Product> productList=new ArrayList<>();
        String query="select p.product_id,p.product_name from order_product op inner join product_info p on p.product_id=op.product_id where order_id=?";
        try {
            PreparedStatement pstmt= conn.prepareCall(query);
            pstmt.setInt(1,oId);
            ResultSet rs=pstmt.executeQuery();
            while (rs.next()){
                int pId=rs.getInt(1);
                String pName=rs.getString(2);
                Product p =new Product(pId,pName);
                productList.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }
}
