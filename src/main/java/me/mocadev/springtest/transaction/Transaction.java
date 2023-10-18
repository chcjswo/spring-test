package me.mocadev.springtest.transaction;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.mocadev.springtest.account.Account;

@NoArgsConstructor
@Getter
@Table(name = "transaction_tb")
@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Account withdrawAccount; // 2222

	@ManyToOne(fetch = FetchType.LAZY)
	private Account depositAccount; // 1111

	@Column(nullable = false)
	private Long amount;

	private Long withdrawAccountBalance;
	private Long depositAccountBalance;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	@Builder
	public Transaction(Long id, Account withdrawAccount, Account depositAccount, Long amount, Long withdrawAccountBalance, Long depositAccountBalance, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.withdrawAccount = withdrawAccount;
		this.depositAccount = depositAccount;
		this.amount = amount;
		this.withdrawAccountBalance = withdrawAccountBalance;
		this.depositAccountBalance = depositAccountBalance;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
