<%--
  Created by IntelliJ IDEA.
  User: James
  Date: 6/7/2019
  Time: 1:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="home.css">
</head>
<body>
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
                <li class="nav-item">
                    <a class="nav-link" href="cart.jsp">Cart <span class="sr-only">(current)</span></a>
                </li>
            </ul>
        </div>
    </nav>
</div>
<div class="space"></div>
<div class="container">
    <div class="row">
        <%
            ClientConfig configur = new ClientConfig();

            Client client = ClientBuilder.newClient(configur);

            WebTarget target = client.target("http://centaurus-2.ics.uci.edu:1030/ProductApp");


            String jsonResponse =
                target.path("webapi").path("products").
                        request(). //send a request
                        accept(MediaType.APPLICATION_JSON). //specify the media type of the response
                        get(String.class); // use the get method and return the response as a string

            System.out.println(jsonResponse);

            ObjectMapper objectMapper = new ObjectMapper(); // This object is from the jackson library

            List<Product> productList = objectMapper.readValue(jsonResponse, new TypeReference<List<Product>>(){});

            session = request.getSession(true);
            int colCount = 0;
            int colNum = 3;
            for(Product item : productList){
                out.println("<div class=\"col-sm\">\n" +
                        "<div class=\"itemContainer\">\n" +
                        "<a href=\"SingleItem?id="+ item.getProductId() + "\"><img src=\"" + item.getPic() + "\" width=\"300\" height=\"300\"></a>\n"+
                        "<p class=\"itemTitle\">" + item.getProductName() + "<span class=\"price\">" + item.getPrice() + "</span></p>\n"+
                        "<p class=\"description\">" + item.getProductDesc() + "</p>\n" +
                        "</div>\n" +
                        "</div>\n");
                colCount = colCount + 1;
                if(colCount % colNum == 0){
                    out.println("</div>\n" +
                            "<div class=\"row\">");
                }

            }
            out.println("</div>\n");
            out.println("<div class=\"container\">\n" +
                    "<h2><center>Item History</center><h2>\n"+
                    "<div class=\"card-columns\"style=\"column-count: 5;\">\n"+
                    "<div class=\"card\" style=\"width: 8rem; height: 8rem;\">\n" +
                    "<img src=\""+ session.getAttribute("firstpic")+"\" class=\"card-img-top\" alt=\"\"\n"+
                    "<div class=\"card-body\">\n"+
                    "<h5 class=\"card-title\">"+ session.getAttribute("firstname")+"</h5>\n"+
                    "<a href=\"SingleItem?id="+ session.getAttribute("first")+"\" class=\"btn btn-primary btn-sm\">Go to Link</a>\n"+
                    "</div>\n"+

                    "<div class=\"card\" style=\"width: 8rem; height: 8rem;\">\n" +
                    "<img src=\""+ session.getAttribute("secondpic")+"\"class=\"card-img-top\" alt=\"\"\n"+
                    "<div class=\"card-body\">\n"+
                    "<h5 class=\"card-title\">\n"+ session.getAttribute("secondname")+"</h5>\n"+
                    "<a href=\"SingleItem?id=" + session.getAttribute("second")+"\" class=\"btn btn-primary btn-sm\">Go to Link</a>\n"+
                    "</div>\n"+

                    "<div class=\"card\" style=\"width: 8rem; height: 8rem;\">\n" +
                    "<img src=\""+ session.getAttribute("thirdpic")+"\"class=\"card-img-top\" alt=\"\"\n"+
                    "<div class=\"card-body\">\n"+
                    "<h5 class=\"card-title\">\n"+ session.getAttribute("thirdname")+"</h5>\n"+
                    "<a href=\"SingleItem?id=" + session.getAttribute("third")+"\" class=\"btn btn-primary btn-sm\">Go to Link</a>\n"+
                    "</div>\n"+

                    "<div class=\"card\" style=\"width: 8rem; height: 8rem;\">\n" +
                    "<img src=\""+ session.getAttribute("fourthpic")+"\"class=\"card-img-top\" alt=\"\"\n"+
                    "<div class=\"card-body\">\n"+
                    "<h5 class=\"card-title\">\n"+ session.getAttribute("fourthname")+"</h5>\n"+
                    "<a href=\"SingleItem?id=" + session.getAttribute("fourth")+"\" class=\"btn btn-primary btn-sm\">Go to Link</a>\n"+
                    "</div>\n"+

                    "<div class=\"card\" style=\"width: 8rem; height: 8rem;\">\n" +
                    "<img src=\""+ session.getAttribute("fifthpic")+"\"class=\"card-img-top\" alt=\"\"\n"+
                    "<div class=\"card-body\">\n"+
                    "<h5 class=\"card-title\">\n"+ session.getAttribute("fifthname")+"</h5>\n"+
                    "<a href=\"SingleItem?id=" + session.getAttribute("fifth")+"\" class=\"btn btn-primary btn-sm\">Go to Link</a>\n"+
                    "</div>\n"+

                    "</div>\n"+
                    "</div>\n");
        %>
</body>
</html>
