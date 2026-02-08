package com.dino.java.dao;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
public class LedgerRepository {
	private final JdbcOperations jdbcOperations;

	public LedgerRepository(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	public void insert(UUID tx, long walletId, String type, BigDecimal amount) {
		String sql = "Insert Into Ledger_Entries(Transaction_Id, Wallet_Id, Entry_Type, Amount) Values (?, ?, ?, ?)";

		this.jdbcOperations.update(sql, tx, walletId, type, amount);
	}
}
