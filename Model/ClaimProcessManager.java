package Model;

import java.util.List;

public interface ClaimProcessManager {
    void addClaim(Claim claim);
    void updateClaim(Claim claim);
    void deleteClaim(String claimID);
    Claim getAClaim(String claimID);
    List<Claim> getClaims();
}
