package EA.Project.RabbitMQ.service;

import EA.Project.RabbitMQ.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PostMessageInfoService {

    @Autowired
    private RestTemplate restTemplate;
    private final String personUrl = "http://localhost:8080/person/{id}";
    private final String pplUrl = "http://localhost:8080/persons";
    private final String pplUrlAdd = "http://localhost:8080/person/add";
    private final String pplUrlAddRedirect = "http://localhost:8080/person/redirect";


    public UserInfo get(Long id) {
        return restTemplate.getForObject(personUrl, UserInfo.class,id);
    }

    public List<UserInfo> getAll(String userId) {
        String url = "http://localhost:8080/api/testFriend/"+ userId;
        System.out.println(url);

        ResponseEntity<List<UserInfo>> response =
                restTemplate.exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<UserInfo>>() {
                        });


        return response.getBody();
    }

}
