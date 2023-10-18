package me.mocadev.springtest.user;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "user_tb")
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false, length = 20)
	private String username;

	@Column(nullable = false, length = 60)
	private String password;

	@Column(nullable = false, length = 20)
	private String email;
	@Column(nullable = false, length = 20)
	private String fullName;

	private String role;

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
