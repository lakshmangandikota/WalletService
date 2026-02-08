package com.dino.java.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dino.java.dao.IdempotencyRepository;
import com.dino.java.dao.LedgerRepository;
import com.dino.java.dao.WalletRepository;
import com.dino.java.pojo.Wallet;

@Service
public class WalletServiceImpl implements WalletService {

	private final WalletRepository walletRepository;
	private final LedgerRepository ledgerRepository;
	private final IdempotencyRepository idempotencyRepository;

	public WalletServiceImpl(WalletRepository walletRepository, LedgerRepository ledgerRepository,
			IdempotencyRepository idempotencyRepository) {
		this.walletRepository = walletRepository;
		this.ledgerRepository = ledgerRepository;
		this.idempotencyRepository = idempotencyRepository;
	}

	@Transactional
	public void topup(String key, long userId, String asset, BigDecimal amount) {
		process(key, 1, userId, asset, amount);
	}

	@Transactional
	public void bonus(String key, long userId, String asset, BigDecimal amount) {
		process(key, 1, userId, asset, amount);
	}

	@Transactional
	public void spend(String key, long userId, String asset, BigDecimal amount) {
		process(key, userId, 1, asset, amount);
	}

	private void process(String key, long fromOwner, long toOwner, String asset, BigDecimal amount) {
		if (!idempotencyRepository.acquire(key)) {
			return;
		}

		var txId = UUID.randomUUID();
		Wallet from = walletRepository.lockWallet(fromOwner, asset);
		Wallet to = walletRepository.lockWallet(toOwner, asset);

		if (from == null) {
			throw new RuntimeException("No Record Found.");
		}

		if (from.getBalance().compareTo(amount) < 0) {
			throw new RuntimeException("INSUFFICIENT BALANCE");
		}

		walletRepository.updateBalance(from.getId(), amount.negate());
		walletRepository.updateBalance(to.getId(), amount);

		ledgerRepository.insert(txId, from.getId(), "DEBIT", amount);
		ledgerRepository.insert(txId, to.getId(), "CREDIT", amount);

		idempotencyRepository.complete(key);
	}
}