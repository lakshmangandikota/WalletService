package com.dino.java.service;

import java.math.BigDecimal;

public interface WalletService {

	void topup(String key, long userId, String asset, BigDecimal amount);

	void bonus(String key, long userId, String asset, BigDecimal amount);

	void spend(String key, long userId, String asset, BigDecimal amount);

}
