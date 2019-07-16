package EA.Project.RabbitMQ.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
public class RelationshipInfo implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    private UserInfo a;
    private UserInfo b;
    @OneToMany
    private List<UserInfo> users;

    public RelationStatus getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(RelationStatus relationStatus) {
        this.relationStatus = relationStatus;
    }

    RelationStatus relationStatus;

    public Integer getMutualFriends() {
        return mutualFriends;
    }

    public void setMutualFriends(Integer mutualFriends) {
        this.mutualFriends = mutualFriends;
    }

    private Integer mutualFriends;

    public RelationshipInfo(UserInfo a, UserInfo b, List<UserInfo> users, Integer mutualFriends, RelationStatus relationStatus) {
        this.a = a;
        this.b = b;
        this.users = users;
        this.mutualFriends = mutualFriends;
        this.relationStatus = relationStatus;
    }

    public UserInfo getA() {
        return a;
    }

    public void setA(UserInfo a) {
        this.a = a;
    }

    public UserInfo getB() {
        return b;
    }

    public void setB(UserInfo b) {
        this.b = b;
    }

    public List<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }
}
