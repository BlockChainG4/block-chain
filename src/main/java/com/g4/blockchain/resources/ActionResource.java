package com.g4.blockchain.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping("action")
@Produces(MediaType.APPLICATION_JSON)
public class ActionResource {
}
