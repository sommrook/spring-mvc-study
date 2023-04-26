package hello.servlet.web.servletmvc;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet{

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // WEB-INF에 있는 jsp파일들은 client에서 직접 호출하지 못한다. -> webapp에 있는 다른 애들은 호출 가능 ex) localhost:8080/jsp/members.jsp
        // 클라이언트가 서버에 request를 보내면 jsp를 return 해주는 형태임
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        // redirect는 실제로 자신의 path가 있음. 포워드는 서버 내부에서 일어나는 호출이기 때문에 path는 동일함
        dispatcher.forward(request, response);
    }
}
