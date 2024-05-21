package com.loosemole.micro.microscoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RestController
public class MicroScoringApplication {
    private int score = 0;

    public static void main(String[] args) {
        SpringApplication.run(MicroScoringApplication.class, args);
    }

    @GetMapping("/getscore")
    public int getScore() {
        return this.score;
    }

    @RequestMapping(value = "/addscore", method = POST)
	@ResponseBody
    public int addScore(@RequestParam(value = "pointAmount", defaultValue = "1") int addedPoints {
        this.score += addedPoints;
        return this.score;
    }
}
