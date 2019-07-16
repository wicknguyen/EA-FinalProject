package EA.Project.RabbitMQ.repository;

import EA.Project.RabbitMQ.dto.PostMessageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostMessageInfoRepository extends JpaRepository<PostMessageInfo,Long> {

    @Query("SELECT p FROM PostMessageInfo p WHERE p.userId= :userId order by p.postInfo.postedDate DESC")
    List<PostMessageInfo> myFindPostByUserId(
            @Param("userId") String userId);
}
