package com.demo.disc.ProductApp.model;
import java.math.BigDecimal;
import java.util.ArrayList;
import com.demo.disc.ProductApp.model.Product;

public class ShoppingCart {
    private ArrayList<Product> cartList = new ArrayList();
    private BigDecimal totalPrice;


    public int itemCount(){
        int num = 0;
        for(Product item : cartList){
           num = num + 1;
        }
        return num;
    }
    public Product getProductByIndex(int index){
        return cartList.get((index));
    }
    public int getQuantityById(int id){
        for(Product item:cartList){
            if(id == item.getProductId()){
                return item.getQuantity();
            }
        }
        return 0;
    }
    public int getItemIndex(int id){
        int count = 0;
        for(Product item:cartList){
            if(id == item.getProductId()){
                break;
            }
            else{
                count++;
            }
        }
        return count;
    }

    public Product getProductById(int id){
        Product retItem = new Product();
        for(Product item:cartList){
            if(id == item.getProductId()){
                retItem=item;
            }
        }
        return retItem;
    }

    public void addItem(Product item){
        cartList.add(item);
        calculateTotal();
    }
    public boolean itemExists(int id){
        for(Product item:cartList){
            if(id==item.getProductId()){
                return true;
            }
        }
        return false;
    }

    public void delete(int id){
        int index = getItemIndex(id);
        cartList.remove(index);
        calculateTotal();
    }
    public void update(int id, int quantity) {
        Product item = null;
        if (quantity > 0) {
            item = cartList.get(getItemIndex(id));
            item.setQuantity(quantity);
            calculateTotal();
        }
    }
    public BigDecimal getTotalPrice(){
        return totalPrice;
    }

    public void calculateTotal(){
        BigDecimal total = BigDecimal.valueOf(0.00);
        for(Product item:cartList){
            BigDecimal quant = BigDecimal.valueOf(item.getQuantity());
            total = total.add(item.getPrice().multiply(quant));
        }
        totalPrice = total;
    }
}
