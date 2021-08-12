package maciej.grochowski.studentsidentities.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddressController {

    @GetMapping()
    public String index() {
        return "index";
    }
}
