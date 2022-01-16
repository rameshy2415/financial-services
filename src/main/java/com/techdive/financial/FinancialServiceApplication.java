package com.techdive.financial;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.techdive.financial.service.impl.SalariedIncomTaxCalServiceImpl;

@SpringBootApplication
public class FinancialServiceApplication implements CommandLineRunner {

	@Autowired
	private SalariedIncomTaxCalServiceImpl incomTaxCalServiceImpl;

	public static void main(String[] args) {
		SpringApplication.run(FinancialServiceApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		try {
			Map<String, Double> taxAmtMap = incomTaxCalServiceImpl.getTaxableIncome(10000000d, 1000000d, 10d, 50000d,
					10d, 10d, 10d, 10d, 10d, 0d);
			incomTaxCalServiceImpl.getTaxAmount(taxAmtMap.get("TOT_TAXABLE"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
