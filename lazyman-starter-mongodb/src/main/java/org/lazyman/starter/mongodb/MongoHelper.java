package org.lazyman.starter.mongodb;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.lazyman.common.util.ThreadLocalUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class MongoHelper {
    private MongoTemplate mongoTemplate;

    public <T> T save(T t, String collection) {
        return mongoTemplate.save(t, collection);
    }

    public <T> Collection<T> saveBatch(Collection<T> t, String collection) {
        return mongoTemplate.insert(t, collection);
    }

    public DeleteResult delete(Query query, String collection) {
        return mongoTemplate.remove(query, collection);
    }

    public DeleteResult deleteByUserId(String collection) {
        return mongoTemplate.remove(buildQueryUserId(), collection);
    }

    public UpdateResult update(Query query, Update update, String collection) {
        return mongoTemplate.updateMulti(query, update, collection);
    }

    private Update buildUpdate(Map<String, Object> object) {
        Update update = new Update();
        for (String key : object.keySet()) {
            Object value = object.get(key);
            if (value != null) {
                update.set(key, value);
            }
        }
        return update;
    }

    public UpdateResult update(Map<String, Object> map, String collection) {
        return mongoTemplate.updateMulti(buildQueryUserId(), buildUpdate(map), collection);
    }

    public UpdateResult update(Query query, Map<String, Object> map, String collection) {
        return mongoTemplate.updateMulti(query, buildUpdate(map), collection);
    }

    public Long count(Query query, Class<?> entityClass, String collection) {
        return mongoTemplate.count(query, entityClass, collection);
    }

    public <T> T findOne(Query query, Class<T> entityClass, String collection) {
        return mongoTemplate.findOne(query, entityClass, collection);
    }

    public <T> List<T> find(Query query, Class<T> entityClass, String collection) {
        return mongoTemplate.find(query, entityClass, collection);
    }

    public <T> T findByUserId(Class<T> entityClass, String collection) {
        return findOne(buildQueryUserId(), entityClass, collection);
    }

    public <T> List<T> findAllByUserId(Class<T> entityClass, String collection) {
        return find(buildQueryUserId(), entityClass, collection);
    }

    public <T> T findFirst(String param, Class<T> entityClass, String collection) {
        return findOne(buildQuerySort(false, param), entityClass, collection);
    }

    public <T> T findLast(String param, Class<T> entityClass, String collection) {
        return findOne(buildQuerySort(true, param), entityClass, collection);
    }

    public Query buildQueryUserId() {
        return new Query(Criteria.where("userId").is(ThreadLocalUtils.getCurrentUserId()));
    }

    public Query buildQuerySort(Boolean isDesc, String field) {
        Query query = buildQueryUserId().limit(1);
        if (isDesc) {
            query.with(Sort.by(Sort.Order.desc(field)));
        } else {
            query.with(Sort.by(Sort.Order.asc(field)));
        }
        return query;
    }
}
