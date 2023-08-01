package com.redis.docker.dto;


import lombok.Builder;

import java.io.Serializable;

@Builder
public record PlayerDto(
        Long id,
        String name,
        int age,
        String team,
        String country,
        int ranking
)implements Serializable {}
