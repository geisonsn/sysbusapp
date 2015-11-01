package br.com.gsn.sysbusapp.abstraction;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.task.TaskOperation;
import br.com.gsn.sysbusapp.util.DatePickerUtil;
import br.com.gsn.sysbusapp.util.TimePickerUtil;

/**
 * Marca uma classe que realiza operaçães de rede por meio de AsyncTask
 */
public abstract class BusinessTaskOperation<Params,Progress, Result> implements TaskOperation<Params, Progress, Result> {

    protected Activity context;
    protected DatePickerUtil.DatePickerWrapper datePickerWrapper;
    protected TimePickerUtil.TimePickerWrapper timePickerWrapper;
    protected MenuItem menuItemProgressBar;
    protected Menu menu;

    protected BusinessTaskOperation() {
        this.datePickerWrapper =  new DatePickerUtil.DatePickerWrapper();
        this.timePickerWrapper =  new TimePickerUtil.TimePickerWrapper();
    }

    public DatePickerUtil.DatePickerWrapper getDatePickerWrapper() {
        return datePickerWrapper;
    }

    public TimePickerUtil.TimePickerWrapper getTimePickerWrapper() {
        return timePickerWrapper;
    }

    public void setMenuItemProgressBar(MenuItem menuItemProgressBar) {
        this.menuItemProgressBar = menuItemProgressBar;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Menu getMenu() {
        return this.menu;
    }

    public void showMenuItemProgressBar() {
        this.menuItemProgressBar.setVisible(true);
    }

    public void hideMenuItemProgressBar() {
        this.menuItemProgressBar.setVisible(false);
    }

    public void showNoConnectionMessage() {
        Toast.makeText(context, R.string.voce_nao_esta_conectado, Toast.LENGTH_SHORT).show();
    }

}
