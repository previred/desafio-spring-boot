package com.previred.task_manager.dto.user.token;

import io.swagger.v3.oas.annotations.media.Schema;
public record LoginRespDto(
    @Schema(name = "token",description = "token JWT",example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwNWIwNGFhZi1jYWU1LTQwY2UtYTkyYS04NWJhNGQxYzgyNmQiLCJpYXQiOjE3NDM3Mjk0MDEsImV4cCI6MTc0MzgxNTgwMX0.c9SZycHBCfym5h5OW8zpuCmJ06oB6xiEGUQf1-zi2jY")
    String token
){}
