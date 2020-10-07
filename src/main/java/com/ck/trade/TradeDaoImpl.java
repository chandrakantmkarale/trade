package com.ck.trade;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Repository;

import com.ck.trade.model.Trade;

@Repository
public class TradeDaoImpl implements ITradeDao {

	private static Map<String, Trade> trades = new HashMap<>();

	@Override
	public Trade find(String tradeId) {
		return trades.get(tradeId);
	}

	@Override
	public void save(Trade trade) {
		trade.setCreatedDate(new Date());
		trades.put(trade.getId(), trade);
	}

	@Override
	public List<Trade> findAll() {
		List<Trade> tradeList = new ArrayList<>();
		tradeList.addAll(trades.values());
		return tradeList; 
	}
}