package fer.hr.auth0.controller;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class UserController {

    @GetMapping("/api/userinfo")
    public Map<String, Object> getUserInfo(@AuthenticationPrincipal OidcUser oidcUser) {
        if (oidcUser != null) {
            return Map.of("name", oidcUser.getFullName());
        } else {
            return Map.of("error", "User not authenticated");
        }
    }
}

