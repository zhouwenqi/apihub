package com.zhouwenqi.apihub.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * Service - Basic
 * Created by zhouwenqi on 2018/9/21.
 */
public class BaseService<T> {
    @Autowired
    protected MongoTemplate mongoTemplate;

    /**
     * 查询单个实体
     * @param id 主键id对象
     * @return
     */
    public T findById(ObjectId id){
        return mongoTemplate.findById(id,getClassType());
    }

    /**
     * 查询单个实体
     * @param id 主键id字符串
     * @return
     */
    public T findById(String id){
        ObjectId objectId = new ObjectId(id);
        return findById(objectId);
    }

    /**
     * 查询实体列表
     * @param query 查询条件
     * @return
     */
    public List<T> find(Query query){
        return mongoTemplate.find(query,getClassType());
    }

    /**
     * 查询所有实体列表
     * @return
     */
    public List<T> findAll(){
        return mongoTemplate.findAll(getClassType());
    }

    /**
     * 插入单个实体
     * @param t 实体
     * @param <T> 实体类型
     */
    public <T> void insert(T t){
        mongoTemplate.insert(t);
    }

    /**
     * 插入单个实体并指定集合名
     * @param t 实体
     * @param collectionName 集合名
     * @param <T> 实体类型
     */
    public <T> void insert(T t,String collectionName){
        mongoTemplate.insert(t,collectionName);
    }

    /**
     * 批量插入实体列表
     * @param collection 实体集合
     */
    public void insert(Collection<? extends Object> collection){
        mongoTemplate.insert(collection,getClassType());
    }

    /**
     * 批量插入实体列表并指定集合名
     * @param collection 实体列表
     * @param collectionName 集合名
     */
    public void insert(Collection<? extends Object> collection,String collectionName){
        mongoTemplate.insert(collection,collectionName);
    }

    /**
     * 批量插入实体列表到默认文档中
     * @param collection 实体列表
     */
    public void insertAll(Collection<? extends Object> collection){
        mongoTemplate.insertAll(collection);
    }

    /**
     * 移除单个实体
     * @param t 实体
     * @param <T>
     */
    public <T> void remove(T t){
        mongoTemplate.remove(t);
    }

    /**
     * 移除指定集合名中的实体
     * @param t 实体
     * @param collectionName 集合名
     * @param <T>
     */
    public <T> void remove(T t,String collectionName){
        mongoTemplate.remove(t,collectionName);
    }

    /**
     * 指定条件移除实体
     * @param query 查询条件
     * @param <T>
     */
    public <T> void remove(Query query){
        mongoTemplate.remove(query,getClassType());
    }

    /**
     * 指定条件且指定集合名移除实体
     * @param query
     * @param collectionName
     */
    public void remove(Query query,String collectionName){
        mongoTemplate.remove(query,collectionName);
    }

    /**
     * 保存单个实体
     * @param t 实体
     * @param <T>
     */
    public <T> void save(T t){
        mongoTemplate.save(t);
    }

    /**
     * 指定集合名保存单个实体
     * @param t 实体
     * @param collectionName 集合名
     * @param <T>
     */
    public <T> void save(T t,String collectionName){
        mongoTemplate.save(t,collectionName);
    }

    /**
     * 更新查询结果第一条数据
     * @param query 查询条件
     * @param update 更新内容
     */
    public void updateFirst(Query query,Update update){
        mongoTemplate.updateFirst(query,update,getClassType());
    }

    /**
     * 更新查询结果第一条数据并指定集合名
     * @param query 查询条件
     * @param update 更新内容
     * @param collectionName 集合名
     */
    public void updateFirst(Query query,Update update,String collectionName){
        mongoTemplate.updateFirst(query,update,collectionName);
    }

    /**
     * 更新查询结果数据
     * @param query 查询条件
     * @param update 更新内容
     */
    public void updateMulti(Query query,Update update){
        mongoTemplate.updateMulti(query,update,getClassType());
    }

    /**
     * 更新查询结果数据并指定集合名
     * @param query 查询箱件
     * @param update 更新内容
     * @param collectionName 集合名
     */
    public void updateMulti(Query query,Update update,String collectionName){
        mongoTemplate.updateMulti(query,update,collectionName);
    }

    /**
     * 获取当前继承对象类型
     * @return
     */
    protected Class<T> getClassType(){
        Type type = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType)type;
        return (Class<T>)parameterizedType.getActualTypeArguments()[0];
    }
}
