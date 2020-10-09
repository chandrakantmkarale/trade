package com.ck.trade.service;

import java.util.Date;

import com.ck.trade.model.Trade;

public class TradeValidator {
	
	public static boolean isMaturityDateValid(Trade trade,Date referenceDate) {
		if (trade.getMaturityDate().before(referenceDate)) {
			return false;
		} else {
			return true;
		}
	}

}
