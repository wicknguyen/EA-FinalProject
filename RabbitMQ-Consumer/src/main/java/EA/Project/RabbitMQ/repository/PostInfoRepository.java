package EA.Project.RabbitMQ.repository;

import EA.Project.RabbitMQ.domain.PostInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostInfoRepository extends JpaRepository<PostInfo,Long> {
}
