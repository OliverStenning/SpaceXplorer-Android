package co.roguestudios.spacexplorer.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class AssetsReader {

    public static Drawable getDrawableFromAssets(Context context, String url) {
        Drawable drawable = null;
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(url);
            drawable = Drawable.createFromStream(inputStream, null);
        } catch (IOException e) {
            Log.e("Loading", "Loading resource image");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                   e.printStackTrace();
                }
            }
        }
        return drawable;
    }

}
