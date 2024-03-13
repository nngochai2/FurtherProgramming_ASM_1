package Controller;

import Model.Claim;
import Model.ClaimProcessManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ManageClaims implements Serializable, ClaimProcessManager {
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


    @Override
    public void add(Claim claim) {

    }

    @Override
    public void update(Claim claim) {

    }

    @Override
    public void delete(String claimID) {

    }

    @Override
    public Claim getOne(String claimID) {
        return null;
    }

    @Override
    public List<Claim> getAll() {
        return null;
    }
}
