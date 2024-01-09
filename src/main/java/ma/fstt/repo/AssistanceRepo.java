package ma.fstt.repo;

import ma.fstt.entity.AssistanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssistanceRepo extends JpaRepository<AssistanceEntity, Long> {
    @Query("SELECT a FROM AssistanceEntity a WHERE a.userId = :userId")
    List<AssistanceEntity> findByUserId(@Param("userId") Long userId);

}