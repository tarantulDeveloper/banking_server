package kg.devcats.server.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:8819", "http://194.152.37.7:8819"})
@RequestMapping("/")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WelcomeController {


    @GetMapping
    @ResponseBody
    public String getHtmlContent() {
        return "<html><body><a href=\"https://www.youtube.com/watch?v=uGl6IUhRqYs\">Все зависит от нас самих 🚀</a></body></html>";
    }
}
