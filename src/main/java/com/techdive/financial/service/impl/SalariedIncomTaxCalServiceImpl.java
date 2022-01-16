package com.techdive.financial.service.impl;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.techdive.financial.pojo.TaxDetailsRequest;
import com.techdive.financial.pojo.TaxDetailsResponse;
import com.techdive.financial.pojo.TaxResponse;
import com.techdive.financial.pojo.TotalTaxAmtDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SalariedIncomTaxCalServiceImpl {

	public TaxResponse getIndividualTax(TaxDetailsRequest taxDetailsRequest) {
		log.info("Individual Income tax calculation started");
		TaxResponse response = new TaxResponse();
		response.setTaxDetailsRequest(taxDetailsRequest);
		Map<String, Double> taxAmtMap = this.getTaxableIncome(taxDetailsRequest.getGrossAmount(),
				taxDetailsRequest.getOtherIncomeAmt(), taxDetailsRequest.getHraExemptionAmount(),
				taxDetailsRequest.getStdDeducationAmt(), taxDetailsRequest.getProfessionalTaxAmt(),
				taxDetailsRequest.getAmount80C(), taxDetailsRequest.getAmount80ccbNps(),
				taxDetailsRequest.getAmount80d_healthInsu(), taxDetailsRequest.getHomeLoanIntrest(),
				taxDetailsRequest.getAmountSBInt_80TTA());
		TaxDetailsResponse taxDetails = new TaxDetailsResponse();
		Integer taxableAmt = (int) Math.round(taxAmtMap.get("TOT_TAXABLE"));
		taxDetails.setTotalTaxableIncomeAmt(taxableAmt > 0 ? taxableAmt : 0);

		if (taxableAmt > 0) {
			taxDetails.setTotalTaxAmtDetails(getTaxAmount(taxAmtMap.get("TOT_TAXABLE")));
		} else {
			TotalTaxAmtDetails totalTaxAmtDetails = new TotalTaxAmtDetails();
			totalTaxAmtDetails.setEduCesAmount(0);
			totalTaxAmtDetails.setFinalTaxAmt(0);
			totalTaxAmtDetails.setFivePerAmount(0);
			totalTaxAmtDetails.setRebate87AAmount(0);
			totalTaxAmtDetails.setRemaningAfterThirtyPerAmount(0);
			totalTaxAmtDetails.setSurchargeAmount(0);
			totalTaxAmtDetails.setTaxOnTotalIncome(0);
			totalTaxAmtDetails.setThirtyPerAmount(0);
			totalTaxAmtDetails.setTwentyPerAmount(0);
			totalTaxAmtDetails.setZeroPerAmount(0);
			taxDetails.setTotalTaxAmtDetails(totalTaxAmtDetails);

		}

		taxDetails.setTotalDedAndExemption((int) Math.round(taxAmtMap.get("TOT_DEDU")));
		taxDetails.getTotalTaxAmtDetails().setRemaningAfterThirtyPerAmount(taxableAmt > 0 ? taxableAmt : 0);

		response.setTaxDetailsResponse(taxDetails);
		log.info("Individual Income tax calculation is completed");
		return response;
	}

	public Map<String, Double> getTaxableIncome(Double grossAmount, Double otherIncome, Double hraExemption,
			Double stdAmountDedu, Double professionalTax, Double amount80c, Double amount80ccb_nps,
			Double amount80d_healthInsu, Double homeLoanIntrest, Double amountSBInt_80TTA) {
		log.info("Getting taxable amount");
		Map<String, Double> taxDetMap = new ConcurrentHashMap<>();
		DecimalFormat df = new DecimalFormat("#");
		df.setMaximumFractionDigits(8);

		log.info("Gross amount: {}", df.format(grossAmount));
		log.info("Other Income amount: {}", otherIncome);
		log.info("HRA Exemption amount: {}", hraExemption);
		log.info("Standard deduction amount: {}", stdAmountDedu);
		log.info("Professional Tax deduction amount: {}", professionalTax);
		log.info("80C declared amount: {}", amount80c);
		log.info("NPS_80CCB declared amount: {}", amount80ccb_nps);
		log.info("Health Insurance amount: {}", amount80d_healthInsu);
		log.info("Home Loan Intrest amount: {}", homeLoanIntrest);
		log.info("SB Intrest amount: {}", amountSBInt_80TTA);

		Double totalDeduction = hraExemption + stdAmountDedu + professionalTax + amount80c + amount80ccb_nps
				+ amount80d_healthInsu + homeLoanIntrest + amountSBInt_80TTA;
		Double finalTaxableAmount = grossAmount + otherIncome - totalDeduction;

		taxDetMap.put("TOT_DEDU", totalDeduction);
		taxDetMap.put("TOT_TAXABLE", finalTaxableAmount);
		log.info("Total deduction and exemption  amount: {}", Math.round(totalDeduction));
		log.info("Taxable amount calcullated: {}", df.format(finalTaxableAmount));
		return taxDetMap;

	}

	public TotalTaxAmtDetails getTaxAmount(Double taxableAmount) {
		TotalTaxAmtDetails totalTaxAmtDetails = new TotalTaxAmtDetails();
		Double surCharges = 0d;
		Double rebate87A = 0d;
		Double taxAmount = 0d;
		Double tax5Amount = 0d;
		Double tax20Amount = 0d;
		Double tax30Amount = 0d;
		Double fullTaxAmount = taxableAmount;
		Double noSlabAmt = 250000d; // 0%
		Double firstSlabAmt = 500000d; // 5%
		Double secondSlabAmt = 1000000d; // 20%
		Double thirdSlabAmt = 1000000d; // 30%
		log.info("Total Taxable amount: {}", Math.round(taxableAmount));
		if (fullTaxAmount > taxAmount) {
			taxAmount = 0d;
			taxableAmount = taxableAmount - 250000d;
			log.info("Tax on first 250000 0%: {}", Math.round(taxAmount));
			totalTaxAmtDetails.setZeroPerAmount(0);
		}

		if (taxableAmount < 250000d && taxableAmount > 0d) {
			tax5Amount = taxableAmount * 0.05;
			log.info("2500001 -- 500000 5%: {}", Math.round(tax5Amount));
			taxableAmount = taxableAmount - 250000d;
		}
		if (taxableAmount > 250000d) {
			taxableAmount = taxableAmount - 250000d;
			tax5Amount = 12500d;
			log.info("2500001 -- 500000 5%: {}", Math.round(tax5Amount));
		}

		if (taxableAmount < 500000d && taxableAmount > 0d) {
			tax20Amount = taxableAmount * 0.2;
			log.info("500001 -- 1000000 20%: {}", Math.round(tax20Amount));
			taxableAmount = taxableAmount - 500001d;
		}
		if (taxableAmount > 500000d) {
			taxableAmount = taxableAmount - 500001d;
			tax20Amount = 100000d;
			log.info("500001 -- 1000000 20%: {}", Math.round(tax20Amount));
		}

		if (taxableAmount > 0d) {
			tax30Amount = taxableAmount * 0.3;
			log.info("1000001 -- {} 30%: {}", Math.round(fullTaxAmount), Math.round(tax30Amount));
		}

		totalTaxAmtDetails.setFivePerAmount((int) Math.round(tax5Amount));
		totalTaxAmtDetails.setTwentyPerAmount((int) Math.round(tax20Amount));
		totalTaxAmtDetails.setThirtyPerAmount((int) Math.round(tax30Amount));
		totalTaxAmtDetails.setRemaningAfterThirtyPerAmount((int) Math.round(fullTaxAmount));
		taxAmount = tax5Amount + tax20Amount + tax30Amount;
		log.info("Tax on total income: {}", Math.round(taxAmount));

		totalTaxAmtDetails.setTaxOnTotalIncome((int) Math.round(taxAmount));

		log.info("Rebate under sec 87A: {}", Math.round(rebate87A));

		Double eduCess = 0d;
		if (fullTaxAmount > 500000d) {
			surCharges = this.getSurcharges(taxAmount, fullTaxAmount);
			eduCess = this.getEduCess(taxAmount); // taxAmount * 0.04
		}

		log.info("Surcharge on tax: {}", Math.round(surCharges));
		totalTaxAmtDetails.setSurchargeAmount((int) Math.round(surCharges));

		// Double eduCess = this.getEduCess(taxAmount); // taxAmount * 0.04;
		log.info("Education Cess 4% : {} ", Math.round(eduCess));
		totalTaxAmtDetails.setEduCesAmount((int) Math.round(eduCess));

		Double finalTaxAmt = taxAmount + surCharges + eduCess - rebate87A;

		if (fullTaxAmount <= 500000d) {
			rebate87A = tax5Amount;
			finalTaxAmt = 0d;
		}
		totalTaxAmtDetails.setRebate87AAmount((int) Math.round(rebate87A));
		log.info("Tax on total income: {}", Math.round(finalTaxAmt));
		totalTaxAmtDetails.setFinalTaxAmt((int) Math.round(finalTaxAmt));

		return totalTaxAmtDetails;

	}

	private Double getSurcharges(Double taxAmount, Double taxableAmount) {
		log.info("Surchagres amount calculating started");
		Double surchargesAmt = 0d;
		Double oneCr = 10000000d; /* 50L to 1cr ->10% */
		Double twoCr = 20000000d; /* 1cr to 2cr ->15% */
		Double threeCr = 30000000d; /* 2cr to 5cr ->25% */
		Double fifthCr = 50000000d; /* 5cr to unlimited ->37% */

		if (taxableAmount < oneCr && taxableAmount >= 5000000d) {
			surchargesAmt = taxAmount * 0.1;
		}
		if (taxableAmount > oneCr && taxableAmount <= twoCr) {
			surchargesAmt = taxAmount * 0.15;
		}
		if (taxableAmount > twoCr && taxableAmount <= fifthCr) {
			surchargesAmt = taxAmount * 0.25;
		}
		if (taxableAmount > fifthCr) {
			surchargesAmt = taxAmount * 0.37;
		}
		log.info("Surchagres amount calculating is done");
		return surchargesAmt;
	}

	private Double getEduCess(Double taxAmount) {
		log.info("Education cess amount calculating started");
		Double eduCess = 0d;
		eduCess = taxAmount * 0.04;
		log.info("Education cess amount calculating done");
		return eduCess;

	}

}
