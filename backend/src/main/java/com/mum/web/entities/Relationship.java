package com.mum.web.entities;

import com.mum.web.dtos.RelationshipInfo;
import com.mum.web.functional.AuthenticationFunctionUtils;

import java.util.List;

public class Relationship {
    User a;
    User b;
    List<User> users;

    public RelationStatus getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(RelationStatus relationStatus) {
        this.relationStatus = relationStatus;
    }

    RelationStatus relationStatus;

    public Relationship(User a, User b, List<User> users) {
        this.a = a;
        this.b = b;
        this.users = users;
    }

    public String getFirstName()
    {
        return a.getFirstName();
    }

    public String getLastName()
    {
        return a.getLastName();
    }


    public int getSize()
    {
        return this.users.size();
    }

    public User getA() {
        return a;
    }

    public void setA(User a) {
        this.a = a;
    }

    public User getB() {
        return b;
    }

    public void setB(User b) {
        this.b = b;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
// TODO[QUY]
//    public RelationshipInfo convertToInfo()
//    {
//        return new RelationshipInfo(AuthenticationFunctionUtils.convertToUserInfo.apply(a)
//        ,AuthenticationFunctionUtils.convertToUserInfo.apply(b)
//        ,AuthenticationFunctionUtils.converToListUserInfo.apply(users),users.size()
//        ,relationStatus);
//    }
}
