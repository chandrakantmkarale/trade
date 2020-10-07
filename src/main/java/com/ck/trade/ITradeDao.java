package  com.ck.trade;

import java.util.List;

import com.ck.trade.model.Trade;

public interface ITradeDao {

	/**
	 * Save the Trade details in the Map OR Database
	 * 
	 * @param trade
	 */
	public void save(Trade trade);

	/**
	 * Return the list of all Trades
	 * 
	 * @return
	 */
	List<Trade> findAll();

	/**
	 * Return details of trade with tradeId
	 * 
	 * @param tradeId
	 * @return
	 */
	Trade find(String tradeId);
}