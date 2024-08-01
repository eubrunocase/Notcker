package services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AdminController {

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
       public String adminAcess() {
           return "admin Content";
       }

}
