package EA.Project.RabbitMQ.restcontroller;

import EA.Project.RabbitMQ.domain.UserInfo;
import EA.Project.RabbitMQ.dto.PostMessageInfo;
import EA.Project.RabbitMQ.repository.PostMessageInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class PostInfoController {

    @Autowired
    PostMessageInfoRepository postMessageInfoRepository;

    @RequestMapping(value = "postInfo/{userId}", method = RequestMethod.GET)
    public List<PostMessageInfo> get(@PathVariable String userId){

        List<PostMessageInfo> postMessageInfoList =  postMessageInfoRepository.myFindPostByUserId(userId);//.findAll();

        return postMessageInfoList;
        //Integer size = postMessageInfoList.size();

        //return size.toString();// userId.toString();
    }

}
