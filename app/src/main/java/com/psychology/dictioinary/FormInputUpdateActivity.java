package com.psychology.dictioinary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.psychology.dictioinary.app.DictionaryApplication;
import com.psychology.dictioinary.helper.KamusHelper;
import com.psychology.dictioinary.model.KamusModel;

import java.util.Observable;
import java.util.Observer;

public class FormInputUpdateActivity extends AppCompatActivity implements Observer{
	private EditText edtKata, edtArti;
	private Button btnAction;
	
	private KamusHelper kamusHelper;
	
	public static int FORM_INPUT = 0;
	public static int FORM_UPDATE = 1;
	public static String KEY_KAMUS_DATA = "kamusData";
	public static String KEY_FORM_TYPE = "type";
	
	private int formType = 0;
	private String kata, arti;
	private int id;
    private KamusModel kamusItem;

    private MenuItem itemDelete;

    private DictionaryApplication application;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form_input_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		edtArti = (EditText)findViewById(R.id.edtArti);
		edtKata = (EditText)findViewById(R.id.edtKata);
		btnAction = (Button)findViewById(R.id.btnFormAction);
		
		kamusHelper = new KamusHelper(FormInputUpdateActivity.this);
		kamusHelper.open();
		application = (DictionaryApplication)getApplication();
        application.getKamusObserver().addObserver(this);

		Intent intent = getIntent();
		formType = intent.getIntExtra(KEY_FORM_TYPE, 0);

        String title = null;
		if (formType==FORM_INPUT){
			btnAction.setText("Tambah");
            title = "Tambah Data";
		} else {
			btnAction.setText("Update");
			title = "Update Data";

            kamusItem = (KamusModel) intent.getSerializableExtra(KEY_KAMUS_DATA);

			kata = kamusItem.getKata();
			arti = kamusItem.getArti();
			id = kamusItem.getId();
			
			edtArti.setText(arti);
			edtKata.setText(kata);
		}

        getSupportActionBar().setTitle(title);
		
		btnAction.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
                String word = edtKata.getText().toString().trim();
                String meaning = edtArti.getText().toString().trim();
                if (!word.equals("") && !meaning.equals("")) {
                    if (formType == FORM_INPUT) {
                        kamusHelper.insert(KamusModel.getKamusModel(word, meaning));
                        Toast.makeText(FormInputUpdateActivity.this, getString(R.string.text_success_input), Toast.LENGTH_LONG).show();
                    }else{
                        kamusHelper.update(KamusModel.getKamusModel(id, edtKata.getText().toString().trim(), edtArti.getText().toString().trim()));
                        Toast.makeText(FormInputUpdateActivity.this, getString(R.string.text_success_update), Toast.LENGTH_LONG).show();
                    }
                    application.getKamusObserver().refresh();
                    finish();
                }else{
                    Toast.makeText(FormInputUpdateActivity.this, getString(R.string.error_all_field_required), Toast.LENGTH_LONG).show();
                }
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		application.getKamusObserver().deleteObserver(this);
		if (kamusHelper != null) {
			kamusHelper.close();
		}
	}
	
	//INPUT
	public static void toFormInputUpdate(Activity activity){
		Intent intent = new Intent(activity, FormInputUpdateActivity.class);
		intent.putExtra(FormInputUpdateActivity.KEY_FORM_TYPE, FORM_INPUT);
		activity.startActivityForResult(intent, 0);
	}
	
	//UPDATE
	public static void toFormInputUpdate(Activity activity, KamusModel kamusModel){
		Intent intent = new Intent(activity, FormInputUpdateActivity.class);
        intent.putExtra(KEY_KAMUS_DATA, kamusModel);
		intent.putExtra(FormInputUpdateActivity.KEY_FORM_TYPE, FORM_UPDATE);
		activity.startActivityForResult(intent, 0);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }

        if (item.getItemId() == R.id.action_delete){
            deleteKata();
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_form, menu);
        itemDelete = menu.findItem(R.id.action_delete);
        if (formType == FORM_INPUT){
            itemDelete.setVisible(false);
        }else{
            itemDelete.setVisible(true);
        }
        return true;
    }

    private void deleteKata(){
        kamusHelper.delete(id);
        Toast.makeText(FormInputUpdateActivity.this, getString(R.string.text_success_delete), Toast.LENGTH_LONG).show();
        application.getKamusObserver().refresh();
        finish();
    }

    public void update(Observable observable, Object o) {

    }
}
