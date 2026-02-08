package com.dino.java.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
public class IdempotencyRepository {
	private final JdbcOperations jdbcOperations;

	public IdempotencyRepository(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	public boolean acquire(String key) {
		String sql = "Insert Into Idempotency_Keys(Idempotency_Key, Status) Values (?, ?)";

		try {
			return this.jdbcOperations.update(sql, key, "IN_PROGRESS") == 1;
		} catch (Exception e) {
			return false;
		}
	}

	public void complete(String key) {
		this.jdbcOperations.update("Update Idempotency_Keys Set Status = ? Where Idempotency_Key = ?", "COMPLETED",
				key);
	}
}
