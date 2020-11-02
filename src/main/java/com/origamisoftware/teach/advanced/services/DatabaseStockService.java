package com.origamisoftware.teach.advanced.services;

import com.origamisoftware.teach.advanced.model.StockData;
import com.origamisoftware.teach.advanced.model.StockQuote;
import com.origamisoftware.teach.advanced.util.DatabaseConnectionException;
import com.origamisoftware.teach.advanced.util.DatabaseUtils;
import com.origamisoftware.teach.advanced.util.Interval;

import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * An implementation of the StockService interface that gets
 * stock data from a database.
 */
public class DatabaseStockService implements StockService {

    /**
     * Return the current price for a share of stock  for the given symbol
     *
     * @param symbol the stock symbol of the company you want a quote for.
     *               e.g. APPL for APPLE
     * @return a  <CODE>BigDecimal</CODE> instance
     * @throws StockServiceException if using the service generates an exception.
     *                               If this happens, trying the service may work, depending on the actual cause of the
     *                               error.
     */
    @Override
    public StockQuote getQuote(String symbol) throws StockServiceException {
        // todo - this is a pretty lame implementation why?
        List<StockQuote> stockQuotes = null;
        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            String queryString = "select * from quotes where symbol = '" + symbol + "'";

            ResultSet resultSet = statement.executeQuery(queryString);
            stockQuotes = new ArrayList<>(resultSet.getFetchSize());
            while(resultSet.next()) {
                String symbolValue = resultSet.getString("symbol");
                Date time = resultSet.getDate("time");
                BigDecimal price = resultSet.getBigDecimal("price");
                stockQuotes.add(new StockQuote(price, time, symbolValue));
            }

        } catch (DatabaseConnectionException | SQLException exception) {
            throw new StockServiceException(exception.getMessage(), exception);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("There is no stock data for:" + symbol);
        }
        return stockQuotes.get(0);
    }

    /**
     * Get a historical list of stock quotes for the provide symbol
     *
     * @param symbol the stock symbol to search for
     * @param from   the date of the first stock quote
     * @param until  the date of the last stock quote
     * @return a list of StockQuote instances
     * @throws   StockServiceException if using the service generates an exception.
     * If this happens, trying the service may work, depending on the actual cause of the
     * error.
     */
    @Override
    public List<StockQuote> getQuote(String symbol, Calendar from, Calendar until, Interval interval) throws StockServiceException {
        List<StockQuote> stockQuotes = null;
        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StockData.dateFormat);

            String fromString = simpleDateFormat.format(from.getTime());
            String untilString = simpleDateFormat.format(until.getTime());

            String queryString = "select * from quotes where symbol = '" + symbol + "'"
                    + "and time BETWEEN '" + fromString + "' and '" + untilString + "'";

            ResultSet resultSet = statement.executeQuery(queryString);
            stockQuotes = new ArrayList<>();
            StockQuote previousStockQuote = null;

            Calendar calendar = Calendar.getInstance();
            while (resultSet.next()) {
                String symbolValue = resultSet.getString("symbol");
                Timestamp timeStamp = resultSet.getTimestamp("time");
                calendar.setTimeInMillis(timeStamp.getTime());
                BigDecimal price = resultSet.getBigDecimal("price");
                java.util.Date time = calendar.getTime();
                StockQuote currentStockQuote = new StockQuote(price, time, symbolValue);

                if (previousStockQuote == null) { // first time through always add stockquote

                    stockQuotes.add(currentStockQuote);

                } else if (isInterval(currentStockQuote.getDate(),
                        interval,
                        previousStockQuote.getDate())) {

                    stockQuotes.add(currentStockQuote);
                }

                previousStockQuote = currentStockQuote;
            }

        } catch (DatabaseConnectionException | SQLException exception) {
            throw new StockServiceException(exception.getMessage(), exception);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("There is no stock data for:" + symbol);
        }

        return stockQuotes;
    }

    private boolean isInterval(java.util.Date endDate, Interval interval, java.util.Date startDate) {
        Calendar startDatePlusInterval = Calendar.getInstance();
        startDatePlusInterval.setTime(startDate);
        startDatePlusInterval.add(Calendar.MINUTE, interval.getMinutes());
        return endDate.after(startDatePlusInterval.getTime());
    }


}
