package com.system.Scheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class SchedulerService {
	
	 @Autowired
	 private WebClient webClient;
	 
	 

	@Scheduled(cron = "0 * * * * ?")
	public void checkq() {
		// TODO Auto-generated method stub
//		Mono<String> userResponse = webClient.post()
//			.uri("/BEService-app/saveUser")
//			.body(BodyInserters.fromValue(Mono.empty()))
//			.retrieve()
//			.bodyToMono(String.class);
		
		String userDetails = webClient.get().uri("/checkquantity/").retrieve().bodyToMono(String.class).block() ;
		System.out.println(userDetails);
	}
	
	@Scheduled(cron="0 * * * * ?")
	public void checkManagerStatus()
	{
		String userDetails= webClient.get().uri("/delete/").retrieve().bodyToMono(String.class).block();
		System.out.println(userDetails);
	}
	 
}
