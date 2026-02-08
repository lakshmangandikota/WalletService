package com.dino.java.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dino.java.service.WalletService;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

	private static final String SUCCESS = "SUCCESS";
	
	private final WalletService walletService;

	public WalletController(WalletService walletService) {
		this.walletService = walletService;
	}

	@PostMapping("/topup")
	public String topup(@RequestHeader("Idempotency-Key") String key, @RequestParam long userId,
			@RequestParam String asset, @RequestParam BigDecimal amount) {
		this.walletService.topup(key, userId, asset, amount);
		
		return SUCCESS;
	}

	@PostMapping("/bonus")
	public String bonus(@RequestHeader("Idempotency-Key") String key, @RequestParam long userId,
			@RequestParam String asset, @RequestParam BigDecimal amount) {
		this.walletService.bonus(key, userId, asset, amount);
		
		return SUCCESS;
	}

	@PostMapping("/spend")
	public String spend(@RequestHeader("Idempotency-Key") String key, @RequestParam long userId,
			@RequestParam String asset, @RequestParam BigDecimal amount) {
		this.walletService.spend(key, userId, asset, amount);
		
		return SUCCESS;
	}
}