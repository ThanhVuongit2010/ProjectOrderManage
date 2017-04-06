package vn.app.android.ordermanager.rest;

/**
 * Created by Mr Vuong on 3/22/2017.
 */
public class URLConnectionResponse {
    public static final String TAG = URLConnectionResponse.class.getName();

    private int statusCode;
    private String entity;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
}
