package com.zhouwenqi.apihub.service;

import com.zhouwenqi.apihub.core.entity.Member;
import com.zhouwenqi.apihub.core.entity.Project;
import com.zhouwenqi.apihub.core.repository.ProjectRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service - 项目
 * Created by zhouwenqi on 2019/1/29.
 */
@Service("projectService")
public class ProjectService extends BaseService<Project> {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private MemberService memberService;

    /**
     * 检查项目是否存在
     * @param project 项目信息
     * @param isExclude 是否排除当前项目名称
     * @return
     */
    public boolean isRepeat(Project project,boolean isExclude){
        Criteria criteria = Criteria.where("userId").is(project.getUserId());
        criteria.and("name").is(project.getName());
        if(null!=project.getId() && isExclude){
            criteria.and("_id").ne(project.getId());
        }
        Query query = new Query(criteria);
        return null !=  mongoTemplate.findOne(query,Project.class);
    }
    /**
     * 判断用户是否属于项目成员
     * @param userId 用户id
     * @param projectId 项目id
     * @return
     */
    public boolean isMember(ObjectId userId, ObjectId projectId){
        Member member = memberService.find(userId,projectId);
        return null != member;
    }
    /**
     * 创建项目
     * @param project 项目信息
     */
    public void createProject(Project project){
        project.setCreateDate(new Date());
        project.setEditDate(new Date());
        projectRepository.insert(project);
    }

    /**
     * 更新项目
     * @param project 项目信息
     */
    public void updateProject(Project project){
        project.setEditDate(new Date());
        projectRepository.save(project);
    }

    /**
     * 查询用户创建的项目
     * @param userId 用户id
     * @return
     */
    public List<Project> findByCreate(ObjectId userId){
        Criteria criteria = Criteria.where("userId").is(userId);
        Query query = new Query(criteria);
        query.with(new Sort(Sort.Direction.DESC,"createDate"));
        List<Project> projects = mongoTemplate.find(query,Project.class);
        return null == projects ? new ArrayList<Project>() : projects;
    }

    /**
     * 查询用户参与的项目(不包含自己创建的项目)
     * @param userId 用户id
     * @return
     */
    public List<Project> findByJoin(ObjectId userId){
        LookupOperation lookupOperation = LookupOperation.newLookup().from("member").localField("_id").foreignField("projectId").as("member");
        Criteria criteria = Criteria.where("member.userId").is(userId);
        criteria.and("userId").ne(userId);
        AggregationOperation operation = Aggregation.match(criteria);
        Aggregation aggregation = Aggregation.newAggregation(lookupOperation,operation);
        AggregationResults<Project> results = mongoTemplate.aggregate(aggregation,"project",Project.class);
        List<Project> list = results.getMappedResults();
        return null != list ? list : new ArrayList<Project>();
    }

    /**
     * 查询用户参与的所有项目
     * @param userId 用户id
     * @return
     */
    public List<Project> findByAll(ObjectId userId){
        LookupOperation lookupOperation = LookupOperation.newLookup().from("member").localField("_id").foreignField("projectId").as("member");
        Criteria criteria = Criteria.where("member.userId").is(userId);
        AggregationOperation operation = Aggregation.match(criteria);
        Aggregation aggregation = Aggregation.newAggregation(lookupOperation,operation);
        AggregationResults<Project> results = mongoTemplate.aggregate(aggregation,"project",Project.class);
        List<Project> list = results.getMappedResults();
        return null != list ? list : new ArrayList<Project>();
    }
}
