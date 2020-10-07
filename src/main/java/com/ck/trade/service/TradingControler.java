package com.ck.trade.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ck.trade.exception.ErrorCode;
import com.ck.trade.exception.TradeNotFoundException;
import com.ck.trade.exception.TradeValidationException;
import com.ck.trade.model.Trade;

@RestController
public class TradingControler {
	@Autowired
	TradingService tService;

	/*
	 */
	@PostMapping("/trade")
	public ResponseEntity<String> saveTrade(@RequestBody Trade receivedTrade) throws TradeValidationException {
		boolean isValidMaturityDate = tService.isMaturityDateValid(receivedTrade);
		if (!isValidMaturityDate) {
			throw new TradeValidationException(ErrorCode.ERR_TRADE_WITH_LESS_MATURITYDATE, receivedTrade.getId());
		}
		Trade existingTrade = tService.getTrade(receivedTrade.getId());
		if (existingTrade == null) {
			// Trade Does not exists.Persist
			tService.persist(receivedTrade);
		} else {
			// Trade exists. Check version.
			tService.checkVersion(receivedTrade, existingTrade);
			// Version OK. Persist
			tService.persist(receivedTrade);
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/trade")
	public List<Trade> findAllTrades() {
		// return list of all trades
		return tService.findAll();
	}

	@GetMapping("/trade/{id}")
	public Trade getTrade(@PathVariable String id) throws TradeNotFoundException {
		Trade trade = tService.getTrade(id);
		if (trade != null) {
			return trade;
		} else {
			throw new TradeNotFoundException(id);
		}

	}

	@PostMapping("/trade/update")
	public ResponseEntity<String> updateExpiredTrades() {
		try {
			tService.updateTrade();
			;
		} catch (Exception e) {
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}