package com.tecnova.nuevospa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tecnova.nuevospa.entity.StatusTask;
import com.tecnova.nuevospa.repository.common.BaseRepository;
/**
 * Repository that manages the information about the status task {@link StatusTask}.<br>
 * 
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Repository
public interface StatusTaskRepository extends BaseRepository<StatusTask>, JpaRepository<StatusTask, Long>{


}