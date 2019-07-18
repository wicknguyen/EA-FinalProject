package EA.Project.RabbitMQ.listener;


import EA.Project.RabbitMQ.domain.PostInfo;
import EA.Project.RabbitMQ.domain.RelationshipInfo;
import EA.Project.RabbitMQ.domain.UserInfo;
import EA.Project.RabbitMQ.dto.PostMessageInfo;
import EA.Project.RabbitMQ.repository.PostInfoRepository;
import EA.Project.RabbitMQ.repository.PostMessageInfoRepository;
import EA.Project.RabbitMQ.service.PostMessageInfoService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RabbitListener(queues = {"RBUserInfo", "RBUsers", "RBPost"})
public class UserListener {

    @Autowired
    PostInfoRepository postInfoRepository;
    @Autowired
    PostMessageInfoRepository postMessageInfoRepository;
    @Autowired
    PostMessageInfoService postMessageInfoService;

    @RabbitHandler
    public void save(@Payload UserInfo userInfo, @Header("operation") String operation) {
        System.out.println("received: " + userInfo.getFullName());
    }

    @RabbitHandler
    public void requestFriend(@Payload RelationshipInfo relationshipInfo, @Header("MessageType") String operation) {
        System.out.println("relationshipInfo: " + relationshipInfo.getA().getFullName());
        System.out.println("relationshipInfo: " + relationshipInfo.getB().getFullName());

    }

    @RabbitHandler
    public void post(@Payload PostInfo postInfo, @Header("MessageType") String operation) {
        System.out.println("postInfo: " + postInfo.getContent());
        System.out.println("postInfo: " + postInfo.getPostedBy().getFullName());


        List<UserInfo> userInfoList = postMessageInfoService.getAll(postInfo.getPostedBy().getEmail());

        System.out.println(userInfoList);
        postInfoRepository.save(postInfo);

        for (UserInfo userInfo : userInfoList) {

            postMessageInfoRepository.save(new PostMessageInfo(postInfo, userInfo.getEmail(), 1));
        }


    }
}
