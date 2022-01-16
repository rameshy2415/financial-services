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
public class TotalTaxAmtDetails {
	@JsonProperty("ZERO_PER_AMT")
	private Integer zeroPerAmount;

	@JsonProperty("FIVE_PER_AMT")
	private Integer fivePerAmount;

	@JsonProperty("TWENTY_PER_AMT")
	private Integer twentyPerAmount;

	@JsonProperty("THIRTY_PER_AMT")
	private Integer thirtyPerAmount;

	@JsonProperty("REBATE_87A_AMT")
	private Integer rebate87AAmount;

	@JsonProperty("EDU_CESS_AMT")
	private Integer eduCesAmount;

	@JsonProperty("SURCHARGE_AMT")
	private Integer surchargeAmount;
	
	@JsonProperty("LEFT_AMT")
	private Integer remaningAfterThirtyPerAmount;
	
	@JsonProperty("TAX_ON_TOTAL_AMT")
	private Integer taxOnTotalIncome;
	
	@JsonProperty("FINAL_TAX_AMT")
	private Integer finalTaxAmt;
}
