package com.taskmanager.spring.Domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>
{
    List<Task> findAllByUserid(Long userid);
    Task findByTaskidAndUserid(Long taskid, Long userid);
    @Query("SELECT t FROM Task t WHERE t.body LIKE ?1 AND t.userid = ?2 ")
    List<Task> searchBodies(String searchstring, Long userid);
}
