package org.ediver.ascuba;

import org.ediver.ascuba.db.DBManager;

import android.app.Application;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AScuba extends Application {
	public DBManager DAO = null;
	public static final String TAG = "org.ediver.ascuba";
	@Override
	public void onCreate() {
		super.onCreate();
		try {
			Log.d(TAG,"Starting ediver version: "+ getPackageManager().getPackageInfo( "org.ediver.ascuba",0 ).versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DAO = DBManager.getInstance();
		DAO.initialize(this);
		Log.d(TAG,"Initializing application aScuba done");
	}
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		DAO.destroy();
	}
	
	private void test(){
		SQLiteDatabase database = DAO.getDatabase();
		
		
	}
	
}
