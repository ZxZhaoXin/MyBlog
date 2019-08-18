package Myblog.controller;

import Myblog.aync.EventModel;
import Myblog.aync.EventProducer;
import Myblog.aync.EventType;
import Myblog.model.Comment;
import Myblog.model.HostHolder;
import Myblog.service.CommentService;
import Myblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = "/addComment/{articleId}")
    public String addComment(@PathVariable("articleId") int articleId, @RequestParam("content")String content
    ,@RequestParam(value = "next",required = false)String next){
        Comment comment = new Comment();
        if (hostHolder.getUser()==null)
            return "redirect:/in?next=/article/"+articleId;
        else
            comment.setUserId(hostHolder.getUser().getId());
        comment.setContent(content);
        comment.setCreatedDate(new Date());
        comment.setArticleId(articleId);
        comment.setStatus(0);
        commentService.addCommet(comment);

        int count = commentService.getCommentsCount(articleId);
        articleService.updateCommentCount(articleId,count);

        eventProducer.fireEvent(new EventModel().setType(EventType.COMMENT)
                .setActorId(hostHolder.getUser().getId())
                .setExts("username",hostHolder.getUser().getName())
                .setExts("email","zhenyutu@126.com")
                .setExts("articleId",String.valueOf(articleId)));

        return "redirect:/article/"+articleId;
    }
}
