package Model;

import java.util.List;

/**
 * @author Nguyen Ngoc Hai - s3978281
 */

public interface ClaimProcessManager {
    void addClaim(Claim claim);
    void updateClaim(Claim claim);
    void deleteClaim(String claimID);
    Claim getAClaim(String claimID);
    List<Claim> getClaims();
}
