package model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="account_number")
	private String accountNumber;

	private float balance;

	public Account() {
	}

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public float getBalance() {
		return this.balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

}