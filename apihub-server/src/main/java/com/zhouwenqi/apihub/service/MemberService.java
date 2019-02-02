package com.zhouwenqi.apihub.service;

import com.zhouwenqi.apihub.core.entity.Authority;
import com.zhouwenqi.apihub.core.entity.Member;
import com.zhouwenqi.apihub.core.model.enums.RoleLevel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service - 项目成员
 * Created by zhouwenqi on 2019/1/29.
 */
@Service("memberService")
public class MemberService extends BaseService<Member> {
    /**
     * 查询成员
     * @param userId 帐号id
     * @param projectId 项目id
     * @return
     */
    public Member find(String userId,String projectId){
        return find(new ObjectId(userId),new ObjectId(projectId));
    }

    /**
     * 查询成员
     * @param userId 帐号ObjectId
     * @param projectId 项目ObjectId
     * @return
     */
    public Member find(ObjectId userId,ObjectId projectId){
        Criteria criteria = Criteria.where("userId").is(userId);
        criteria.and("projectId").is(projectId);
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query,Member.class);
    }
    /**
     * 创建项目成员
     * @param userId 用户id
     * @param projectId 项目id
     * @param roleLevel 成员级别
     * @return
     */
    public Member join(ObjectId userId, ObjectId projectId, RoleLevel roleLevel){
        Member member = find(userId,projectId);
        if(null == member){
            member = new Member();
            member.setUserId(userId);
            member.setProjectId(projectId);
            member.setEditDate(new Date());
            member.setCreateDate(new Date());
            member.setRoleLevel(roleLevel);
            member.setAuthority(buildAuthority(roleLevel));
            mongoTemplate.insert(member,"member");
        }
        return member;
    }
    /**
     * 查询项目成员
     * @param projectId 项目id
     * @return
     */
    public List<Member> findByProjectId(ObjectId projectId){
        Criteria criteria = Criteria.where("projectId").is(projectId);
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(criteria));
        operations.add(Aggregation.lookup("user","userId","_id","user"));
        operations.add(Aggregation.unwind("user"));
        Aggregation aggregation = Aggregation.newAggregation(operations);
        AggregationResults<Member> results = mongoTemplate.aggregate(aggregation,"member",Member.class);
        List<Member> list = results.getMappedResults();
        return null == list ? new ArrayList<Member>() : list;
    }

    /**
     * 查询项目成员
     * @param projectId 项目字符串id
     * @return
     */
    public List<Member> findByProjectId(String projectId){
        ObjectId id = new ObjectId(projectId);
        return findByProjectId(id);
    }

    private Authority buildAuthority(RoleLevel roleLevel){
        Authority authority = new Authority();
        authority.setCreateApi(true);
        authority.setCreateDirectory(true);
        authority.setCreateMember(true);

        authority.setModifyProject(true);
        authority.setModifyApi(true);
        authority.setModifyDirectory(true);
        authority.setModifyMember(true);

        authority.setDeleteApi(true);
        authority.setDeleteDirectory(true);
        authority.setDeleteMember(true);

        authority.setCreateDate(new Date());
        authority.setEditDate(new Date());

        switch (roleLevel){
            case BUILD:
                authority.setCreateMember(false);
                authority.setModifyMember(false);
                authority.setDeleteMember(false);
                break;
            case FONTEND:
            case TEST:
                authority.setCreateApi(false);
                authority.setCreateDirectory(false);
                authority.setCreateMember(false);

                authority.setModifyProject(false);
                authority.setModifyApi(false);
                authority.setModifyDirectory(false);
                authority.setModifyMember(false);

                authority.setDeleteApi(false);
                authority.setDeleteDirectory(false);
                authority.setDeleteMember(false);

                authority.setCreateDate(new Date());
                authority.setEditDate(new Date());
                break;
            case MANAGER:
            default:
                break;
        }
        return authority;
    }
}
