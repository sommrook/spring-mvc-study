package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1(){
        ModelAndView mav = new ModelAndView("response/hello").addObject("data", "hello!");

        return mav;
    }

    // String으로 반환한다는 뜻이 아니라, model을 파라미터로 받고 있기 때문에 해당 경로의 view를 받는다는 뜻이다.
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model){
        model.addAttribute("data", "hello!");

        return "response/hello";
    }

    // return이 void이면 request가 들어온 url로 view경로를 찾아 리턴함 - 명확하지 않아서 비추!
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model){
        model.addAttribute("data", "hello!");
    }

    @RequestMapping("/response-view-test")
    public ModelAndView responseViewTest(@RequestParam String message){
        ModelAndView mav = new ModelAndView("response/hello").addObject("data", message);

        return mav;
    }
}
