package com.ck.trade.exception; 

/**
 * 
 * @author ckarale
 *
 */
public class TradeNotFoundException extends Exception {
	private String tradeId;
	public TradeNotFoundException(String tradeId) {
		super();
		this.tradeId = tradeId;
	}
	
	@Override
	public String getMessage() {
		return "TradeId "+ this.tradeId + " not found";
	}
	
}
