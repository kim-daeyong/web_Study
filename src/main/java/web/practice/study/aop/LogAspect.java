package web.practice.study.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.omg.CORBA.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import web.practice.study.dto.SlackAPIDto;

import javax.servlet.http.HttpServletRequest;

@Component // 1
@Aspect // 2
@Slf4j
@RequiredArgsConstructor
public class LogAspect {

    protected static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private final SlackApi slackApi = new SlackApi("https://hooks.slack.com/services/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");
    private final SlackMessage slackMessage = new SlackMessage();


    private SlackAPIDto slackAPIDto;


    @Before("execution(public * web..*(..))")
    public void before(JoinPoint jointPoint) {
        String signatureName = jointPoint.getSignature().getName();
        logger.info("@Before [ " + signatureName + " ] 메서드 실행 전처리 수행 ");
        for(Object arg : jointPoint.getArgs()) {
            logger.info("@Before [ " + signatureName + " ] : " + arg);

        }
    }

    @AfterReturning(pointcut="execution(public * web.practice.study..*.*(..))", returning="ret")
    public void afterReturning(JoinPoint joinPoint, Object ret) {
        String signatureName = joinPoint.getSignature().getName();

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        logger.info("@AfterReturing [ " + signatureName + " ] 메서드 실행 후처리 수행");
        logger.info("@AfterReturing [ " + signatureName + " ] 리턴값=" + ret);


        System.out.println(slackApi.toString());
        slackMessage.setUsername("testtest");
        slackMessage.setChannel("#general");
        slackMessage.setText("{"+ signatureName +"}, url={"+request.getRequestURL()+"}, errorMessage={"+ret+"}");

        System.out.println(slackMessage.toString());

        slackApi.call(slackMessage);


    }

    @AfterThrowing(pointcut="execution(* *..service*.*(..))", throwing="ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String signatureName = joinPoint.getSignature().getName();
        logger.info("@AfterThrowing [ " + signatureName + " ] 메서드 실행 중 예외 발생");
        logger.info("@AfterThrowing [ " + signatureName + " ] 예외=" + ex.getMessage());
    }

    @After("execution(* *..*.*service(..))")
    public void afterFinally(JoinPoint joinPoint) {
        String signatureName = joinPoint.getSignature().getName();
        logger.info("@After [ " + signatureName + " ] 메서드 실행 완료");
    }
}
