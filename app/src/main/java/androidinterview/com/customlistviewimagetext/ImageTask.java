package androidinterview.com.customlistviewimagetext;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * Created by jong-min on 2015-08-16.
 */
public class ImageTask extends AsyncTask<Integer, Integer, Integer> {
    private CustomListAdapter adapter;
    public ImageTask(CustomListAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected Integer doInBackground(Integer... params) {
        // TODO Auto-generated method stub
        downloadBitmap(params[0]);
        return null;
    }

    protected void onPostExecute(Integer result){
        adapter.notifyDataSetChanged();
    }


    public void downloadBitmap(int position)
    {
        InputStream bis;
        try {
            bis = new java.net.URL("http://128.199.102.18/Mapics/"+adapter.getObjects().get(position).getMapCaptureUrl()).openStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Bitmap bm = BitmapFactory.decodeStream(new FlushedInputStream(bis),null,options);
            adapter.getObjects().get(position).setImage(bm);
            adapter.getObjects().get(position).setText1("샘플 텍스트");
            adapter.getObjects().get(position).setText2("샘플 텍스트");
            bis.close();

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}