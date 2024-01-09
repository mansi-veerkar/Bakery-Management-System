package dao;

import dto.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdminService extends ServiceImpl{

    public int addProduct(Product product) {
        int n =0;
        String insertQuery = "INSERT INTO PRODUCT_INFO(PRODUCT_NAME,PRODUCT_PRICE,PRODUCT_QTY) VALUES(?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1,product.getProductName());
            pstmt.setDouble(2,product.getProductPrice());
            pstmt.setInt(3,product.getProductQty());
            n = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("INVALID DATA, PRODUCT NOT INSERTED");
        }
        return n;
    }

    public int removeProduct(int productId) {
        int n =0;
        String deleteQuery = "DELETE FROM PRODUCT_INFO WHERE PRODUCT_ID=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
            pstmt.setInt(1,productId);
            n=pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("PRODUCT NOT FOUND");
        }
        return n;
    }

    public int updateProduct(Product product){
        int n=0;
        String updateQuery = "UPDATE PRODUCT_INFO SET PRODUCT_NAME=?, PRODUCT_PRICE=?, PRODUCT_QTY=? WHERE PRODUCT_ID=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(updateQuery);
            pstmt.setString(1,product.getProductName());
            pstmt.setDouble(2,product.getProductPrice());
            pstmt.setInt(3,product.getProductQty());
            pstmt.setInt(4,product.getProductId());
            n=pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("PRODUCT ID NOT FOUND");
        }
        return n;
    }


    @Override
    public List<Product> displayAllProduct() {
        String displayQuery = "SELECT * FROM PRODUCT_INFO";
        List<Product> productList= new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(displayQuery);

            while (rs.next()){
                int productId=rs.getInt(1);
                String productName=rs.getString(2);
                double productPrice=rs.getDouble(3);
                int productQty=rs.getInt(4);

                Product product = new Product(productId,productName,productPrice,productQty);
                productList.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return productList;
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


}
