package Complaint.utilities;

//import com.teamapt.cosmos.control.lib.AuthenticationUtils;
//import com.teamapt.exceptions.CosmosServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class AuthUtil {

    @Value("${oauth2.server.token.endpoint}")
    private String accessTokenUrl;

    @Value("${oauth.client.id}")
    private String clientId;

    @Value("${oauth.client.secret}")
    private String clientSecret;

    private Logger log = LoggerFactory.getLogger(getClass());

//    public String getAccessToken() {
//
//        try {
//            try{
//                return AuthenticationUtils.getAccessToken();
//            }
//            catch (Exception ex){
//                return AuthenticationUtils.getClientToken(accessTokenUrl, clientId, clientSecret);
//            }
//        } catch (CosmosServiceException e) {
//            log.error("Access token could not be retrieved", e);
//            throw new RuntimeException("Could not fetch access token.");
//        }
//
//    }
}
