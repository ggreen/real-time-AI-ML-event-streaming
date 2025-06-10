package realtime.ai.ml.event.streaming.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Initialize home page
 * @author gregory green
 */
@Controller
public class IndexController {
    private final String userId;
    public IndexController(@Value("${mail.user.id}")
                           String userId) {
        this.userId = userId;
    }

    /**
     * Set user name in model
     * @param model the model
     * @return index
     */
    @RequestMapping("/")
    public String homePage(Model model) {
        model.addAttribute("userId",userId);
        return "index";
    }

    /**
     * Set user name in model
     * @param model the model
     * @return index
     */
    @RequestMapping("/mail")
    public String mailPage(Model model) {
        model.addAttribute("userId",userId);
        return "mail";
    }
}
