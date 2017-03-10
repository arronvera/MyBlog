package code.vera.myblog.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vera on 2017/3/10 0010.
 */

public class PictureUtils {
   private static final File file=new File(Environment.getExternalStorageDirectory()+"circle/image");

    /**
     * 保存图片
     */
    public static void  savePic(Bitmap bitmap, Context context){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd _HH_mm_ss");
        if (!file.isDirectory()){
            file.mkdir();//新建
        }
        String fileName=file+"/"+simpleDateFormat.format(new Date())+".png";//文件名
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream=new FileOutputStream(fileName);
            if (null!=fileOutputStream){
                bitmap.compress(Bitmap.CompressFormat.PNG,90,fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                ToastUtil.showToast(context,"图片保存成功");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩图片
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }
}
