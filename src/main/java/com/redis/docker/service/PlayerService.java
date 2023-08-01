package com.redis.docker.service;

import com.redis.docker.dto.PlayerDto;
import com.redis.docker.entity.Player;
import com.redis.docker.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableCaching
@RequiredArgsConstructor
public class PlayerService {
    private static final String PLAYER_HASH_KEY = "PLAYER";
    private static final String LAST_QUERIED_PLAYER_KEY = "LAST_PLAYER_KEY";

    private final PlayerRepository playerRepository;
    private final RedisTemplate redisTemplate;

    public List<PlayerDto> getPlayers() {
        Iterable<Player> players = playerRepository.findAll();

        List<PlayerDto> playerRecords = new ArrayList<>();
        players.forEach(
                player -> {
                    PlayerDto playerRecord =
                            PlayerDto.builder()
                                    .id(player.getId())
                                    .name(player.getName())
                                    .age(player.getAge())
                                    .country(player.getCountry())
                                    .team(player.getTeam())
                                    .ranking(player.getRanking())
                                    .build();
                    playerRecords.add(playerRecord);
                });

        return playerRecords;
    }

    public PlayerDto getPlayer(Long id) {
        Player player =
                playerRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new ResponseStatusException(
                                                HttpStatus.NOT_FOUND, "Player not found with ID: " + id));
        cacheLastQueriedPlayer(player);
        return PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .team(player.getTeam())
                .age(player.getAge())
                .country(player.getCountry())
                .ranking(player.getRanking())
                .build();
    }

    private void cacheLastQueriedPlayer(Player player) {
        PlayerDto playerRecord = PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .team(player.getTeam())
                .age(player.getAge())
                .country(player.getCountry())
                .ranking(player.getRanking())
                .build();
        redisTemplate.opsForHash().put(PLAYER_HASH_KEY, LAST_QUERIED_PLAYER_KEY, playerRecord);
    }

    public PlayerDto getLastQueriedPlayer() {
        return (PlayerDto) redisTemplate.opsForHash().get(PLAYER_HASH_KEY, LAST_QUERIED_PLAYER_KEY);
    }
}
