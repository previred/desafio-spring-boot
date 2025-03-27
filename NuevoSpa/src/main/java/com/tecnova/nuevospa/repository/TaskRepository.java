package com.tecnova.nuevospa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tecnova.nuevospa.entity.Task;
import com.tecnova.nuevospa.repository.common.BaseRepository;
/**
 * Repository that manages the information about the task {@link Task}.<br>
 * 
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Repository
public interface TaskRepository extends BaseRepository<Task>, JpaRepository<Task, Long>{


}