package pra.lue11.empleoexpres.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author luE11 on 14/07/23
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {
    private static final String INDEX_PAGE = "index";
    @Autowired
    private BCryptPasswordEncoder encoder;


    @GetMapping
    public String showIndexPage() {
        System.out.println(encoder.encode("1234"));
        return INDEX_PAGE;
    }


}
