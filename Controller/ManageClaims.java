package Controller;

import Model.Claim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ManageClaims implements Serializable {
    private static ManageClaims instance;
    public ArrayList<Claim> claims;
    public ManageClaims() {
        claims = new ArrayList<>();
    }

    public static ManageClaims getInstance() {
        if (instance == null) {
            instance = new ManageClaims();
        }
        return instance;
    }

    public List<Claim> getAllClaims() {
        return claims;
    }





}
