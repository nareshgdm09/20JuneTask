package com.task.webservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.entity.Player;
import com.task.exception.RecordNotFoundException;
import com.task.service.PlayerWebService;

@RestController
@RequestMapping("/api")
public class PlayerWebController {
	@Autowired
	PlayerWebService playerWebService;

	@PostMapping(path = "/players", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Player> playerRestservice(@RequestBody Player player){
		System.out.println("Request coming");
		if(playerWebService.findPlayer(player).isEmpty())
			throw new RecordNotFoundException("Player Record not found :"+player);
		return playerWebService.findPlayer(player);

	}
}