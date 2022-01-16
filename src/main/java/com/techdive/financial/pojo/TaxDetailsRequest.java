package com.techdive.financial.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaxDetailsRequest {
	@JsonProperty("GROSS_AMT")
	private Double grossAmount;

	@JsonProperty("OTH_INCOME_AMT")
	private Double otherIncomeAmt;

	@JsonProperty("HRA_EXEMPTION")
	private Double hraExemptionAmount;

	@JsonProperty("STD_DEDU_AMT")
	private Double stdDeducationAmt;

	@JsonProperty("PROF_TAX")
	private Double professionalTaxAmt;

	@JsonProperty("80C_AMT")
	private Double amount80C;

	@JsonProperty("NPS_80CCB_AMT")
	private Double amount80ccbNps;

	@JsonProperty("80D_HEALTH_INSU")
	private Double amount80d_healthInsu;

	@JsonProperty("HOME_LOAN_INTREST")
	private Double homeLoanIntrest;

	@JsonProperty("80TTA_SB_INTREST")
	private Double amountSBInt_80TTA;
}
