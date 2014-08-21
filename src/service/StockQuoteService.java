package service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import bean.KeyStatsBean;
import bean.StockQuote;

public class StockQuoteService {

	public StockQuote getAllStockData(String symbol) throws JSONException, IOException{

		String fullUrlStr1="http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quote%20where%20symbol%3D'"
				+symbol+"'&"+"format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";

		URL fullUrl = new URL(fullUrlStr1);
		InputStream is = fullUrl.openStream();

		JSONTokener tok = new JSONTokener(is);

		JSONObject result = new JSONObject(tok);


		JSONObject q = result.getJSONObject("query");

		JSONObject result1 = q.getJSONObject("results");

		JSONObject quote =result1.getJSONObject("quote");

		StockQuote stockQuote = new StockQuote();

		stockQuote.setChange(quote.getDouble("Change"));
		stockQuote.setName(quote.getString("Name"));
		stockQuote.setDaysLow(quote.getDouble("DaysLow"));
		stockQuote.setDaysHigh(quote.getDouble("DaysHigh"));
		stockQuote.setStockExchange(quote.getString("StockExchange"));
		stockQuote.setSymbol(quote.getString("Symbol"));
		stockQuote.setYearHigh(quote.getDouble("YearHigh"));
		stockQuote.setYearLow(quote.getDouble("YearLow"));
		stockQuote.setLastTradePrice(quote.getDouble("LastTradePriceOnly"));
		is.close();	

		return stockQuote;
	}

	public float getLastTradedPrice(String symbol) throws IOException, JSONException, ParseException{
		String fullUrlStr1="http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quote%20where%20symbol%3D'"
				+symbol+"'&"+"format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";

		URL fullUrl = new URL(fullUrlStr1);

		InputStream is = fullUrl.openStream();

		JSONTokener tok = new JSONTokener(is);

		JSONObject result = new JSONObject(tok);

		JSONObject q = result.getJSONObject("query");

		JSONObject result1 = q.getJSONObject("results");

		JSONObject quote =result1.getJSONObject("quote");

		double currentPrice = quote.getDouble("LastTradePriceOnly");

		System.out.println("Last Traded Price is ::"+currentPrice);

		//to convert double to float
		float price;
		Double x= new Double(currentPrice);
		price = x.floatValue();

		is.close();

		return price;
	}

	public List<KeyStatsBean> getKeyStatsForStock(String symbol){

		List<KeyStatsBean> ksBeanList = new ArrayList<KeyStatsBean>();

		String fullUrlStr1 ="http://query.yahooapis.com/v1/public/yql?q=SELECT%20*%20FROM%20yahoo.finance.keystats%20WHERE%20symbol%3D'"+symbol+"'&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";

		try{

			URL fullUrl = new URL(fullUrlStr1);

			InputStream is = fullUrl.openStream();

			JSONTokener tok = new JSONTokener(is);

			System.out.println("JSON Token :::"+tok);

			JSONObject result = new JSONObject(tok);

			System.out.println("JSON Object is :::"+ result);

			JSONObject q = result.getJSONObject("query");

			JSONObject result1 = q.getJSONObject("results");

			JSONObject stats =result1.getJSONObject("stats");

			System.out.println("JSON Object For Stats is:::" +stats);

			//To get market Cap
			KeyStatsBean ksBean = new KeyStatsBean();
			JSONObject marketCap = stats.getJSONObject("MarketCap");			
			ksBean.setStatistics("Market Cap");
			ksBean.setTerm(marketCap.getString("term"));			
			ksBean.setContent(marketCap.getString("content"));
			ksBeanList.add(ksBean);

			//To get Enterprise Value
			KeyStatsBean ksBean1 = new KeyStatsBean();
			JSONObject enterpriseValue = stats.getJSONObject("EnterpriseValue");
			ksBean1.setStatistics("EnterPrice Value");
			ksBean1.setTerm(enterpriseValue.getString("term"));
			ksBean1.setContent(enterpriseValue.getString("content"));
			ksBeanList.add(ksBean1);

			//To get Trailing PE
			KeyStatsBean ksBean2 = new KeyStatsBean();
			JSONObject trailingPE = stats.getJSONObject("TrailingPE");
			ksBean2.setStatistics("Trailing PE");
			ksBean2.setTerm(trailingPE.getString("term"));
			ksBean2.setContent(trailingPE.getString("content"));
			ksBeanList.add(ksBean2);

			//To get ForwardPE
			KeyStatsBean ksBean3 = new KeyStatsBean();
			JSONObject forwardPE = stats.getJSONObject("ForwardPE");
			ksBean3.setStatistics("Forward PE");
			ksBean3.setTerm(forwardPE.getString("term"));
			ksBean3.setContent(forwardPE.getString("content"));
			ksBeanList.add(ksBean3);

			//To get PEGRatio
			KeyStatsBean ksBean4 = new KeyStatsBean();
			JSONObject pEGRatio = stats.getJSONObject("PEGRatio");
			ksBean4.setStatistics("PEG Ratio");
			ksBean4.setTerm(pEGRatio.getString("term"));
			ksBean4.setContent(pEGRatio.getString("content"));
			ksBeanList.add(ksBean4);

			//To get PriceSales
			KeyStatsBean ksBean5 = new KeyStatsBean();
			JSONObject priceSales = stats.getJSONObject("PriceSales");
			ksBean5.setStatistics("Price Sales");
			ksBean5.setTerm(priceSales.getString("term"));
			ksBean5.setContent(priceSales.getString("content"));
			ksBeanList.add(ksBean5);

			//To get PriceBook
			KeyStatsBean ksBean6 = new KeyStatsBean();
			JSONObject priceBook = stats.getJSONObject("PriceBook");
			ksBean6.setStatistics("Price Book");
			ksBean6.setTerm(priceBook.getString("term"));
			ksBean6.setContent(priceBook.getString("content"));
			ksBeanList.add(ksBean6);

			//To get EnterpriseValueRevenue
			KeyStatsBean ksBean7 = new KeyStatsBean();
			JSONObject enterpriseValueRevenue = stats.getJSONObject("EnterpriseValueRevenue");
			ksBean7.setStatistics("Enterprise value revenue");
			ksBean7.setTerm(enterpriseValueRevenue.getString("term"));
			ksBean7.setContent(enterpriseValueRevenue.getString("content"));
			ksBeanList.add(ksBean7);

			//To get MostRecentQuarter
			KeyStatsBean ksBean8 = new KeyStatsBean();
			JSONObject mostRecentQuarter = stats.getJSONObject("MostRecentQuarter");
			ksBean8.setStatistics("Most Recent Quarter");
			ksBean8.setTerm(mostRecentQuarter.getString("term"));
			ksBean8.setContent(mostRecentQuarter.getString("content"));
			ksBeanList.add(ksBean8);

			//To get ProfitMargin
			KeyStatsBean ksBean9 = new KeyStatsBean();
			JSONObject profitMargin = stats.getJSONObject("ProfitMargin");
			ksBean9.setStatistics("Profit Margin");
			ksBean9.setTerm(profitMargin.getString("term"));
			ksBean9.setContent(profitMargin.getString("content"));
			ksBeanList.add(ksBean9);

			//To get OperatingMargin
			KeyStatsBean ksBean10 = new KeyStatsBean();
			JSONObject operatingMargin = stats.getJSONObject("OperatingMargin");
			ksBean10.setStatistics("Operating Margin");
			ksBean10.setTerm(operatingMargin.getString("term"));
			ksBean10.setContent(operatingMargin.getString("content"));
			ksBeanList.add(ksBean10);

			//To get Revenue
			KeyStatsBean ksBean11 = new KeyStatsBean();
			JSONObject revenue = stats.getJSONObject("Revenue");
			ksBean11.setStatistics("Revenue");
			ksBean11.setTerm(revenue.getString("term"));
			ksBean11.setContent(revenue.getString("content"));
			ksBeanList.add(ksBean11);

			//to get Revenue per share
			KeyStatsBean ksBean12 = new KeyStatsBean();
			JSONObject revenuePerShare = stats.getJSONObject("RevenuePerShare");
			ksBean12.setStatistics("Revenue Per Share");
			ksBean12.setTerm(revenuePerShare.getString("term"));
			ksBean12.setContent(revenuePerShare.getString("content"));
			ksBeanList.add(ksBean12);

			//QtrlyRevenueGrowth
			KeyStatsBean ksBean13 = new KeyStatsBean();
			JSONObject QtrlyRevenueGrowth = stats.getJSONObject("QtrlyRevenueGrowth");
			ksBean13.setStatistics("Quarterly Revenue Growth");
			ksBean13.setTerm(QtrlyRevenueGrowth.getString("term"));
			ksBean13.setContent(QtrlyRevenueGrowth.getString("content"));
			ksBeanList.add(ksBean13);

			//GrossProfit
			KeyStatsBean ksBean14 = new KeyStatsBean();
			JSONObject GrossProfit = stats.getJSONObject("GrossProfit");
			ksBean14.setStatistics("Gross Profit");
			ksBean14.setTerm(GrossProfit.getString("term"));
			ksBean14.setContent(GrossProfit.getString("content"));
			ksBeanList.add(ksBean14);

			//NetIncomeAvltoCommon
			KeyStatsBean ksBean15 = new KeyStatsBean();
			JSONObject NetIncomeAvltoCommon = stats.getJSONObject("NetIncomeAvltoCommon");
			ksBean15.setStatistics("NetIncomeAvltoCommon");
			ksBean15.setTerm(NetIncomeAvltoCommon.getString("term"));
			ksBean15.setContent(NetIncomeAvltoCommon.getString("content"));
			ksBeanList.add(ksBean15);

			//DilutedEPS
			KeyStatsBean ksBean16 = new KeyStatsBean();
			JSONObject DilutedEPS = stats.getJSONObject("DilutedEPS");
			ksBean16.setStatistics("DilutedEPS");
			ksBean16.setTerm(DilutedEPS.getString("term"));
			ksBean16.setContent(DilutedEPS.getString("content"));
			ksBeanList.add(ksBean16);

			//QtrlyEarningsGrowth
			KeyStatsBean ksBean17 = new KeyStatsBean();
			JSONObject QtrlyEarningsGrowth = stats.getJSONObject("QtrlyEarningsGrowth");
			ksBean17.setStatistics("QtrlyEarningsGrowth");
			ksBean17.setTerm(QtrlyEarningsGrowth.getString("term"));
			ksBean17.setContent(QtrlyEarningsGrowth.getString("content"));
			ksBeanList.add(ksBean17);

			//TotalCash
			KeyStatsBean ksBean18 = new KeyStatsBean();
			JSONObject TotalCash = stats.getJSONObject("TotalCash");
			ksBean18.setStatistics("TotalCash");
			ksBean18.setTerm(TotalCash.getString("term"));
			ksBean18.setContent(TotalCash.getString("content"));
			ksBeanList.add(ksBean18);

			//TotalCashPerShare
			KeyStatsBean ksBean19 = new KeyStatsBean();
			JSONObject TotalCashPerShare = stats.getJSONObject("TotalCashPerShare");
			ksBean19.setStatistics("TotalCashPerShare");
			ksBean19.setTerm(TotalCashPerShare.getString("term"));
			ksBean19.setContent(TotalCashPerShare.getString("content"));
			ksBeanList.add(ksBean19);

			//TotalDebt
			KeyStatsBean ksBean20 = new KeyStatsBean();
			JSONObject TotalDebt = stats.getJSONObject("TotalDebt");
			ksBean20.setStatistics("TotalDebt");
			ksBean20.setTerm(TotalDebt.getString("term"));
			ksBean20.setContent(TotalDebt.getString("content"));
			ksBeanList.add(ksBean20);

			//TotalDebtEquity
			KeyStatsBean ksBean21 = new KeyStatsBean();
			JSONObject TotalDebtEquity = stats.getJSONObject("TotalDebtEquity");
			ksBean21.setStatistics("TotalDebtEquity");
			ksBean21.setTerm(TotalDebtEquity.getString("term"));
			ksBean21.setContent(TotalDebtEquity.getString("content"));
			ksBeanList.add(ksBean21);

			//CurrentRatio
			KeyStatsBean ksBean22 = new KeyStatsBean();
			JSONObject CurrentRatio = stats.getJSONObject("CurrentRatio");
			ksBean22.setStatistics("CurrentRatio");
			ksBean22.setTerm(CurrentRatio.getString("term"));
			ksBean22.setContent(CurrentRatio.getString("content"));
			ksBeanList.add(ksBean22);

			//BookValuePerShare
			KeyStatsBean ksBean23 = new KeyStatsBean();
			JSONObject BookValuePerShare = stats.getJSONObject("BookValuePerShare");
			ksBean23.setStatistics("BookValuePerShare");
			ksBean23.setTerm(BookValuePerShare.getString("term"));
			ksBean23.setContent(BookValuePerShare.getString("content"));
			ksBeanList.add(ksBean23);

			//OperatingCashFlow
			KeyStatsBean ksBean24 = new KeyStatsBean();
			JSONObject OperatingCashFlow = stats.getJSONObject("OperatingCashFlow");
			ksBean24.setStatistics("OperatingCashFlow");
			ksBean24.setTerm(OperatingCashFlow.getString("term"));
			ksBean24.setContent(OperatingCashFlow.getString("content"));
			ksBeanList.add(ksBean24);

			//LeveredFreeCashFlow
			KeyStatsBean ksBean25 = new KeyStatsBean();
			JSONObject LeveredFreeCashFlow = stats.getJSONObject("LeveredFreeCashFlow");
			ksBean25.setStatistics("LeveredFreeCashFlow");
			ksBean25.setTerm(LeveredFreeCashFlow.getString("term"));
			ksBean25.setContent(LeveredFreeCashFlow.getString("content"));
			ksBeanList.add(ksBean25);

			//p_52_WeekHigh
			KeyStatsBean ksBean26 = new KeyStatsBean();
			JSONObject p_52WeekHigh = stats.getJSONObject("p_52_WeekHigh");
			ksBean26.setStatistics("52WeekHigh");
			ksBean26.setTerm(p_52WeekHigh.getString("term"));
			ksBean26.setContent(p_52WeekHigh.getString("content"));
			ksBeanList.add(ksBean26);

			//p_52_WeekLow
			KeyStatsBean ksBean27 = new KeyStatsBean();
			JSONObject p_52_WeekLow = stats.getJSONObject("p_52_WeekLow");
			ksBean27.setStatistics("52_WeekLow");
			ksBean27.setTerm(p_52_WeekLow.getString("term"));
			ksBean27.setContent(p_52_WeekLow.getString("content"));
			ksBeanList.add(ksBean27);

			//ShortRatio
			KeyStatsBean ksBean28 = new KeyStatsBean();
			JSONObject ShortRatio = stats.getJSONObject("ShortRatio");
			ksBean28.setStatistics("ShortRatio");
			ksBean28.setTerm(ShortRatio.getString("term"));
			ksBean28.setContent(ShortRatio.getString("content"));
			ksBeanList.add(ksBean28);

			//ShortPercentageofFloat
			KeyStatsBean ksBean29 = new KeyStatsBean();
			JSONObject ShortPercentageofFloat = stats.getJSONObject("ShortPercentageofFloat");
			ksBean29.setStatistics("ShortPercentageofFloat");
			ksBean29.setTerm(ShortPercentageofFloat.getString("term"));
			ksBean29.setContent(ShortPercentageofFloat.getString("content"));
			ksBeanList.add(ksBean29);

			//TrailingAnnualDividendYield
			KeyStatsBean ksBean30 = new KeyStatsBean();
			JSONObject TrailingAnnualDividendYield = stats.getJSONObject("TrailingAnnualDividendYield");
			ksBean30.setStatistics("TrailingAnnualDividendYield");
			ksBean30.setTerm(TrailingAnnualDividendYield.getString("term"));
			ksBean30.setContent(TrailingAnnualDividendYield.getString("content"));
			ksBeanList.add(ksBean30);

			//LastSplitFactor
			KeyStatsBean ksBean31 = new KeyStatsBean();
			JSONObject LastSplitFactor = stats.getJSONObject("LastSplitFactor");
			ksBean31.setStatistics("LastSplitFactor");
			ksBean31.setTerm(LastSplitFactor.getString("term"));
			ksBean31.setContent(LastSplitFactor.getString("content"));
			ksBeanList.add(ksBean31);

			is.close();

		}catch(JSONException je){
			je.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}

		return ksBeanList;				
	}

	public List<KeyStatsBean> getHistoricalDataForStocks(String symbol){

		List<KeyStatsBean> ksBeanList = new ArrayList<KeyStatsBean>();

		try{
			//Get Current Date
			Calendar currentDate = Calendar.getInstance(); 
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd"); 
			String dateNow = formatter.format(currentDate.getTime());

			//Get previous 3 month's date approx
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date myDate1 = dateFormat.parse(dateNow);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(myDate1);
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			Date previousDate = calendar.getTime();
			String eDate = dateFormat.format(previousDate);

			//Get previous day date
			Date myDate = dateFormat.parse(dateNow);
			calendar.setTime(myDate);
			calendar.add(Calendar.DAY_OF_YEAR, -93);
			Date previousDate1 = calendar.getTime();
			String sDate = dateFormat.format(previousDate1);

			// Query to fetch historical data
			String fullUrlStr1 = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20%3D'"+symbol+
					"'and%20startDate%20%3D'"+sDate+"'and%20endDate%20%3D'"+eDate+
					"'&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";

			URL fullUrl = new URL(fullUrlStr1);

			InputStream is = fullUrl.openStream();

			JSONTokener tok = new JSONTokener(is);

			JSONObject result = new JSONObject(tok);

			JSONObject q = result.getJSONObject("query");

			JSONObject results = q.getJSONObject("results");

			JSONObject quote =results.getJSONObject("quote");
			
			for(int i=0; i < quote.length(); i++){				
				//KeyStatsBean keyStatsBean = new KeyStatsBean();
				;
			}
		}catch(JSONException je){
			je.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return ksBeanList;
	}
}
