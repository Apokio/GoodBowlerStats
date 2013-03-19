package com.dewald.goodBowler;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LeagueNightActivity extends Activity implements OnClickListener, TextWatcher{
	
	private TextView tvName;
	private TextView tvLeague;
	private TextView tvDate;
	private EditText et1Score;
	private EditText et2Score;
	private EditText et3Score;
	private EditText etSeries;
	private Button btnAccept;
	private Button btnList;
	private Button btnFrameGameOne;
	private Button btnFrameGameTwo;
	private Button btnFrameGameThree;
	private BowlerDatabaseAdapter mDbHelper;
	private int requestCode;
	private Bundle extras;
	private String sqlDate;
	private String regDate;
	private String bowler;
	private String league;
	private Integer gameOne = 0;
	private Integer gameTwo = 0;
	private Integer gameThree = 0;
	private Integer series = 0;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaguenight);
        
        mDbHelper = new BowlerDatabaseAdapter(this);
        mDbHelper.open();
        
        
        extras = getIntent().getExtras();
        sqlDate = extras.getString("SQLDate");
        regDate = extras.getString("date");
        bowler = extras.getString("bowler");
        league = extras.getString("league");
        //Log.v("sqlDate", sqlDate);
        
        tvName = (TextView)findViewById(R.id.nameTV);
        tvName.setText("Bowler Name: " + bowler);
        tvLeague = (TextView)findViewById(R.id.leagueTV);
        tvLeague.setText("League Name: " + league);
        tvDate = (TextView)findViewById(R.id.dateTV);
        tvDate.setText("Date: " + regDate);
        et1Score = (EditText)findViewById(R.id.gameone);
        et1Score.addTextChangedListener(this);
        et2Score = (EditText)findViewById(R.id.gametwo);
        et2Score.addTextChangedListener(this);
        et3Score = (EditText)findViewById(R.id.gamethree);
        et3Score.addTextChangedListener(this);
        etSeries = (EditText)findViewById(R.id.series);
        btnAccept = (Button)findViewById(R.id.acceptButton);
        btnAccept.setOnClickListener(this);
        btnList = (Button)findViewById(R.id.listButton);
        btnList.setOnClickListener(this);
        btnFrameGameOne = (Button)findViewById(R.id.frameButtonGameOne);
        btnFrameGameOne.setOnClickListener(this);
        btnFrameGameTwo = (Button)findViewById(R.id.frameButtonGameTwo);
        btnFrameGameTwo.setOnClickListener(this);
        btnFrameGameThree = (Button)findViewById(R.id.frameButtonGameThree);
        btnFrameGameThree.setOnClickListener(this);
        
        checkLeagueNightExists();
        calculateSeries();
        
	}
	
	@Override
	public void onStop(){
		super.onStop();
		if(mDbHelper.checkLeagueNightRecords(bowler, league, sqlDate)){
			mDbHelper.deleteBowlerLeagueNight(bowler, league, sqlDate);
			//Log.w("League Night", "Deleted");
		}
		mDbHelper.createLeagueNight(bowler, league, sqlDate, gameOne, gameTwo, gameThree, series);
		
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		mDbHelper.close();
	}
	
	@Override
	public void finish(){
		String date = sqlDate;
			if(gameOne  >= 0 && gameOne <= 300 && gameTwo >= 0 && gameTwo <= 300 && gameThree >= 0 && gameThree <= 300 ){
			//check to see database entry already exists and delete it so multiple records do not exist
			if(mDbHelper.checkLeagueNightRecords(bowler, league, sqlDate)){
				mDbHelper.deleteBowlerLeagueNight(bowler, league, sqlDate);
				//Log.w("League Night", "Deleted");
			}
		mDbHelper.createLeagueNight(bowler, league, date, gameOne, gameTwo, gameThree, series);
		super.finish();
		}else{
			Toast t = Toast.makeText(this, "Values must be between 0 and 300", Toast.LENGTH_SHORT);
			t.show();
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState){
		savedInstanceState.putString("SQLDate", sqlDate);
		savedInstanceState.putString("RegDate", regDate);
		savedInstanceState.putString("Bowler", bowler);
		savedInstanceState.putString("League", league);
		savedInstanceState.putString("GameOne", et1Score.getText().toString());
		savedInstanceState.putString("GameTwo", et2Score.getText().toString());
		savedInstanceState.putString("GameThree", et2Score.getText().toString());
		savedInstanceState.putString("Series", etSeries.getText().toString());
		super.onSaveInstanceState(savedInstanceState);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		sqlDate = savedInstanceState.getString("SQLDate");
		regDate = savedInstanceState.getString("RegDate");
		bowler = savedInstanceState.getString("Bowler");
		league = savedInstanceState.getString("League");
		et1Score.setText(savedInstanceState.getString("GameOne"));
		et2Score.setText(savedInstanceState.getString("GameTwo"));
		et3Score.setText(savedInstanceState.getString("GameThree"));
		etSeries.setText(savedInstanceState.getString("Series"));
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.acceptButton:
			String date = sqlDate;
			if(gameOne  >= 0 && gameOne <= 300 && gameTwo >= 0 && gameTwo <= 300 && gameThree >= 0 && gameThree <= 300 ){
				//check to see database entry already exists and delete it so multiple records do not exist
				if(mDbHelper.checkLeagueNightRecords(bowler, league, sqlDate)){
					mDbHelper.deleteBowlerLeagueNight(bowler, league, sqlDate);
					//Log.w("League Night", "Deleted");
				}
			mDbHelper.createLeagueNight(bowler, league, date, gameOne, gameTwo, gameThree, series);
			finish();
			}else{
				Toast t = Toast.makeText(this, "Values must be between 0 and 300", Toast.LENGTH_SHORT);
				t.show();
			}
			break;
		case R.id.listButton:
			Intent i = new Intent(LeagueNightActivity.this, ListLeagueNight.class);
			i.putExtra("sqlDate", sqlDate);
			i.putExtra("date", regDate);
			i.putExtra("bowler", bowler);
			i.putExtra("league", league);
			startActivity(i);
			break;
		case R.id.frameButtonGameOne:
			i = new Intent(LeagueNightActivity.this, ScoreCardActivity.class);
			i.putExtra("gameNumber", "1");
			i.putExtra("sqlDate", sqlDate);
			i.putExtra("date", regDate);
			i.putExtra("bowler", bowler);
			i.putExtra("league", league);
			startActivityForResult(i, 1);
			break;
		case R.id.frameButtonGameTwo:
			i = new Intent(LeagueNightActivity.this, ScoreCardActivity.class);
			i.putExtra("gameNumber", "2");
			i.putExtra("sqlDate", sqlDate);
			i.putExtra("date", regDate);
			i.putExtra("bowler", bowler);
			i.putExtra("league", league);
			startActivityForResult(i, 2);
			break;
		case R.id.frameButtonGameThree:
			i = new Intent(LeagueNightActivity.this, ScoreCardActivity.class);
			i.putExtra("gameNumber", "3");
			i.putExtra("sqlDate", sqlDate);
			i.putExtra("date", regDate);
			i.putExtra("bowler", bowler);
			i.putExtra("league", league);
			startActivityForResult(i, 3);
			break;
		}
		
	}

	@Override
	public void afterTextChanged(Editable s) {
		try{
			if (!et1Score.getText().toString().equals("")){
			gameOne = Integer.parseInt(et1Score.getText().toString());
			}
			if (!et2Score.getText().toString().equals("")){
			gameTwo = Integer.parseInt(et2Score.getText().toString());
			}
			if (!et3Score.getText().toString().equals("")){
			gameThree = Integer.parseInt(et3Score.getText().toString());
			}
		}catch(NumberFormatException nfe){
			Toast t = Toast.makeText(this, "Values must be a number", Toast.LENGTH_SHORT);
			t.show();	
		}
		calculateSeries();
		
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == 1){
			switch(requestCode){
			case 1:
				Bundle bundle1 = data.getExtras();
				String totalScore1 = bundle1.getString("totalScore");
				et1Score.setText(totalScore1);
				break;
			case 2:
				Bundle bundle2 = data.getExtras();
				String totalScore2 = bundle2.getString("totalScore");
				et2Score.setText(totalScore2);
				break;
			case 3:
				Bundle bundle3 = data.getExtras();
				String totalScore3 = bundle3.getString("totalScore");
				et3Score.setText(totalScore3);
				break;
			}
		}
	}
	
	private void calculateSeries() {
		series = gameOne + gameTwo + gameThree;
		etSeries.setText(series.toString());
		
	}
	
	private void checkLeagueNightExists(){
		if(mDbHelper.checkLeagueNightRecords(bowler, league, sqlDate)){
			Cursor c = mDbHelper.fetchScoresForBowlerLeagueDate(bowler, league, sqlDate);
			c.moveToFirst();
			et1Score.setText(c.getString(0));
			//Log.v("game1", c.getString(0));
			et2Score.setText(c.getString(1));
			et3Score.setText(c.getString(2));
			etSeries.setText(c.getString(3));
			c.close();
		}
	}
	
}
