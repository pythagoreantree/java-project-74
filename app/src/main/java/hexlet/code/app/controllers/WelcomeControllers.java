package hexlet.code.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WelcomeControllers {
    @GetMapping(path = "/welcome")
    public String welcome() {
        return "Welcome to Spring";
    }
}
