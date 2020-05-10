package web.practice.study.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SlackAPIDto {

    private String message;
}
