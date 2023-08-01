package com.redis.docker.repository;

import com.redis.docker.entity.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player,Long> {}
