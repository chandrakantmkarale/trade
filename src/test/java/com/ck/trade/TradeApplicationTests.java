package com.ck.trade;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ck.trade.exception.TradeNotFoundException;
import com.ck.trade.exception.TradeValidationException;
import com.ck.trade.model.Trade;
import com.ck.trade.service.TradingControler;

@SpringBootTest
class TradeApplicationTests {
	private static final long ONE_DAY_MSEC = 24 * 60 * 60 * 1000L;
	@Autowired
	TradingControler tradeController;

	@Test
	void testSaveTrade_success() throws TradeValidationException, TradeNotFoundException {
		ResponseEntity<String> responseEntity = tradeController.saveTrade(getTradeObjectWithFutureMaturity("T1", 1));
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.CREATED).build(), responseEntity);
		// Created
		Trade trade = tradeController.getTrade("T1");
		// Id Check
		Assertions.assertEquals("T1", trade.getId());
	}

	@Test
	void testSaveTrade_expiredMaturity_fail() throws TradeValidationException {
		Assertions.assertThrows(TradeValidationException.class, () -> {
			tradeController.saveTrade(getTradeObjectWithExpiredMaturity("T2", 1));
		});
	}
	
	
	@Test
	void testSaveTrade_oldversion_failure() throws TradeValidationException {
		ResponseEntity<String> responseEntity = tradeController.saveTrade(getTradeObjectWithFutureMaturity("T5", 5));
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.CREATED).build(), responseEntity);
		//Saving with older version
		Assertions.assertThrows(TradeValidationException.class, () -> {
			tradeController.saveTrade(getTradeObjectWithFutureMaturity("T5", 1));
		});
	
	}
	
	
	

	Trade getActiveTradeObject(String id, int version, String counterPartyId, String bookId, Date maturityDate) {
		Trade trade = new Trade();
		trade.setId(id);
		trade.setBookId(bookId);
		trade.setVersion(version);
		trade.setCounterPartyId(counterPartyId);
		trade.setMaturityDate(maturityDate);
		trade.setCreatedDate(new Date());
		trade.setExpired(false);
		return trade;
	}

	Trade getExpiredTradeObject(String id, int version, String counterPartyId, String bookId, Date maturityDate) {
		Trade trade = getActiveTradeObject(id, version, counterPartyId, bookId, maturityDate);
		trade.setExpired(true);
		return trade;
	}

	private Trade getTradeObject(String tradeId, int version) {
		Trade trade = new Trade();
		trade.setId(tradeId);
		trade.setBookId("bookdId-01");
		trade.setVersion(version);
		trade.setCounterPartyId("CounterpartyId-01");
		trade.setMaturityDate(new Date());
		trade.setExpired(true);
		return trade;
	}

	private Trade getTradeObjectWithFutureMaturity(String tradeId, int version) {
		Trade trade = getTradeObject(tradeId, version);
		Date futureDate = new Date(System.currentTimeMillis() + ONE_DAY_MSEC);
		trade.setMaturityDate(futureDate);
		return trade;

	}

	private Trade getTradeObjectWithExpiredMaturity(String tradeId, int version) {
		Trade trade = getTradeObject(tradeId, version);
		Date backDate = new Date(System.currentTimeMillis() - ONE_DAY_MSEC);
		trade.setMaturityDate(backDate);
		return trade;

	}

}
