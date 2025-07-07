package com.dio.task_manager.domain.spec;


import com.dio.task_manager.domain.Task;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TaskSpecifications {

    private TaskSpecifications(){}

    public static Specification<Task> hasUsername(String username) {
        return (root, query, cb) -> cb.equal(root.get("user").get("username"), username);
    }

    public static Specification<Task> hasTitleLike(String title) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Task> hasStatusId(Long statusId) {
        return (root, query, cb) -> cb.equal(root.get("status").get("id"), statusId);
    }

    public static Specification<Task> dueBefore(LocalDate dueDate) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("dueDate"), dueDate);
    }
}
