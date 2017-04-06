package vn.app.android.ordermanager.mode;

import org.json.JSONException;
import org.json.JSONObject;

import vn.app.android.ordermanager.util.LogUtil;

/**
 * Created by ASUS on 3/13/2017.
 */
public class Register {

    public static final String TAG = Register.class.getName();
    private String compCode;
    private String compName;
    private String email;
    private String address;
    private String phone;
    private String username;
    private String password;
    private int usingTime;

    public String getCompCode() {
        return compCode;
    }

    public void setCompCode(String compCode) {
        this.compCode = compCode;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUsingTime() {
        return usingTime;
    }

    public void setUsingTime(int usingTime) {
        this.usingTime = usingTime;
    }
    /**
     * convert Json to object Register
     * @param userObject
     * @return
     * @throws JSONException
     */
    public Register fromJsontoObject(JSONObject userObject) {
        try {
            JSONObject jsonUser = userObject.getJSONObject("User");
            username = jsonUser.optString("UserName");
            password = jsonUser.optString("Password");
            compCode = jsonUser.optString("Comp_Code");
            usingTime = jsonUser.optInt("UsingTime");
            compName = jsonUser.optString("Comp_Name");
            email = jsonUser.optString("Email");
            address = jsonUser.optString("Address");
            phone = jsonUser.optString("Phone");
            return this;
        } catch (JSONException e){
            LogUtil.e(TAG, e.getMessage());
            return null;
        }

    }

    /**
     * convert Object User to JsonObject
     * @return
     * @throws JSONException
     */
    public JSONObject toJsonObject() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserName", username);
            jsonObject.put("Password", password);
            jsonObject.put("Comp_Code", compCode);
            jsonObject.put("UsingTime", usingTime);
            jsonObject.put("Comp_Name", compName);
            jsonObject.put("Email", email);
            jsonObject.put("Address", address);
            jsonObject.put("Phone", phone);
            return jsonObject;
        }catch (JSONException e){
            LogUtil.e(TAG,e.getMessage());
            return null;
        }

    }

    @Override
    public String toString() {
        return toJsonObject().toString();
    }
}
