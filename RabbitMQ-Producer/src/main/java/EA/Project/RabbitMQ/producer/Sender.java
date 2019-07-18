package EA.Project.RabbitMQ.producer;

import EA.Project.RabbitMQ.domain.PostInfo;
import EA.Project.RabbitMQ.domain.RelationshipInfo;
import EA.Project.RabbitMQ.domain.UserInfo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {
    @Autowired
    private RabbitTemplate template;


    public void SendPost(PostInfo msg) {
        String queue = "RBPost";
        template.convertAndSend(queue, msg, m -> {

            m.getMessageProperties().getHeaders().put("MessageType", "PostInfo");
            return m;
        });
        System.out.println("Sent: " + msg + " to: " + queue);
    }


    public void SendRequestFriend(RelationshipInfo msg) {
        String queue = "RBUsers";
        template.convertAndSend(queue, msg, m -> {

            m.getMessageProperties().getHeaders().put("MessageType", "RelationshipInfo");
            return m;
        });
        System.out.println("Sent: " + msg + " to: " + queue);
    }


    public void SendUserInfo(UserInfo msg) {
        String queue = "RBUserInfo";
        template.convertAndSend(queue, msg, m -> {

            m.getMessageProperties().getHeaders().put("operation", "add");
            return m;
        });
        System.out.println("Sent: " + msg + " to: " + queue);
    }


}
