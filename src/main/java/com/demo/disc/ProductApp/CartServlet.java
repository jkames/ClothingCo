package com.demo.disc.ProductApp;

import com.demo.disc.ProductApp.model.ShoppingCart;
import com.demo.disc.ProductApp.model.Product;
import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import com.demo.disc.ProductApp.model.Product;
import org.codehaus.jackson.type.TypeReference;
import org.glassfish.jersey.client.ClientConfig;

@WebServlet("/cart")
public class CartServlet extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        String strAction = request.getParameter("action");
        if(strAction.equals("add")){
            addCart(request);
        }
        else if(strAction.equals("update")){
            //updateCart(request);
        }
        else if(strAction.equals("delete")){
            deleteCart(request);
        }
        response.sendRedirect("cart.jsp");
    }
    protected void addCart(HttpServletRequest request){



        HttpSession session = request.getSession();
        String stringId = request.getParameter("id");
        String desc = request.getParameter("description");
        String name = request.getParameter("name");
        String StringPrice = request.getParameter("price");
        String pic = request.getParameter("pic");
        int id=Integer.parseInt(stringId);
        BigDecimal price = new BigDecimal(StringPrice);


        System.out.println(id);
        System.out.println(desc);

        ShoppingCart shoppingCart= null;
        Object objCart = session.getAttribute("cart");



        if(objCart!=null){
           shoppingCart = (ShoppingCart)objCart;
        }
        else{
            shoppingCart = new ShoppingCart();
            session.setAttribute("cart", shoppingCart);
        }
        if(shoppingCart.itemExists(id)){
           shoppingCart.update(id, shoppingCart.getQuantityById(id)+1);
           System.out.println(shoppingCart.itemCount());
            Product newItem = shoppingCart.getProductById(id);
            System.out.println("cart servlet quantity: " + newItem.getQuantity());
           WebTarget resource = ClientBuilder.newBuilder().build().target("http://centaurus-2.ics.uci.edu:1030/ProductApp/webapi/products/"+newItem.getQuantity());
           resource.request().put(Entity.json(newItem));
        }
        else if(!shoppingCart.itemExists(id)){
            Product newItem = new Product();
            newItem.setProductId(id);
            newItem.setPic(pic);
            newItem.setProductDesc(desc);
            newItem.setProductName(name);
            newItem.setPrice(price);
            newItem.setQuantity(1);
            shoppingCart.addItem(newItem);

            WebTarget resource = ClientBuilder.newBuilder().build().target("http://centaurus-2.ics.uci.edu:1030/ProductApp/webapi/products");
            resource.request().post(Entity.json(newItem));
        }

        System.out.println("Shopping item count:"+shoppingCart.itemCount());
        System.out.println("total amount without tax:" + shoppingCart.getTotalPrice());

    }

    protected void updateCart(HttpServletRequest request){
        HttpSession session = request.getSession();
        String stringQuantity = request.getParameter("quantity");
        String stringId = request.getParameter("id");
        int id= Integer.parseInt(stringId);
        int quantity = Integer.parseInt(stringQuantity);

        ShoppingCart shoppingCart = null;
        Object objCart = session.getAttribute("cart");

        if(objCart!=null){
            shoppingCart = (ShoppingCart)objCart;
        }
        else{
            shoppingCart = new ShoppingCart();
        }
        shoppingCart.update(id, quantity);
    }

    protected void deleteCart(HttpServletRequest request){
        HttpSession session = request.getSession();
        String stringId = request.getParameter("id");
        ShoppingCart shoppingCart = null;
        int id = Integer.parseInt(stringId);

        Object objCart = session.getAttribute("cart");
        if(objCart!=null){
            shoppingCart= (ShoppingCart)objCart;
        }
        else{
            shoppingCart = new ShoppingCart();
        }
        shoppingCart.delete(id);
        WebTarget resource = ClientBuilder.newBuilder().build().target("http://centaurus-2.ics.uci.edu:1030/ProductApp/webapi/products/"+id);
        resource.request().delete();
    }


}
