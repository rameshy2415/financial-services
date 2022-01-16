package com.techdive.financial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techdive.financial.pojo.TaxDetailsRequest;
import com.techdive.financial.pojo.TaxResponse;
import com.techdive.financial.service.impl.SalariedIncomTaxCalServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("incometax")
@Slf4j
public class SalariedIncomTaxCalController {

	@Autowired
	private SalariedIncomTaxCalServiceImpl salariedIncomTaxCalServiceImpl;

	@PostMapping("/individual-tax-cal")
	public ResponseEntity<TaxResponse> calculateIncomeTax(@RequestBody TaxDetailsRequest taxDetailsRequest) {
		log.info("Request  details: {}", taxDetailsRequest);
		TaxResponse res = salariedIncomTaxCalServiceImpl.getIndividualTax(taxDetailsRequest);
		log.info("Response  details: {}", res);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
