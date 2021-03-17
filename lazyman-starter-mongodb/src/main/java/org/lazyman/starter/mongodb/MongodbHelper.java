package org.lazyman.starter.mongodb;

import cn.hutool.json.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class MongodbHelper {
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


    public UpdateResult update(Query query, Update update, String collection) {
        return mongoTemplate.updateMulti(query, update, collection);
    }

    public UpdateResult update(Query query, JSONObject param, String collection) {
        return mongoTemplate.updateMulti(query, buildUpdate(param), collection);
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

    private Update buildUpdate(JSONObject param) {
        Update update = new Update();
        for (String key : param.keySet()) {
            Object value = param.get(key);
            if (value != null) {
                update.set(key, value);
            }
        }
        return update;
    }
}
