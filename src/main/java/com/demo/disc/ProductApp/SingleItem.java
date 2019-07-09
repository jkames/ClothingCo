package com.demo.disc.ProductApp;
import com.demo.disc.ProductApp.model.Product;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.glassfish.jersey.client.ClientConfig;

@WebServlet(name = "SingleItem", urlPatterns = {"/SingleItem"})
public class SingleItem extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Connection conn = null;
        response.setContentType("text/html");
        String id =request.getParameter("id");

        PrintWriter out = response.getWriter();
        ClientConfig config = new ClientConfig();

        Client client = ClientBuilder.newClient(config);

        WebTarget target = client.target("http://centaurus-2.ics.uci.edu:1030/ProductApp");


        String jsonResponse =
                target.path("webapi").path("products").path(id).
                        request(). //send a request
                        accept(MediaType.APPLICATION_JSON). //specify the media type of the response
                        get(String.class); // use the get method and return the response as a string

        System.out.println(jsonResponse);

        ObjectMapper objectMapper = new ObjectMapper(); // This object is from the jackson library

        Product product = objectMapper.readValue(jsonResponse, new TypeReference<Product>(){});


        String[] enumArray = new String[]{"first", "second", "third", "fourth", "fifth"};
        HttpSession session = request.getSession(true);

        //create session and add item history
        if(session.getAttribute("fifth") == null){
            for(int i = 0; i < 5; i++){
                if(session.getAttribute(enumArray[i])==null){
                    session.setAttribute(enumArray[i], id);
                    session.setAttribute(enumArray[i]+"pic", product.getPic());
                    session.setAttribute(enumArray[i]+"name", product.getProductName());
                    break;
                }
            }
        }
        else{
            for(int i = 0; i < 4; i++){
                session.setAttribute(enumArray[i], session.getAttribute(enumArray[i+1]));
                session.setAttribute(enumArray[i]+"pic", session.getAttribute(enumArray[i+1]+"pic"));
                session.setAttribute(enumArray[i]+"name", session.getAttribute(enumArray[i+1]+"name"));
            }
            session.setAttribute(enumArray[4],id);
            session.setAttribute(enumArray[4]+"pic", product.getPic());
            session.setAttribute(enumArray[4]+"name", product.getProductName());
        }

        out.println("<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <title>Dress Pants</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\">\n" +
                "    <script\n" +
                "  src=\"https://code.jquery.com/jquery-3.4.1.js\"\n" +
                "  integrity=\"sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=\"\n" +
                "  crossorigin=\"anonymous\"></script>\"\n" +
                "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\"></script>\n" +
                "    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\"></script>\n" +
                "    <script src=\"https://cdn.rawgit.com/PascaleBeier/bootstrap-validate/v2.2.0/dist/bootstrap-validate.js\" ></script>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Lato\" rel=\"stylesheet\">\n" +
                "    <script src=\"jquery.csv.js\"></script>\n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"home.css\">\n" +
                "    <script src=\"singleItem.js\"></script>\n" +
                "    <title>Single Item</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div id=\"app\" class=\"container\">\n" +
                "                <nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">\n" +
                "                    <a class=\"navbar-brand\" href=\"index.html\">Clothing Company</a>\n" +
                "                    <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarNavDropdown\" aria-controls=\"navbarNavDropdown\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n" +
                "                        <span class=\"navbar-toggler-icon\"></span>\n" +
                "                    </button>\n" +
                "                    <div id=\"navbarNavDropdown\" class=\"navbar-collapse collapse\">\n" +
                "                        <ul class=\"navbar-nav mr-auto\">\n" +
                "                            <li class=\"nav-item active\">\n" +
                "                                <a class=\"nav-link\" href=\"loadItems.jsp\">Browse <span class=\"sr-only\">(current)</span></a>\n" +
                "                            </li>\n" +
                "                    </div>\n" +
                "                </nav>\n" +
                "            </div>");
        out.println("<div class=\"space\"></div>\n"+
                "<div class=\"container\">\n"+
                "<div class=\"row\">\n"+
                "<div class=\"col-lg\">\n"+
                "<img src=\""+ product.getPic()+"\" class=\"mainPic\" height=\"500\" width=\"500\"/>\n"+
                "</div>\n"+
                "<div class=\"col-lg\">\n"+
                "<h2 class=\"itemTitle\">"+ product.getProductName()+ "</h1>\n" +
                "<p class=\"description\">"+ product.getProductDesc()+ "</p>\n"+
                "<h4 class=\"itemTitle\">"+ product.getPrice()+ "</h2>\n"+
                "<form action=\"cart?action=add&id="+product.getProductId()+"&name="+product.getProductName()+"&price="+product.getPrice()+"&pic="+product.getPic()+"&description="+product.getProductDesc()+"\" method=\"post\">"+
                    "<input type=\"submit\" class=\"btn btn-info\" name=\"cartAdd\" value=\"Add To Cart\"/>"+
                "</form>");

                //"<button method=\"post\" onclick=\"window.location.href=\'cart?action=add&id="+product.getProductId()+"&name="+product.getProductName()+"&price="+product.getPrice()+"&pic="+product.getPic()+"\'\""+ "type=\"button\" class=\"btn btn-primary style=\"width: 300px; margin-left:50px; background-color:black\">Add to Cart</button>\n");

    }


}
