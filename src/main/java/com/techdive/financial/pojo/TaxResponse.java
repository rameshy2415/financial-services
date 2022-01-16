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
public class TaxResponse {

	@JsonProperty("TAX_REQUEST")
	private TaxDetailsRequest taxDetailsRequest;

	@JsonProperty("TAX_RESPONSE")
	private TaxDetailsResponse taxDetailsResponse;

}
