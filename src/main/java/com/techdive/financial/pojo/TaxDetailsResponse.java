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
public class TaxDetailsResponse {
	@JsonProperty("TOTAL_TAXABLE_AMT")
	private Integer totalTaxableIncomeAmt;

	@JsonProperty("TOTAL_DEDU_EXEMPTION")
	private Integer totalDedAndExemption;

	@JsonProperty("TOTAL_TAX_AMT_DETAILS")
	private TotalTaxAmtDetails totalTaxAmtDetails;
}
