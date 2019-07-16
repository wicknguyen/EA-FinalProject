package EA.Project.RabbitMQ.controller;

import EA.Project.RabbitMQ.domain.Person;
import EA.Project.RabbitMQ.domain.PostInfo;
import EA.Project.RabbitMQ.domain.RelationshipInfo;
import EA.Project.RabbitMQ.domain.UserInfo;
import EA.Project.RabbitMQ.producer.Sender;
import EA.Project.RabbitMQ.service.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private Sender sender;
    @RequestMapping(value = "friends/request1", method = RequestMethod.POST)
    public @ResponseBody void add(@RequestBody UserInfo userInfo) {
        sender.SendUserInfo(userInfo);
    }


    @RequestMapping(value = "friends/request", method = RequestMethod.POST)
    public @ResponseBody void requestFriend(@RequestBody RelationshipInfo relationshipInfo) {
        sender.SendRequestFriend(relationshipInfo);
    }


    @RequestMapping(value = "posts/add", method = RequestMethod.POST)
    public @ResponseBody void postAdd(@RequestBody PostInfo postInfo) {

        System.out.println(postInfo);
        sender.SendPost(postInfo);
    }





}
