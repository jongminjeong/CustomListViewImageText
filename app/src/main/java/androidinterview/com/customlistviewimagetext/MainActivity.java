package androidinterview.com.customlistviewimagetext;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends Activity implements AbsListView.OnScrollListener {
	private ListView list;


	private String[] navItems = {"가", "나", "다",
			"라", "마"};
	private ListView lvNavList;
	private DrawerLayout dlDrawer;
	private ActionBarDrawerToggle dtToggle;


	private ArrayList<fromServerData> objects;
	private CustomListAdapter adapter;
	private String serverURL = "http://128.199.102.18/Mapics/Controller/map_load.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		list=(ListView)findViewById(R.id.list);

		lvNavList = (ListView)findViewById(R.id.lv_activity_main_nav_list);
		lvNavList.setAdapter(
				new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navItems));

		lvNavList.setOnItemClickListener(new DrawerItemClickListener());

		dlDrawer = (DrawerLayout)findViewById(R.id.drawer);
		dtToggle = new ActionBarDrawerToggle(this, dlDrawer,
				R.drawable.ic_drawer, R.string.open_drawer, R.string.close_drawer){

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
		};
		dlDrawer.setDrawerListener(dtToggle);
		getActionBar().setDisplayHomeAsUpEnabled(true);




		objects = new ArrayList<fromServerData>();
		adapter = new CustomListAdapter(this, objects);
		list.setAdapter(adapter);
		sendData();

	}
	protected void onPostCreate(Bundle savedInstanceState){
		super.onPostCreate(savedInstanceState);
		dtToggle.syncState();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(dtToggle.onOptionsItemSelected(item)){
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		dtToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		//
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		//
	}


	private class DrawerItemClickListener implements ListView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			switch(position){
				case 0:
					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
			}
			dlDrawer.closeDrawer(lvNavList);
		}
	}



	private class DownloadJson extends AsyncTask<String,String,String> {
		@Override
		protected String doInBackground(String... arg0) {
			try {
				String content = executeClient(arg0[0]);
				return content;
			} catch (Exception e) {
				return "Json download failed";
			}
		}

		protected void onPostExecute(String result) {
			try {
				JSONObject jArray = new JSONObject(result);

				String[] jsonName = {"mapInfo0","mapInfo1","mapInfo2","mapInfo3","mapInfo4"};
				String[][] parsedData = new String[jArray.length()][jsonName.length];

				JSONObject json = null;
				for (int i=0; i < jArray.length(); i++) {
					json = jArray.getJSONObject("mapInfo"+i);
					if (json != null) {
						for (int j=0; j < jsonName.length; j++) {
							parsedData[i][j] = json.getString("mapCapture");
						}
					}
				}

				for (int i=0; i < parsedData.length; i++) {
					objects.add(new fromServerData());
					adapter.notifyDataSetChanged();
					objects.get(i).setMapCaptureUrl(parsedData[i][2]);
					startImageTaskThread(i);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		// 실제 전송하는 부분
		public String executeClient(String url) {
			String myResult = null;
			try {
				//--------------------------
				//   URL 설정하고 접속하기
				//--------------------------
				URL serverUrl = new URL(url);       // URL 설정
				HttpURLConnection http = (HttpURLConnection) serverUrl.openConnection();   // 접속
				//--------------------------
				//   전송 모드 설정 - 기본적인 설정이다
				//--------------------------
				http.setDefaultUseCaches(false);
				http.setDoInput(true);                         // 서버에서 읽기 모드 지정
				http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
				http.setRequestMethod("POST");         // 전송 방식은 POST

				// 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
				http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
				//--------------------------
				//   서버에서 전송받기
				//--------------------------
				InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR");
				BufferedReader reader = new BufferedReader(tmp);
				StringBuilder builder = new StringBuilder();
				String str;
				while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
					builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
				}
				myResult = builder.toString();                       // 전송결과를 전역 변수에 저장
				return myResult;
			} catch (MalformedURLException e) {
				//
			} catch (IOException e) {
				//
			} // try
			return null;
		}
	}

	public void sendData() {
		// set the server URL
		String url = serverURL;

		// call data from web URL
		try {
			ConnectivityManager conManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = conManager.getActiveNetworkInfo();

			if (netInfo != null && netInfo.isConnected()) {
				new DownloadJson().execute(url);
			} else {
				Toast toast = Toast.makeText(getApplicationContext(), "Network isn't connected", Toast.LENGTH_LONG);
				toast.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




	public void startImageTaskThread(int position)
	{
		ImageTask imagetask = new ImageTask(adapter);
		imagetask.execute(position);
	}
}

