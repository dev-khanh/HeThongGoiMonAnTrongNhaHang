package tinh.khanh.phuc.order.Public;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by NGUYENTRITINH on 05/08/2017.
 */

public class Convert_Image_String {
    public String convertImageToString(ImageView img) {
        img.setDrawingCacheEnabled(true);
        Bitmap bm = img.getDrawingCache();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] b = stream.toByteArray();
        return Base64.encodeToString(b, 1);
    }
    public Bitmap convertStringToBitmap(String string) {
        String s = string.substring(string.indexOf(",") + 1);
        InputStream stream = new ByteArrayInputStream(Base64.decode(s.getBytes(),
                Base64.DEFAULT));
        return BitmapFactory.decodeStream(stream);
    }
}
