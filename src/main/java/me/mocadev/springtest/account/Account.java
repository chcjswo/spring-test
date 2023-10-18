package me.mocadev.springtest.account;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.mocadev.springtest.user.User;

@NoArgsConstructor
@Getter
@Table(name = "account_tb", indexes = {
	@Index(name = "idx_account_number", columnList = "number")
})
@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@Column(unique = true, nullable = false, length = 4)
	private Integer number; // 계좌번호
	@Column(nullable = false, length = 4)
	private Integer password; // 계좌비번
	@Column(nullable = false)
	private Long balance; // 잔액 (기본값 1000원)

	@Column(nullable = false)
	private Boolean status; // true, false

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
}
