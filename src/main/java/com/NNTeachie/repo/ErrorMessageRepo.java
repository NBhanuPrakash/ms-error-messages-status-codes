package com.NNTeachie.repo;

import com.NNTeachie.model.ErrorMessages;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ErrorMessageRepo extends MongoRepository<ErrorMessages,String> {
    List<ErrorMessages> findByStatusCode(Integer statusCode);
}
