package br.com.gsn.sysbusapp.abstraction;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import java.util.Date;

import br.com.gsn.sysbusapp.dialog.DatePickerFragment;
import br.com.gsn.sysbusapp.task.TaskOperation;
import br.com.gsn.sysbusapp.util.Dates;

/**
 * Marca uma classe que realiza operaçães de rede por meio de AsyncTask
 */
public abstract class BusinessTaskOperation<Params,Progress, Result> implements TaskOperation<Params, Progress, Result> {

    protected Activity context;
    protected DialogInterface currentDatePicker;

    public void setCurrentDatePicker(DialogInterface currentDatePicker) {
        this.currentDatePicker = currentDatePicker;
    }

    public void closeDataPicker() {
        currentDatePicker.cancel();
    }

    public interface DatePicker {
        void onCreateDialog();
        void onCloseDialog();
    }

    public class Foo {
        private AppCompatActivity context;
        private DialogFragment calendar;
        private EditText source;

        public Foo(AppCompatActivity context, EditText source) {
            this.context = context;
            this.source = source;
        }

        public void show() {
            calendar = new DatePickerFragment();
            calendar.show(context.getSupportFragmentManager(), "calendar");
        }

        public void onDataSet(Date date) {
            source.setText(Dates.format(date, Dates.FORMAT_PT_BR_DATE));
        }
    }

}
