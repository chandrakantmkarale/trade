package com.ck.trade;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ck.trade.controller.TradeManagementController;
import com.ck.trade.exception.TradeNotFoundException;
import com.ck.trade.exception.TradeValidationException;
import com.ck.trade.model.Trade;
import com.ck.trade.transport.rest.dto.TradeDTO;

@SpringBootTest
class TradeApplicationTests {
	private static final long ONE_DAY_MSEC = 24 * 60 * 60 * 1000L;
	@Autowired
	TradeManagementController tradeController;

	@Test
	void testSaveTrade_success() throws TradeValidationException, TradeNotFoundException {
		ResponseEntity<String> responseEntity = tradeController.saveTrade(getTradeObjectWithFutureMaturity("T1", 1));
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.CREATED).build(), responseEntity);
		// Created
		TradeDTO trade = tradeController.getTrade("T1");
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
		//Allow Saving with older version
		Assertions.assertDoesNotThrow(() -> {
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

	private TradeDTO getTradeObject(String tradeId, int version) {
		TradeDTO trade = new TradeDTO();
		trade.setId(tradeId);
		trade.setBookId("bookdId-01");
		trade.setVersion(version);
		trade.setCounterPartyId("CounterpartyId-01");
		trade.setMaturityDate(new Date());
		trade.setExpired(true);
		return trade;
	}

	private TradeDTO getTradeObjectWithFutureMaturity(String tradeId, int version) {
		TradeDTO trade = getTradeObject(tradeId, version);
		Date futureDate = new Date(System.currentTimeMillis() + ONE_DAY_MSEC);
		trade.setMaturityDate(futureDate);
		return trade;

	}

	private TradeDTO getTradeObjectWithExpiredMaturity(String tradeId, int version) {
		TradeDTO trade = getTradeObject(tradeId, version);
		Date backDate = new Date(System.currentTimeMillis() - ONE_DAY_MSEC);
		trade.setMaturityDate(backDate);
		return trade;

	}

	
	
	/**
	 * 
	 * { "id":"100", "version":"1", "counterPartyId":"cp1", "bookId":"bkid-01",
	 * "maturityDate":"04/10/2020", "isExpired":"false" }
	 */

	/**
	 * [ { "id": "100", "version": 1, "counterPartyId": "cp1", "bookId": "bkid-01",
	 * "maturityDate": "2020-10-05T00:00:00.000+00:00", "createdDate":
	 * "2020-10-04T11:51:37.784+00:00", "expired": false }, { "id": "101",
	 * "version": 1, "counterPartyId": "cp1", "bookId": "bkid-01", "maturityDate":
	 * "2020-10-05T00:00:00.000+00:00", "createdDate":
	 * "2020-10-04T11:52:33.608+00:00", "expired": false } ]
	 */

	/**
	 * 
	 * create table trade (id varchar(255) not null, book_id varchar(255),
	 * counter_party_id varchar(255), created_date timestamp, is_expired boolean not
	 * null, maturity_date timestamp, version integer not null, primary key (id))
	 */

}
