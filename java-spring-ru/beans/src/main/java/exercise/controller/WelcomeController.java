package exercise.controller;

import exercise.daytime.Day;
import exercise.daytime.Night;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

// BEGIN
@RestController
public class WelcomeController {

    @Autowired
    private Day day;
    @Autowired
    private Night night;

    @GetMapping(path = "/welcome")
    public String greetings() {
        var time = LocalDateTime.now().getHour();

        if (time >= 22 && time < 6) {
            return "It is " + day.getName() + " now! Welcome to Spring!";
        } else {
            return "It is " + night.getName() + " now! Welcome to Spring!";
        }
    }

}
// END
