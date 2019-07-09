<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.demo.disc.ProductApp.model.ShoppingCart" %><%--
  Created by IntelliJ IDEA.
  User: James
  Date: 6/9/2019
  Time: 11:04 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <titl>Confirmation Page</titl></title>
</head>
<body>
<h2>Thank you for your order!</h2>
<%
    ShoppingCart shoppingCart= null;
    Object objCart = session.getAttribute("cart");
    shoppingCart = (ShoppingCart)objCart;
    for(int i = 0; i < shoppingCart.itemCount();i++){
        out.print("<p>item: "+ shoppingCart.getProductByIndex(i).getProductName()+ " description: "+ shoppingCart.getProductByIndex(i).getProductDesc()+
        "price: "+ shoppingCart.getProductByIndex(i).getPrice()+ " quantity: "+ shoppingCart.getProductByIndex(i).getQuantity()+"</p>");
    }
    out.print("<h2>Total Price: "+ shoppingCart.getTotalPrice()+"</h2>");
%>

</body>
</html>
