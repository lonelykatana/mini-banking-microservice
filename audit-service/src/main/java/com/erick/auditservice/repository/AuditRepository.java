package com.erick.auditservice.repository;

import com.erick.auditservice.entity.AuditLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends MongoRepository<AuditLog, String> {


}
