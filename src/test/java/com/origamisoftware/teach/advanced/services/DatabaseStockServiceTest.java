package com.origamisoftware.teach.advanced.services;

import com.origamisoftware.teach.advanced.model.StockData;
import com.origamisoftware.teach.advanced.model.StockQuote;
import com.origamisoftware.teach.advanced.util.DatabaseInitializationException;
import com.origamisoftware.teach.advanced.util.DatabaseUtils;
import com.origamisoftware.teach.advanced.util.Interval;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Unit tests for the DatabaseStockService
 */
public class DatabaseStockServiceTest {

//    private DatabaseStockService databaseStockService;
//
//    /**
//     * Creates the quotes table
//     * @throws DatabaseInitializationException
//     */
//    @Before
//    public void initialize() throws DatabaseInitializationException {
//        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
//        databaseStockService = new DatabaseStockService();
//
//    }
//
//    /**
//     * Calls getQuote to see if Checks to see if APPL exists in the db
//     * @throws Exception
//     */
//    @Test
//    public void testGetQuote() throws Exception {
//        DatabaseStockService databaseStockService = new DatabaseStockService();
//        String symbol = "APPL";
//        StockQuote stockQuote = databaseStockService.getQuote(symbol);
//        assertNotNull("Verify we can get a stock quote from the db", stockQuote);
//        assertEquals("Make sure the symbols match", symbol, stockQuote.getSymbol());
//    }
//
//    /**
//     * Calls the overloaded getQuote method to see if the expected symbol is returned between date parameters in the db
//     * @throws Exception
//     */
//    @Test
//    public void testGetQuoteWithIntervalBasic() throws Exception {
//        String symbol = "GOOG";
//        String fromStringDate = "2000-02-10 00:00:01";
//        String untilStringDate = "2015-02-03 00:00:01";
//
//        Calendar fromCalendar = makeCalendarFromString(fromStringDate);
//        Calendar untilCalendar = makeCalendarFromString(untilStringDate);
//
//        List<StockQuote> stockQuotes = databaseStockService.getQuote(symbol, fromCalendar, untilCalendar, Interval.DAY);
//        assertFalse("verify stock quotes where returned", stockQuotes.isEmpty());
//    }
//
//    /**
//     * Converts a String to a Calendar
//     * @param dateString
//     * @return
//     * @throws ParseException
//     */
//    private Calendar makeCalendarFromString(String dateString) throws ParseException {
//        DateFormat format = new SimpleDateFormat(StockData.dateFormat, Locale.ENGLISH);
//        Date date = format.parse(dateString);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        return calendar;
//
//    }

}
