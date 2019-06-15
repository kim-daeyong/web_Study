package web.practice.study.controller.API;


import lombok.RequiredArgsConstructor;
import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.practice.study.dto.SlackAPIDto;

@RestController
@RequestMapping("/api/slack")
@RequiredArgsConstructor
public class SlackAPIController {
    @GetMapping("/test")
    public void webHook(){
        SlackApi api = new SlackApi("https://hooks.slack.com/services/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");    //웹훅URL
        api.call(new SlackMessage("#general", "TEST-WEBHOOK", "연습~~~~"));
    }
}
