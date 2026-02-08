package com.dino.java.dao;

import java.math.BigDecimal;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import com.dino.java.pojo.Wallet;

@Repository
public class WalletRepository {

	private final JdbcOperations jdbcOperations;

	public WalletRepository(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	/**
	 * Locks the wallet row to avoid race conditions. Must be called inside
	 * a @Transactional method.
	 */
	public Wallet lockWallet(long ownerId, String assetCode) {
		String sql = """
				    Select w.Id, w.Balance
				    From wallets w
				    Join Asset_Types a on w.Asset_Type_Id = a.Id
				    Where w.Owner_Id = ? and a.Code = ?
				    FOR UPDATE
				""";

		try {
			return this.jdbcOperations.queryForObject(sql,
					(rs, rowNum) -> new Wallet(rs.getLong("Id"), rs.getBigDecimal("Balance")), ownerId, assetCode);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Updates wallet balance by delta (positive or negative).
	 */
	public void updateBalance(long walletId, BigDecimal balance) {
		this.jdbcOperations.update("Update Wallets Set Balance = Balance + ? Where Id = ?", balance, walletId);
	}

	/**
	 * Returns current wallet balance.
	 */
	public BigDecimal getBalance(long ownerId, String assetCode) {
		String sql = """
				    Select w.Balance
				    From Wallets w
				    join Asset_Types a on w.Asset_Type_Id = a.Id
				    Where w.Owner_Id = ? and a.Code = ?
				""";

		return this.jdbcOperations.queryForObject(sql, BigDecimal.class, ownerId, assetCode);
	}
}
