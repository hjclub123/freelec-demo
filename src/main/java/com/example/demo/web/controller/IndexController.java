package com.example.demo.web.controller;

import com.example.demo.config.auth.dto.LoginUser;
import com.example.demo.config.auth.dto.SessionUser;
import com.example.demo.service.PostsService;
import com.example.demo.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    /**
     * 컨트롤러에서 문자열을 반환할 때 앞의 경로와 뒤의 파일 확장자는 자동으로 지정된다.
     * 여기선 "index"을 반환하므로, src/main/resources/templates/index.mustache로 전환되어
     * View Resolver가 처리하게 된다.
     *
     * - Model
     * 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
     * 여기서는 결과를 posts로 index.mustaches 에 전달한다.
     */
    /*
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }
    */

    /*
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());

        // CustomOAuth2UserService 클래스에서 로그인 성공 시 세션에 SessionUser를 저장하도록 구성했다.
        // getAttribute("user")로 가져온다.
        SessionUser user = (SessionUser) httpSession.getAttribute("user");


         // 세션에 저장된 값이 있을 때만 model에 userName으로 등록한다.
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index-login";
    }
     */

    /**
     * 5.4 이노테이션 기반으로 개선하기 p.200
     * ■ @LoginUser
     * 기존에 httpSession.getAttribute("user") 로 가져오던 세션 정보 값이 개선되었다.
     * 이제는 어느 컨트롤러든지 @LoginUser만 사용하면 세션 정보를 가져올 수 있게 되었다.
     */
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) throws UnsupportedEncodingException {
        model.addAttribute("posts", postsService.findAllDesc());
        if (user != null) {
            System.out.println("##### userName: " + user.getName());
            model.addAttribute("userName", user.getName());
        }
        return "index-login";
    }

    /**
     * /posts/save를 호출하면 posts-save.mustache를 호출하는 메소드
     */
    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
