package com.eazybytes.loans.entity;

import jakarta.persistence.*;
import lombok.*;


@Table(name ="loans", schema = "demo")
@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Loans extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long loanId;

	private String mobileNumber;

	private String loanNumber;

	private String loanType;

	private int totalLoan;

	private int amountPaid;

	private int outstandingAmount;
	
}
