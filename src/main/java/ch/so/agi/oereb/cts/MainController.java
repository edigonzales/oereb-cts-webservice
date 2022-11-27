package ch.so.agi.oereb.cts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class MainController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OerebValidator validator;

    @GetMapping("/")
    public String show(/*Model model*/) {
        System.out.println(validator.getResults());

        // Eventuell doch Repository:
        // - Übersicht für Titelseite
        // - Detail pro Requestgruppe
        
        //model.addAttribute("repositories", iliRepoList);
        return "gui";
    } 

}
