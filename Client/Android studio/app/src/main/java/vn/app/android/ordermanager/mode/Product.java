package vn.app.android.ordermanager.mode;

/**
 * Created by ASUS on 12/11/2016.
 */

public class Product {
    private String mCode;
    private String mName;
    private long money;
    private int quantity;
    private String urlImage;
    private int numberOrder;

    public Product(String code, String name, int quantity, long money, int numberOrder){
        this.mCode = code;
        this.mName = name;
        this.money = money;
        this.quantity = quantity;
        this.numberOrder = numberOrder;

    }

    public int getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(int numberOrder) {
        this.numberOrder = numberOrder;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
