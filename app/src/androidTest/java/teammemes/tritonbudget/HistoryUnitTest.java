package teammemes.tritonbudget;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import teammemes.tritonbudget.db.HistoryDataSource;
import teammemes.tritonbudget.db.MenuDataSource;
import teammemes.tritonbudget.db.TranHistory;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class HistoryUnitTest {
    Context appContext;
    HistoryDataSource ds;

    @Before
    public void setup() {
        appContext = InstrumentationRegistry.getTargetContext();
        ds = new HistoryDataSource(appContext);
    }

    @After
    public void cleanup() {
        ds.close();
    }

    @Test
    public void createTransactions() throws Exception {
        TranHistory menuTran = createMenuTransaction();
        assertEquals("MenuTran",1,menuTran.getMenuId());
        assertEquals("MenuTran",0.84,menuTran.getCost(),0.01);
        TranHistory cashTran = createCashTransaction();
        assertEquals("MenuTran",-1,cashTran.getMenuId());
        assertEquals("MenuTran",-900.0,cashTran.getCost(),0.01);
    }

    private TranHistory createMenuTransaction() {
        int menuId = 1;
        MenuDataSource dataSource = new MenuDataSource(appContext);
        Menu menu = dataSource.getMenuById(menuId);
        dataSource.close();
        TranHistory tran = new TranHistory();
        if (menu != null) {
            tran.setMenuId(menuId);
            tran.setCost(menu.getCost());
        } else
            tran.setMenuId(-1);//cash (deposit)
        tran.setTdate(new Date());
        tran.setQuantity(tran.getQuantity());
        TranHistory newTran = ds.createTransaction(tran);
        return newTran;
    }

    private TranHistory createCashTransaction() {
        TranHistory tran = new TranHistory();
        tran.setMenuId(-1);
        tran.setCost(-900.0);
        tran.setQuantity(1);
        tran.setTdate(new Date());
        TranHistory newTran = ds.createTransaction(tran);
        return newTran;
    }
}