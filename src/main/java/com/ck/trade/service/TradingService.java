package com.ck.trade.service; 

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ck.trade.TradeDaoImpl;
import com.ck.trade.exception.ErrorCode;
import com.ck.trade.exception.TradeNotFoundException;
import com.ck.trade.exception.TradeValidationException;
import com.ck.trade.model.Trade;

@Service
public class TradingService {
	

	@Autowired
	TradeDaoImpl tradeRepo;

	public void persist(Trade trade) {
		trade.setCreatedDate(new Date());
		tradeRepo.save(trade);
	}

	public List<Trade> findAll() {
		return tradeRepo.findAll();
	}



	public Trade getTrade(String tradeId) {
		return tradeRepo.find(tradeId);
	}

	public boolean validate(Trade trade) throws TradeNotFoundException, TradeValidationException {
		isMaturityDateValid(trade);
		Trade tradeResult = tradeRepo.find(trade.getId());
		if (tradeResult != null) {
				return checkVersion(trade, tradeResult);
		} else {
				throw new TradeNotFoundException(trade.getId());
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

	// 2. Store should not allow the trade which has less maturity date then today
	// date
	public boolean isMaturityDateValid(Trade trade) throws TradeValidationException {
		if (trade.getMaturityDate().before(new Date())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 3. Update trades which have expired.
	 *  @throws TradeValidationException 
	 */
	public void updateTrade()  {
		//TODO: scheduled invocation?
		List<Trade> tradeList = tradeRepo.findAll();
		for (Trade trade : tradeList) {
				try {
					isMaturityDateValid(trade);
				} catch (TradeValidationException e) {
					//Expire the trade
					trade.setExpired(true);
					tradeRepo.save(trade);
				}
				
		}
	}
}