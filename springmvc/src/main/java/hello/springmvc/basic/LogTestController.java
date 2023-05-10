package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Rest API의 그 API -> 화면 return이 아닌 데이터를 return해줌 -> 뷰를 찾는것이 아니라, HTTP 메시지 바디에 바로 입력
// Controller는 반환값이 String이면 뷰 이름으로 인식된다.
@Slf4j
@RestController
public class LogTestController {

//    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest(){
        String name = "Spring";

        // logging설정 레벨에 상관없이 trace가 찍히지 않더라도, 문자열 "+"의 연산이 일어나서 필요없는 CPU연산도 일어나고 메모리 낭비가 생긴다.
        // log.trace("trace log="+name);

        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info("info log={}", name);
        log.warn("warn log={}", name);
        log.error("error log={}", name);

        return "OK";
    }
}
