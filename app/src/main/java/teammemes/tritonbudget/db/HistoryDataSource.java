package teammemes.tritonbudget.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import teammemes.tritonbudget.History;


/**
 * Created by andrewli on 2/26/17.
 */

public class HistoryDataSource extends BaseDataSource{

    public HistoryDataSource(Context context) {
        super(context);
    }

    public TranHistory createTransaction(TranHistory trans) {
        ContentValues values = new ContentValues();
        values.put(HistoryDB.NAME, trans.getName());
        values.put(HistoryDB.QUANTITY, trans.getQuantity());
        SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        values.put(HistoryDB.TDATE, dformat.format(trans.getTdate()));
        values.put(MenuDB.COSTCOL, trans.getCost());
        long insertId = database.insert(HistoryDB.Table_Transaction, null,
                values);
        Cursor cursor = database.query(HistoryDB.Table_Transaction,
                HistoryDB.allColumns, MenuDB.IDCOL + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        TranHistory newTran = cursorToTransaction(cursor);
        cursor.close();
        return newTran;
    }

    public TranHistory getTransaction(int id)  {
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(HistoryDB.Table_Transaction, HistoryDB.allColumns, HistoryDB.ID + " = " +
                    id, null, null, null, null);
            cursor.moveToFirst();
            TranHistory tran = null;
            if (!cursor.isAfterLast()) {
                SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                tran = new TranHistory(id, cursor.getString(cursor.getColumnIndex(HistoryDB.NAME)),
                        cursor.getInt(cursor.getColumnIndex(HistoryDB.QUANTITY)),
                        dformat.parse(cursor.getString(cursor.getColumnIndex(HistoryDB.TDATE))),
                        cursor.getDouble(cursor.getColumnIndex(HistoryDB.COST)));
            }
            // make sure to close the cursor
            cursor.close();
            return tran;

        }
        catch (Exception e) {
            Log.e("get transaction", "failed", e);
        }
        return null;

    }

    /**
     * Update if exists otherwise insert
     * @param trans
     * @return
     */
    public boolean updateTranHistory(TranHistory trans){
        TranHistory tHistory = getTransaction(trans.getId());

        ContentValues contentValues = new ContentValues();
        contentValues.put(HistoryDB.NAME,trans.getName());
        contentValues.put(HistoryDB.QUANTITY,trans.getQuantity());
        SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        contentValues.put(HistoryDB.TDATE, dformat.format(trans.getTdate()));
        contentValues.put(HistoryDB.COST, trans.getCost());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = 0;
        if(tHistory == null){
            result = db.insert(HistoryDB.Table_Transaction,null,contentValues);
        }else{
            result = db.update(HistoryDB.Table_Transaction, contentValues, "ID = "+ trans.getId(),null);
        }

        if(result == -1){
            return false;
        }
        else{
            return true;
        }

    }

    public List<TranHistory> getAllTransaction() {
        List<TranHistory> transactions = new ArrayList<TranHistory>();
        // order by TDate
        Cursor cursor = database.query(HistoryDB.Table_Transaction, HistoryDB.allColumns, null, null, null, null, "Tdate ");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TranHistory transaction = cursorToTransaction(cursor);
            transactions.add(transaction);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return transactions;
    }

    public boolean deleteTransaction (int id) {

        String Id = String.valueOf(id);
        long result = database.delete(HistoryDB.Table_Transaction, "ID = ?",new String[] {Id});
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

  public double[] getTransactionByWeek() {
        double[] cost = new double[7];
        List<TranHistory> histories= this.getAllTransaction();
        int currDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        for(int i=0;i<histories.size();i++)
        {
            Date day = histories.get(i).getTdate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(day);
            int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
            if(0<=currDay-dayOfYear && currDay-dayOfYear<7&&histories.get(i).getCost()>0)
            {
                cost[currDay-dayOfYear]+=histories.get(i).getCost();
            }
        }
        return cost;
    }
    public double[] getTransactionByMonth() {
        double[] cost = new double[4];
        List<TranHistory> histories = this.getAllTransaction();
        int currDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        for (int i = 0; i < histories.size(); i++) {
            Date day = histories.get(i).getTdate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(day);
            int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
            if(histories.get(i).getCost()>0) {
                if (0 <= currDay - dayOfYear && currDay - dayOfYear < 7) {
                    cost[0] += histories.get(i).getCost();
                }
                if (7 <= currDay - dayOfYear && currDay - dayOfYear < 14) {
                    cost[1] += histories.get(i).getCost();
                }
                if (14 <= currDay - dayOfYear && currDay - dayOfYear < 21) {
                    cost[2] += histories.get(i).getCost();
                }
                if (21 <= currDay - dayOfYear && currDay - dayOfYear < 28) {
                    cost[3] += histories.get(i).getCost();
                }
            }
        }
        return cost;
    }
    public double getThisWeekTotal() {
        double cost = 0;
        List<TranHistory> histories= this.getAllTransaction();
        int currMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currWeek = Calendar.getInstance().get(Calendar.WEEK_OF_MONTH);
        for(int i=0;i<histories.size();i++)
        {
            Date day = histories.get(i).getTdate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(day);
            int currmon = cal.get(Calendar.MONTH);
            int currwek = cal.get(Calendar.WEEK_OF_MONTH);
            if(currmon==currMonth&&currWeek==currwek&&histories.get(i).getCost()>0)
            {
                cost += histories.get(i).getCost();
            }
        }
        return cost;
    }
    public double getLastWeekTotal() {
        double cost = 0;
        List<TranHistory> histories= this.getAllTransaction();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date(System.currentTimeMillis()-60*60*24*1000*7));
        int currMonth = cal2.get(Calendar.MONTH);
        int currWeek = cal2.get(Calendar.WEEK_OF_MONTH);
        for(int i=0;i<histories.size();i++)
        {
            Date day = histories.get(i).getTdate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(day);
            int currmon = cal.get(Calendar.MONTH);
            int currwek = cal.get(Calendar.WEEK_OF_MONTH);
            if(currmon==currMonth&&currWeek==currwek&&histories.get(i).getCost()>0)
            {
                cost += histories.get(i).getCost();
            }
        }
        return cost;
    }
    public double getDailyAverage() {

        int curDay=Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        return getThisWeekTotal()/curDay;
    }

    private TranHistory cursorToTransaction(Cursor cursor) {
        TranHistory transaction = new TranHistory();
        try {
            transaction.setId(cursor.getInt(cursor.getColumnIndex(HistoryDB.ID)));
            transaction.setName(cursor.getString(cursor.getColumnIndex(HistoryDB.NAME)));
            transaction.setQuantity(cursor.getInt(cursor.getColumnIndex(HistoryDB.QUANTITY)));
            SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            transaction.setTdate(dformat.parse(cursor.getString(cursor.getColumnIndex(HistoryDB.TDATE))));
            transaction.setCost(cursor.getDouble(cursor.getColumnIndex(MenuDB.COSTCOL)));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return transaction;
    }
}
