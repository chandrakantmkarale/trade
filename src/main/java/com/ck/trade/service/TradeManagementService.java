package com.ck.trade.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ck.trade.TradeRepository;
import com.ck.trade.exception.ErrorCode;
import com.ck.trade.exception.TradeNotFoundException;
import com.ck.trade.exception.TradeValidationException;
import com.ck.trade.model.Trade;

@Service
public class TradeManagementService {

	@Autowired
	TradeRepository tradeRepo;

	public void persist(Trade trade) {
		trade.setCreatedDate(new Date());
		tradeRepo.save(trade);
	}

	public List<Trade> findAll() {
		return tradeRepo.findAll();
	}

	public boolean exists(String tradeId) {
		return tradeRepo.existsById(tradeId);
	}

	public Trade getTrade(String tradeId) {
		return tradeRepo.getOne(tradeId);
	}

	public boolean validate(Trade trade) throws TradeNotFoundException, TradeValidationException {
		Optional<Trade> tradeResult = tradeRepo.findById(trade.getId());
		if (tradeResult.isPresent()) {
			return checkVersion(trade, tradeResult.get());
		} else {
			throw new TradeNotFoundException(trade.getId());
		}
	}

	public void saveTrade(Trade receivedTrade) throws TradeValidationException {
		boolean isValidMaturityDate = TradeValidator.isMaturityDateValid(receivedTrade,new Date());
		if (!isValidMaturityDate) {
			throw new TradeValidationException(ErrorCode.ERR_TRADE_WITH_LESS_MATURITYDATE, receivedTrade.getId());
		}
		boolean exists = exists(receivedTrade.getId());
		if (!exists) {
			// Trade Does not exists.Persist
			persist(receivedTrade);
		} else {
			Trade existingTrade = getTrade(receivedTrade.getId());
			// Trade exists. Check version.
			checkVersion(receivedTrade, existingTrade);
			// Version OK. Persist
			persist(receivedTrade);
		}
	}

	public boolean checkVersion(Trade received, Trade saved) throws TradeValidationException {
		/**
		 * 1. During transmission if the lower version is being received by the store it
		 * will reject the trade and throw an exception. If the version is same it will
		 * override the existing record.
		 */
		if (received.getVersion() >= saved.getVersion()) {
			return true;
		}
		throw new TradeValidationException(ErrorCode.ERR_OLD_TRADE_VERSION, saved.getId());
	}
}