package com.app.account.dao;

import com.app.account.models.History;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoryRepository extends MongoRepository<History,String> {
}
