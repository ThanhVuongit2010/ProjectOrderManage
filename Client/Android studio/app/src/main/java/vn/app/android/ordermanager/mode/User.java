package vn.app.android.ordermanager.mode;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mr Vuong on 3/13/2017.
 */
public class User {

    private String userName;
    private String password;
    private int compCode;
    private int branchCode;
    private String roleCode;
    private String sector;
    private String lastLogin;
    private String startDate;
    private String endDate;
    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public int getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(int branchCode) {
        this.branchCode = branchCode;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * convert Json to object User
     * @param jsonUser
     * @return
     * @throws JSONException
     */
    public User fromJsontoObject(JSONObject jsonUser) throws JSONException {
//        JSONObject jsonUser = userObject.getJSONObject("user");
        userName = jsonUser.optString("UserName");
        password = jsonUser.optString("Password");
        compCode = jsonUser.optInt("Comp_Code");
        branchCode = jsonUser.optInt("Branch_Code");
        roleCode = jsonUser.optString("Role_Code");
        sector = jsonUser.optString("Sector");
        lastLogin = jsonUser.optString("Last_loging");
        startDate = jsonUser.optString("Apply_Start_Date");
        endDate = jsonUser.optString("Apply_End_Date");
        return this;
    }

    /**
     * convert Object User to JsonObject
     * @return
     * @throws JSONException
     */
    public JSONObject toJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserName", userName);
        jsonObject.put("Password", password);
        jsonObject.put("Comp_Code", compCode);
        jsonObject.put("Branch_Code", branchCode);
        jsonObject.put("Role_Code", roleCode);
        jsonObject.put("Sector", sector);
        jsonObject.put("Last_loging", lastLogin);
        jsonObject.put("Apply_Start_Date", startDate);
        jsonObject.put("Apply_End_Date", endDate);

        return jsonObject;
    }

}
