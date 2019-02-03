package com.zhouwenqi.apihub.service;

import com.zhouwenqi.apihub.core.entity.Directory;
import com.zhouwenqi.apihub.core.entity.Member;
import com.zhouwenqi.apihub.core.entity.Project;
import com.zhouwenqi.apihub.core.model.request.ReqDirectorySort;
import com.zhouwenqi.apihub.core.model.response.RspDirectory;
import com.zhouwenqi.apihub.core.repository.DirectoryRepository;
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
 * Service - 目录
 * Created by zhouwenqi on 2019/2/2.
 */
@Service("directoryService")
public class DirectoryService extends BaseService<Directory> {
    @Autowired
    private DirectoryRepository directoryRepository;
    /**
     * 判断目录是否已存在
     * @param directory
     * @param isExclude 是否排除当前目录名称
     * @return
     */
    public boolean isRepeat(Directory directory,boolean isExclude){
        Criteria criteria = Criteria.where("projectId").is(directory.getProjectId());
        criteria.and("name").is(directory.getName());
        if(null != directory.getDescription()){
            criteria.and("directoryId").is(directory.getDirectoryId());
        }
        if(null!=directory.getId() && isExclude){
            criteria.and("_id").ne(directory.getId());
        }
        Query query = new Query(criteria);
        return null != mongoTemplate.findOne(query,Directory.class);
    }

    /**
     * 获取目录排序
     * @param directory
     * @return
     */
    private int getIdx(Directory directory){
        Criteria criteria = Criteria.where("projectId").is(directory.getProjectId());
        criteria.and("directoryId").is(directory.getDirectoryId());
        Query query = new Query(criteria);
        query.with(new Sort(Sort.Direction.DESC,"idx"));
        int idx = 0;
        Directory sortDirectory = mongoTemplate.findOne(query,Directory.class);
        if(null != sortDirectory && null != sortDirectory.getIdx()){
            idx = sortDirectory.getIdx();
        }
        idx ++ ;
        return idx;
    }

    /**
     * 创建目录
     * @param directory 目录信息
     * @return
     */
    public Directory create(Directory directory){
        directory.setIdx(getIdx(directory));
        directory.setCreateDate(new Date());
        directory.setEditDate(new Date());
        directoryRepository.insert(directory);
        return directory;
    }

    /**
     * 更新目录
     * @param directory 目录信息
     * @return
     */
    public Directory update(Directory directory){
        directory.setEditDate(new Date());
        directoryRepository.save(directory);
        return directory;
    }

    /**
     * 目录排序
     * @param reqDirectorySort 排序信息
     * @param projectId 项目id
     */
    public void sort(ReqDirectorySort reqDirectorySort, ObjectId projectId){
        int i = 0;
        for(String id:reqDirectorySort.getIds()){
            Directory directory = findById(id);

            if(null == directory || !directory.getProjectId().equals(projectId)){
                break;
            }
            i++;
            directory.setIdx(i);
            directory.setDirectoryId(reqDirectorySort.getDirectionId());
            directory.setEditDate(new Date());
            directoryRepository.save(directory);
        }
    }

    /**
     * 获取项目目录列表
     * @param projectId 项目id
     * @return
     */
    public List<RspDirectory> findByProjectId(ObjectId projectId){
        Criteria criteria = Criteria.where("projectId").is(projectId);
        criteria.and("directoryId").exists(false);
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.sort(new Sort(Sort.Direction.ASC,"idx")));
        operations.add(Aggregation.match(criteria));
        operations.add(Aggregation.lookup("directory","_id","directoryId","directorys"));
        Aggregation aggregation = Aggregation.newAggregation(operations);
        AggregationResults<RspDirectory> results = mongoTemplate.aggregate(aggregation,"directory",RspDirectory.class);
        List<RspDirectory> list = results.getMappedResults();
        return null == list ? new ArrayList<RspDirectory>() : list;
    }
}
