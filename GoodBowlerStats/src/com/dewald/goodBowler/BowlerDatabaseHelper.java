package com.dewald.goodBowler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BowlerDatabaseHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "bowlerdata";
	private static final int DATABASE_VERSION = 1;
	
	// Database creation sql statement
	private static final String BOWLER_DATABASE_CREATE = "CREATE TABLE bowler (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "name TEXT NOT NULL, average TEXT NOT NULL);";
	private static final String LEAGUE_DATABASE_CREATE = "CREATE TABLE league (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "leaguename TEXT NOT NULL, house TEXT NOT NULL, bowlername TEXT NOT NULL);";
	private static final String LEAGUE_NIGHT_DATABASE_CREATE = "CREATE TABLE leaguenight (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "bowlername TEXT NOT NULL, leaguename TEXT NOT NULL, date TEXT NOT NULL, gameonescore INTEGER NOT NULL, " 
					+ "gametwoscore INTEGER NOT NULL, gamethreescore INTEGER NOT NULL, seriesscore INTEGER NOT NULL);";
	private static final String BOWLING_GAME_DATABASE_CREATE = "CREATE TABLE game (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "bowlername TEXT NOT NULL, leaguename TEXT NOT NULL, date TEXT NOT NULL, gamenumber TEXT NOT NULL, "
					+ "f1b1pins TEXT NOT NULL, f1b1ball TEXT NOT NULL, f1b1mark TEXT NOT NULL, f1b1feet TEXT NOT NULL, " 
					+ "f1b2pins TEXT NOT NULL, f1b2ball TEXT NOT NULL, f1b2mark TEXT NOT NULL, f1b2feet TEXT NOT NULL, "
					+ "f2b1pins TEXT NOT NULL, f2b1ball TEXT NOT NULL, f2b1mark TEXT NOT NULL, f2b1feet TEXT NOT NULL, "
					+ "f2b2pins TEXT NOT NULL, f2b2ball TEXT NOT NULL, f2b2mark TEXT NOT NULL, f2b2feet TEXT NOT NULL, "
					+ "f3b1pins TEXT NOT NULL, f3b1ball TEXT NOT NULL, f3b1mark TEXT NOT NULL, f3b1feet TEXT NOT NULL, "
					+ "f3b2pins TEXT NOT NULL, f3b2ball TEXT NOT NULL, f3b2mark TEXT NOT NULL, f3b2feet TEXT NOT NULL, "
					+ "f4b1pins TEXT NOT NULL, f4b1ball TEXT NOT NULL, f4b1mark TEXT NOT NULL, f4b1feet TEXT NOT NULL, "
					+ "f4b2pins TEXT NOT NULL, f4b2ball TEXT NOT NULL, f4b2mark TEXT NOT NULL, f4b2feet TEXT NOT NULL, "
					+ "f5b1pins TEXT NOT NULL, f5b1ball TEXT NOT NULL, f5b1mark TEXT NOT NULL, f5b1feet TEXT NOT NULL, "
					+ "f5b2pins TEXT NOT NULL, f5b2ball TEXT NOT NULL, f5b2mark TEXT NOT NULL, f5b2feet TEXT NOT NULL, "
					+ "f6b1pins TEXT NOT NULL, f6b1ball TEXT NOT NULL, f6b1mark TEXT NOT NULL, f6b1feet TEXT NOT NULL, "
					+ "f6b2pins TEXT NOT NULL, f6b2ball TEXT NOT NULL, f6b2mark TEXT NOT NULL, f6b2feet TEXT NOT NULL, "
					+ "f7b1pins TEXT NOT NULL, f7b1ball TEXT NOT NULL, f7b1mark TEXT NOT NULL, f7b1feet TEXT NOT NULL, "
					+ "f7b2pins TEXT NOT NULL, f7b2ball TEXT NOT NULL, f7b2mark TEXT NOT NULL, f7b2feet TEXT NOT NULL, "
					+ "f8b1pins TEXT NOT NULL, f8b1ball TEXT NOT NULL, f8b1mark TEXT NOT NULL, f8b1feet TEXT NOT NULL, "
					+ "f8b2pins TEXT NOT NULL, f8b2ball TEXT NOT NULL, f8b2mark TEXT NOT NULL, f8b2feet TEXT NOT NULL, "
					+ "f9b1pins TEXT NOT NULL, f9b1ball TEXT NOT NULL, f9b1mark TEXT NOT NULL, f9b1feet TEXT NOT NULL, "
					+ "f9b2pins TEXT NOT NULL, f9b2ball TEXT NOT NULL, f9b2mark TEXT NOT NULL, f9b2feet TEXT NOT NULL, "
					+ "f10b1pins TEXT NOT NULL, f10b1ball TEXT NOT NULL, f10b1mark TEXT NOT NULL, f10b1feet TEXT NOT NULL, "
					+ "f10b2pins TEXT NOT NULL, f10b2ball TEXT NOT NULL, f10b2mark TEXT NOT NULL, f10b2feet TEXT NOT NULL, "
					+ "f10b3pins TEXT NOT NULL, f10b3ball TEXT NOT NULL, f10b3mark TEXT NOT NULL, f10b3feet TEXT NOT NULL)";
	private static final String BALL_DATABASE_CREATE = "CREATE TABLE ball (_id INTEGER PRIMARY KEY AUTOINCREMENT, bowlername TEXT NOT NULL, bowlingball TEXT NOT NULL)"; 
	
	
	public BowlerDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	//Method called when the database is created
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(BOWLER_DATABASE_CREATE);
		database.execSQL(LEAGUE_DATABASE_CREATE);
		database.execSQL(LEAGUE_NIGHT_DATABASE_CREATE);
		database.execSQL(BOWLING_GAME_DATABASE_CREATE);
		database.execSQL(BALL_DATABASE_CREATE);
	}

	@Override
	//Method used to update the version of the database
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(BowlerDatabaseHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS bowler");
		database.execSQL("DROP TABLE IF EXISTS league");
		database.execSQL("DROP TABLE IF EXISTS leaguenight");
		database.execSQL("DROP TABLE IF EXISTS game");
		database.execSQL("DROP TABLE IF EXISTS ball");
		onCreate(database);
		
	}

}
