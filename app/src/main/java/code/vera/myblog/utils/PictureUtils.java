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

/**
 * Created by vera on 2017/3/10 0010.
 */

public class PictureUtils {
    private static final File file = new File(Environment.getExternalStorageDirectory() + "/circle/image");

    /**
     * 保存图片
     */
    public static void savePic(Bitmap bitmap, Context context) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "circle");
        if (!appDir.exists()) {
            appDir.mkdir();    //创建文件夹
        }
        String fileName = System.currentTimeMillis() + ".jpg"; //需要的图片格式
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file); //获取文件流
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);  //保存成图片
            fos.flush();
            fos.close();
            ToastUtil.showToast(context, "保存图片成功~");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            ToastUtil.showToast(context, "保存图片失败~");
        } catch (IOException e) {
            e.printStackTrace();
            ToastUtil.showToast(context, "保存图片失败~");
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

    public static void saveGif(byte[] bytes, Context context) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "circle");
        if (!appDir.exists()) {
            appDir.mkdir();    //创建文件夹
        }
        String fileName = System.currentTimeMillis() + ".gif"; //需要的图片格式
        File file = new File(appDir, fileName);
        FileOutputStream fileWriter = null;
        try {
            fileWriter = new FileOutputStream(file);
            fileWriter.write(bytes);
            fileWriter.flush();
            fileWriter.close();
            ToastUtil.showToast(context, "保存图片成功~");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            ToastUtil.showToast(context, "保存图片失败~");
        } catch (IOException e) {
            e.printStackTrace();
            ToastUtil.showToast(context, "保存图片失败~");
        }
    }

}
