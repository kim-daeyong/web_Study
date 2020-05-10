package web.practice.study.controller;


import lombok.RequiredArgsConstructor;
import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/slack")
@RequiredArgsConstructor
public class SlackTestController {

    @GetMapping("/test")
    public String webHook() {
        SlackApi api = new SlackApi("https://hooks.slack.com/services/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");    //웹훅URL
        api.call(new SlackMessage("#general", "TEST-WEBHOOK", "연습~~~~"));


        return "index";
    }
}
