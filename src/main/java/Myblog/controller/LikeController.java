package Myblog.controller;

import Myblog.model.HostHolder;
import Myblog.model.User;
import Myblog.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class LikeController {
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;


    @RequestMapping(path = "/like/{articleId}")
    public String like(@PathVariable("articleId")int articleId){
        User user = hostHolder.getUser();
       if (user==null){
           return "redirect:/in?next=/article/"+articleId;
       }

       likeService.like(user.getId(),articleId);
       return "redirect:/article/"+articleId;
    }

    @RequestMapping(path = "/dislike/{articleId}")
    public String dislike(@PathVariable("articleId")int articleId){
        User user = hostHolder.getUser();
        if (user==null){
            return "redirect:/in?next=/article/"+articleId;
        }
        likeService.dislike(user.getId(),articleId);
        return "redirect:/article/"+articleId;
    }
}
