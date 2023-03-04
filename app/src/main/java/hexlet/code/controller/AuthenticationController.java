package hexlet.code.controller;

import hexlet.code.dto.AuthenticationRequest;
import hexlet.code.dto.AuthenticationResponse;
import hexlet.code.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;

    @PostMapping("/api/login")
    public ResponseEntity<AuthenticationResponse> createAuthToken(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
