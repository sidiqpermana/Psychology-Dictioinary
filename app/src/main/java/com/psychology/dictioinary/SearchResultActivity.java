package com.psychology.dictioinary;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.psychology.dictioinary.adapter.ListKataAdapter;
import com.psychology.dictioinary.helper.KamusHelper;
import com.psychology.dictioinary.model.KamusModel;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity{
	private ListView lvSearch;
	private ListKataAdapter listKataAdapter;
	
	public static String searchKey = "searchKey";
	public String keyword = "";
	
	private KamusHelper kamusHelper;
	
	private ArrayList<KamusModel> listData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

        kamusHelper = new KamusHelper(SearchResultActivity.this);
        kamusHelper.open();
        listData = new ArrayList<KamusModel>();
        handleIntent(getIntent());

        getSupportActionBar().setTitle("Hasil Pencarian");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		lvSearch = (ListView)findViewById(R.id.lvSearch);
		keyword = getIntent().getStringExtra(searchKey);
		
		lvSearch.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				MainActivity.showMeaningDialog(SearchResultActivity.this, listData.get(arg2));
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		if (kamusHelper != null) {
			kamusHelper.close();
		}
	}
	
	private class SearchData extends AsyncTask<Void, Void, Void>{
		
		ProgressDialog mProgressDialog;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

            mProgressDialog = new ProgressDialog(SearchResultActivity.this);
            mProgressDialog.setTitle(getString(R.string.notify_searching));
            mProgressDialog.setMessage(getString(R.string.text_please_wait));
            mProgressDialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			listData = kamusHelper.getSearchResult(keyword);
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			mProgressDialog.dismiss();
			
			if (listData.size()>0) {
				listKataAdapter = new ListKataAdapter(SearchResultActivity.this, listData);
				lvSearch.setAdapter(listKataAdapter);
			} else {
				Toast.makeText(SearchResultActivity.this, getString(R.string.error_data_not_found), Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home){
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {

		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			keyword = intent.getStringExtra(SearchManager.QUERY);
            new SearchData().execute();
		}
	}
}
