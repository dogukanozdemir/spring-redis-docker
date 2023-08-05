package com.redis.docker.controller;

import com.redis.docker.dto.PlayerDto;
import com.redis.docker.entity.Player;
import com.redis.docker.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/players")
    public ResponseEntity<List<PlayerDto>> getAllPlayers() {
        return ResponseEntity.ok(playerService.getPlayers());
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.getPlayer(id));
    }

    @GetMapping("/players/last")
    public ResponseEntity<PlayerDto> getLastQueriedPlayer(){
        return ResponseEntity.ok(playerService.getLastQueriedPlayer());
    }


}
