<%--
  Created by IntelliJ IDEA.
  User: James
  Date: 6/8/2019
  Time: 12:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import ="javax.ws.rs.client.Client"%>
<%@ page import ="javax.ws.rs.client.ClientBuilder"%>
<%@ page import ="javax.ws.rs.client.WebTarget"%>
<%@ page import ="javax.ws.rs.core.MediaType"%>
<%@ page import ="javax.ws.rs.core.UriBuilder"%>
<%@ page import ="org.codehaus.jackson.map.ObjectMapper"%>
<%@ page import ="com.demo.disc.ProductApp.model.Product"%>
<%@ page import ="org.codehaus.jackson.type.TypeReference"%>
<%@ page import ="org.glassfish.jersey.client.ClientConfig"%>
<%@ page import ="java.util.List"%>
<%@ page import ="javax.servlet.http.HttpServlet"%>
<%@ page import =" javax.servlet.http.HttpServletRequest"%>
<%@ page import ="javax.servlet.http.HttpSession"%>
<%@ page import ="javax.servlet.http.HttpServletResponse"%>
<%@ page import="com.demo.disc.ProductApp.model.ShoppingCart" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="javax.swing.*" %>

<html>
<head>
    <title>Cart</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script
            src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>"
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script src="https://cdn.rawgit.com/PascaleBeier/bootstrap-validate/v2.2.0/dist/bootstrap-validate.js" ></script>
    <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
    <script src="jquery.csv.js"></script>
    <link rel="stylesheet" type="text/css" href="home.css">
    <script type="text/javascript" src="detailedView.js?t=123"></script>
    <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">

</head>
<body>
<div id="app" class="container">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="index.html">Clothing Company</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div id="navbarNavDropdown" class="navbar-collapse collapse">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="loadItems.jsp">Browse <span class="sr-only">(current)</span></a>
                </li>
            </ul>
        </div>
    </nav>
</div>
<%
    String strAction = request.getParameter("action");
    session=request.getSession();
    ShoppingCart shoppingCart = null;
    Object objCart = session.getAttribute("cart");

    if(objCart!=null){
        shoppingCart = (ShoppingCart)objCart;
    }
    else{
        shoppingCart = new ShoppingCart();
        session.setAttribute("cart", shoppingCart);
    }
%>
<div class="space"></div>
<div class="container">
    <div class="row">
        <diddv class="col-lg">
            <div class="container">
                <table id="cart" class="table table-hover table-condensed">
                    <thead>
                    <tr>
                        <th style="width:72%">Product</th>
                        <th style="width:10%">Price</th>
                        <th style="width:8%">Quantity</th>
                        <th style="width:10%"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <!--@@@@@@@@@@@@@@@@@@@cart items here@@@@@@@@@@@@@@@-->
                    <%
                        for(int i = 0; i < shoppingCart.itemCount(); i++){
                            Product item = shoppingCart.getProductByIndex(i);
                            out.print("<tr>\n" +
                                    "                        <td data-th=\"Product\">\n" +
                                    "                            <div class=\"row\">\n" +
                                    "                                <div class=\"col-sm\">\n" +
                                    "                                    <h4>"+item.getProductName()+"</h4>\n" +
                                    "                                    <p>"+ item.getProductDesc()+"</p>\n" +
                                    "                                </div>\n" +
                                    "                            </div>\n" +
                                    "                        </td>\n" +
                                    "                        <td data-th=\"Price\">"+ item.getPrice()+"</td>\n" +
                                    "                        <td data-th=\"Quantity\">\n" +
                                    "                            <h5>"+ item.getQuantity()+"</h4>\n" +
                                    "                        </td>\n" +
                                    "                        <td class=\"actions\" data-th=\"\">\n" +
                                        "<form action=\"cart?action=delete&id="+item.getProductId()+"&quantity="+item.getQuantity()+"\" method=\"post\">"+
                                            "<input type=\"submit\" class=\"btn btn-danger btn-sm\" name=\"deleteItem\" value=\"Delete\"/>"+
                                        "</form>"+
                                    "                        </td>\n" +
                                    "                    </tr>");
                        }

                        out.print("</tbody>\n" +
"                    <tfoot>\n" +
"                    <tr class=\"visible-xs\">\n" +
"                        <td class=\"text-center\"><strong>Total: $"+ shoppingCart.getTotalPrice()+"</strong></td>\n" +
"                    </tr>\n" +
"                    <tr>\n" +
"                        <td><a href=\"loadItems.jsp\" class=\"btn btn-warning\"><i class=\"fa fa-angle-left\"></i> Continue Shopping</a></td>\n" +
"                        <td colspan=\"2\" class=\"hidden-xs\"></td>\n" +
"                    </tr>\n" +
"                    </tfoot>\n" +
"                </table>\n" +
"            </div>\n" +
"        </div>");
                    %>


        <div class="col-lg">
            <form id="submitted"method="post"action="" style="width: 400px; margin: auto">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" class="form-control"name="email"  id="email" placeholder="name@email.com">
                </div>
                <div class="row">
                    <div class="col">
                        <label for="firstname">First Name</label>
                        <input type="text" name="fname"class="form-control"  id="firstname">
                    </div>
                    <div class="col">
                        <label for="lastname">Last Name</label>
                        <input type="text" name="lname"class="form-control" id="lastname">
                    </div>
                </div>
                <div class="form-group">
                    <label for="phonenumber">Phone Number</label>
                    <input type="text" id="phonenumber" placeholder="9491537653"class="form-control">
                </div>
                <div class="form-group">
                    <label for="zip">Zip Code</label>
                    <input type="text" id="zip" class="form-control">
                </div>
                <div class="form-group">
                    <label for="state">State</label>
                    <input type="text" id="state" class="form-control">
                </div>
                <div class="form-group">
                    <label for="address">Shipping Address</label>
                    <input type="text" id="address" class="form-control">
                </div>
                <label for="shippingmethod">Shipping Method</label>
                <select class="form-control" id="shippingmethod">
                    <option>Overnight</option>
                    <option>2-Days Expedited</option>
                    <option>6-Days Ground</option>
                </select>
                <div class="form-group">
                    <label for="creditcard">Credit Card Information</label>
                    <input type="text" id="creditcard" class="form-control" placeholder="Card Number">
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" id="cardname"placeholder="Name On Card">
                </div>
                <div class="row">
                    <div class="col">
                        <input type="text" class="form-control" id="carddate"placeholder="Expiration (MM/YY)">
                    </div>
                    <div class="col">
                        <input type="text" class="form-control" id="cardcode"placeholder="Security Code">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="tax">Tax</label>
                        <input type="float" class="form-control" id="tax">
                    </div>
                </div>
                <div class="space2"></div>
                <button id="submitClicked" type="button" class="btn btn-primary" style="width: 300px; margin-left:50px; background-color:black">Submit</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
