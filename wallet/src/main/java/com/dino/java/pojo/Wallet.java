package com.dino.java.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class Wallet implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private BigDecimal balance;

	public Wallet(long id, BigDecimal balance) {
		this.id = id;
		this.balance = balance;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
}
