package pra.lue11.empleoexpres.controller;

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

    @GetMapping
    public String showIndexPage() {
        return INDEX_PAGE;
    }


}
