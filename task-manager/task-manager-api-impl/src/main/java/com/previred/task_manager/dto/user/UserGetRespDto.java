package com.previred.task_manager.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserGetRespDto extends UserRespDto implements Serializable {
    private String name;
    @Override
    @JsonIgnore
    public String getToken() {
        return super.getToken();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
