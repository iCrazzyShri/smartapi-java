package com.angelbroking.smartapi.sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import com.angelbroking.smartapi.SmartConnect;
import com.angelbroking.smartapi.http.exceptions.SmartAPIException;
import com.angelbroking.smartapi.models.Gtt;
import com.angelbroking.smartapi.models.GttParams;
import com.angelbroking.smartapi.models.Order;
import com.angelbroking.smartapi.models.OrderParams;
import com.angelbroking.smartapi.models.User;
import com.angelbroking.smartapi.ticker.OnConnect;
import com.angelbroking.smartapi.ticker.OnDisconnect;
import com.angelbroking.smartapi.ticker.OnError;
import com.angelbroking.smartapi.ticker.OnTicks;
import com.angelbroking.smartapi.ticker.SmartAPITicker;
import com.angelbroking.smartapi.utils.Constants;

@SuppressWarnings("unused")
public class Examples {

	public void getProfile(SmartConnect smartConnect) throws IOException, SmartAPIException {
		User profile = smartConnect.getProfile();
	}

	/** Place order. */
	public void placeOrder(SmartConnect smartConnect) throws SmartAPIException, IOException {

		OrderParams orderParams = getROBOMOrder();
		Order order = smartConnect.placeOrder(orderParams, orderParams.variety);
	}

	@NotNull
	private OrderParams getSLMOrder() {
		OrderParams orderParams = new OrderParams();
		orderParams.variety = "STOPLOSS";
		orderParams.quantity = 75;
		orderParams.symboltoken = "62279";
		orderParams.exchange = Constants.EXCHANGE_NFO;
		orderParams.ordertype = "STOPLOSS_MARKET";
		orderParams.tradingsymbol = "NIFTY27MAY2115000CE";
		orderParams.producttype = "INTRADAY";
		orderParams.duration = Constants.VALIDITY_DAY;
		orderParams.transactiontype = Constants.TRANSACTION_TYPE_BUY;
		orderParams.price = 222.0;
		orderParams.squareoff = "100";
		orderParams.stoploss = "100";
		orderParams.trailingStopLoss = "200";
		orderParams.disclosedquantity = "0";
		orderParams.duration = "0";
		orderParams.ordertag = "From Telegram bot";
		orderParams.triggerprice = "122";
		return orderParams;
	}

	@NotNull
	private OrderParams getROBOMOrder() {
		OrderParams orderParams = new OrderParams();
		orderParams.variety = "ROBO";
		orderParams.quantity = 75;
		orderParams.symboltoken = "62279";
		orderParams.exchange = Constants.EXCHANGE_NFO;
		orderParams.ordertype = "STOPLOSS_LIMIT";
		orderParams.tradingsymbol = "NIFTY27MAY2115000CE";
		orderParams.producttype = "INTRADAY";
		orderParams.duration = Constants.VALIDITY_DAY;
		orderParams.transactiontype = Constants.TRANSACTION_TYPE_BUY;
		orderParams.price = 122.2;
		orderParams.squareoff = "100";
		orderParams.stoploss = "100";
		orderParams.trailingStopLoss = "100";
		orderParams.disclosedquantity = "0";
		orderParams.duration = "0";
		orderParams.ordertag = "From Telegram bot";
		orderParams.triggerprice = "122";
		return orderParams;
	}

	@NotNull
	private OrderParams getSLOrder() {
		OrderParams orderParams = new OrderParams();
		orderParams.variety = "STOPLOSS";
		orderParams.quantity = 75;
		orderParams.symboltoken = "62279";
		orderParams.exchange = Constants.EXCHANGE_NFO;
		orderParams.ordertype = "STOPLOSS_LIMIT";
		orderParams.tradingsymbol = "NIFTY27MAY2115000CE";
		orderParams.producttype = "INTRADAY";
		orderParams.duration = Constants.VALIDITY_DAY;
		orderParams.transactiontype = Constants.TRANSACTION_TYPE_BUY;
		orderParams.price = 122.2;
		orderParams.squareoff = "100";
		orderParams.stoploss = "100";
		orderParams.trailingStopLoss = "100";
		orderParams.disclosedquantity = "0";
		orderParams.duration = "0";
		orderParams.ordertag = "From Telegram bot";
		orderParams.triggerprice = "122";
		return orderParams;
	}

	@NotNull
	private OrderParams getLimitOrder() {
		OrderParams orderParams = new OrderParams();
		orderParams.variety = "NORMAL";
		orderParams.quantity = 75;
		orderParams.symboltoken = "62279";
		orderParams.exchange = Constants.EXCHANGE_NFO;
		orderParams.ordertype = Constants.ORDER_TYPE_LIMIT;
		orderParams.tradingsymbol = "NIFTY27MAY2115000CE";
		orderParams.producttype = Constants.PRODUCT_INTRADAY;
		orderParams.duration = Constants.VALIDITY_DAY;
		orderParams.transactiontype = Constants.TRANSACTION_TYPE_BUY;
		orderParams.price = 122.2;
		orderParams.squareoff = "100";
		orderParams.stoploss = "100";
		orderParams.trailingStopLoss = "100";
		orderParams.disclosedquantity = "0";
		orderParams.duration = "0";
		orderParams.ordertag = "From Telegram bot";
		orderParams.triggerprice = "122";
		return orderParams;
	}

	/** Modify order. */
	public void modifyOrder(SmartConnect smartConnect) throws SmartAPIException, IOException {
		// Order modify request will return order model which will contain only

		OrderParams orderParams = new OrderParams();
		orderParams.quantity = 1;
		orderParams.ordertype = Constants.ORDER_TYPE_LIMIT;
		orderParams.tradingsymbol = "ASHOKLEY";
		orderParams.symboltoken = "3045";
		orderParams.producttype = Constants.PRODUCT_DELIVERY;
		orderParams.exchange = Constants.EXCHANGE_NSE;
		orderParams.duration = Constants.VALIDITY_DAY;
		orderParams.price = 122.2;

		String orderId = "201216000755110";
		Order order = smartConnect.modifyOrder(orderId, orderParams, Constants.VARIETY_REGULAR);
	}

	/** Cancel an order */
	public void cancelOrder(SmartConnect smartConnect) throws SmartAPIException, IOException {
		// Order modify request will return order model which will contain only
		// order_id.
		// Cancel order will return order model which will only have orderId.
		Order order = smartConnect.cancelOrder("201009000000015", Constants.VARIETY_REGULAR);
	}

	/** Get order details */
	public void getOrder(SmartConnect smartConnect) throws SmartAPIException, IOException {
		JSONObject orders = smartConnect.getOrderHistory(smartConnect.getUserId());
		System.out.print(orders);
//		for (int i = 0; i < orders.size(); i++) {
//			System.out.println(orders.get(i).orderId + " " + orders.get(i).status);
//		}
	}

	/**
	 * Get last price for multiple instruments at once. USers can either pass
	 * exchange with tradingsymbol or instrument token only. For example {NSE:NIFTY
	 * 50, BSE:SENSEX} or {256265, 265}
	 */
	public void getLTP(SmartConnect smartConnect) throws SmartAPIException, IOException {
		String exchange = "NSE";
		String tradingSymbol = "SBIN-EQ";
		String symboltoken = "3045";
		JSONObject ltpData = smartConnect.getLTP(exchange, tradingSymbol, symboltoken);
	}

	/** Get tradebook */
	public void getTrades(SmartConnect smartConnect) throws SmartAPIException, IOException {
		// Returns tradebook.
		JSONObject trades = smartConnect.getTrades();
		
	}

	/** Get RMS */
	public void getRMS(SmartConnect smartConnect) throws SmartAPIException, IOException {
		// Returns RMS.
		JSONObject response = smartConnect.getRMS();
	}

	/** Get Holdings */
	public void getHolding(SmartConnect smartConnect) throws SmartAPIException, IOException {
		// Returns Holding.
		JSONObject response = smartConnect.getHolding();
	}

	/** Get Position */
	public void getPosition(SmartConnect smartConnect) throws SmartAPIException, IOException {
		// Returns Position.
		JSONArray response = smartConnect.getPosition();
	}

	/** convert Position */
	public void convertPosition(SmartConnect smartConnect) throws SmartAPIException, IOException {

		JSONObject requestObejct = new JSONObject();
		requestObejct.put("exchange", "NSE");
		requestObejct.put("oldproducttype", "DELIVERY");
		requestObejct.put("newproducttype", "MARGIN");
		requestObejct.put("tradingsymbol", "SBIN-EQ");
		requestObejct.put("transactiontype", "BUY");
		requestObejct.put("quantity", 1);
		requestObejct.put("type", "DAY");

		JSONArray response = smartConnect.getPosition();
	}
	/** Create Gtt Rule*/
	public void createRule(SmartConnect smartConnect)throws SmartAPIException,IOException{
		GttParams gttParams= new GttParams();
		
		gttParams.tradingsymbol="SBIN-EQ";
		gttParams.symboltoken="3045";
		gttParams.exchange="NSE";
		gttParams.producttype="MARGIN";
		gttParams.transactiontype="BUY";
		gttParams.price= 100000.01;
		gttParams.qty=10;
		gttParams.disclosedqty=10;
		gttParams.triggerprice=20000.1;
		gttParams.timeperiod=300;
		
		Gtt gtt = smartConnect.gttCreateRule(gttParams);
	}
	
	/** Modify Gtt Rule */
	public void modifyRule(SmartConnect smartConnect)throws SmartAPIException,IOException{
		GttParams gttParams= new GttParams();
		
		gttParams.tradingsymbol="SBIN-EQ";
		gttParams.symboltoken="3045";
		gttParams.exchange="NSE";
		gttParams.producttype="MARGIN";
		gttParams.transactiontype="BUY";
		gttParams.price= 100000.1;
		gttParams.qty=10;
		gttParams.disclosedqty=10;
		gttParams.triggerprice=20000.1;
		gttParams.timeperiod=300;
		
		Integer id= 1000051;
		
		Gtt gtt = smartConnect.gttModifyRule(id,gttParams);
	}
	
	/** Cancel Gtt Rule */
	public void cancelRule(SmartConnect smartConnect)throws SmartAPIException, IOException{
		Integer id=1000051;
		String symboltoken="3045";
		String exchange="NSE";
		
		Gtt gtt = smartConnect.gttCancelRule(id,symboltoken,exchange);
	}
	
	/** Gtt Rule Details */
	public void ruleDetails(SmartConnect smartConnect)throws SmartAPIException, IOException{
		Integer id=1000051;
	
		JSONObject gtt = smartConnect.gttRuleDetails(id);
	}
	
	/** Gtt Rule Lists */
	@SuppressWarnings("serial")
	public void ruleList(SmartConnect smartConnect)throws SmartAPIException, IOException{
		
		List<String> status=new ArrayList<String>(){{
			add("NEW");
			add("CANCELLED");
			add("ACTIVE");
			add("SENTTOEXCHANGE");
			add("FORALL");
			}};
		Integer page=1;
		Integer count=10;
	
		JSONArray gtt = smartConnect.gttRuleList(status,page,count);
	}
	
	/** Historic Data */
	public void getCandleData(SmartConnect smartConnect) throws SmartAPIException, IOException {

		JSONObject requestObejct = new JSONObject();
		requestObejct.put("exchange", "NSE");
		requestObejct.put("symboltoken", "3045");
		requestObejct.put("interval", "ONE_MINUTE");
		requestObejct.put("fromdate", "2021-03-08 09:00");
		requestObejct.put("todate", "2021-03-09 09:20");

		String response = smartConnect.candleData(requestObejct);
	}

	public void tickerUsage(String clientId, String feedToken, String strWatchListScript, String task)
			throws SmartAPIException {

		SmartAPITicker tickerProvider = new SmartAPITicker(clientId, feedToken);

		tickerProvider.setOnConnectedListener(new OnConnect() {
			@Override
			public void onConnected() {
				tickerProvider.subscribe(strWatchListScript, task);
				//tickerProvider.runScript(strWatchListScript, task);
			}
		});

		tickerProvider.setOnDisconnectedListener(new OnDisconnect() {
			@Override
			public void onDisconnected() {
				System.out.println("onDisconnected");
			}
		});

		/** Set error listener to listen to errors. */
		tickerProvider.setOnErrorListener(new OnError() {
			@Override
			public void onError(Exception exception) {
				System.out.println("onError: " + exception.getMessage());
			}

			@Override
			public void onError(SmartAPIException smartAPIException) {
				System.out.println("onError: " + smartAPIException.getMessage());
			}

			@Override
			public void onError(String error) {
				System.out.println("onError: " + error);
			}
		});

		tickerProvider.setOnTickerArrivalListener(new OnTicks() {
			@Override
			public void onTicks(JSONArray ticks) {
				System.out.println("ticker data: " + ticks.toString());
			}
		});

		/**
		 * connects to Smart API ticker server for getting live quotes
		 */
		tickerProvider.connect();

		/**
		 * You can check, if websocket connection is open or not using the following
		 * method.
		 */
		boolean isConnected = tickerProvider.isConnectionOpen();
		System.out.println(isConnected);

		// After using SmartAPI ticker, close websocket connection.
		// tickerProvider.disconnect();

	}
	

	/** Logout user. */
	public void logout(SmartConnect smartConnect) throws SmartAPIException, IOException {
		/** Logout user and kill session. */
		JSONObject jsonObject = smartConnect.logout();
	}

}
