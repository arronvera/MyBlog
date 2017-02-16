
package code.vera.myblog.utils;

import android.support.v4.util.ArrayMap;

import code.vera.myblog.R;
import ww.com.core.Debug;


/**
 * @author : zejian
 * @time : 2016年1月5日 上午11:32:33
 * @email : shinezejian@163.com
 * @description :表情加载类,可自己添加多种表情，分别建立不同的map存放和不同的标志符即可
 */
public class EmotionUtils {

	/**
	 * 表情类型标志符
	 */
	public static final int EMOTION_FACE_TYPE=0x0001;//脸表情

	/**
	 * key-表情文字;
	 * value-表情图片资源
	 */
	public static ArrayMap<String, Integer> EMPTY_MAP;
	public static ArrayMap<String, Integer> EMOTION_CLASSIC_MAP;


	static {
		EMPTY_MAP = new ArrayMap<>();
		EMOTION_CLASSIC_MAP = new ArrayMap<>();
		EMOTION_CLASSIC_MAP.put("[呵呵]", R.drawable.d_huaixiao);
		EMOTION_CLASSIC_MAP.put("[舔屏]", R.drawable.d_tianping);
		EMOTION_CLASSIC_MAP.put("[污]", R.drawable.d_wu);
		EMOTION_CLASSIC_MAP.put("[微笑]", R.drawable.d_weixiao);
		EMOTION_CLASSIC_MAP.put("[嘻嘻]", R.drawable.d_xixi);
		EMOTION_CLASSIC_MAP.put("[吃惊]", R.drawable.d_chijing);
		EMOTION_CLASSIC_MAP.put("[害羞]", R.drawable.d_haixiu);
		EMOTION_CLASSIC_MAP.put("[挖鼻]", R.drawable.d_wabi);
		EMOTION_CLASSIC_MAP.put("[可怜]", R.drawable.d_kelian);
		EMOTION_CLASSIC_MAP.put("[可爱]", R.drawable.d_keai);
		EMOTION_CLASSIC_MAP.put("[挤眼]", R.drawable.d_jiyan);

	}

	/**
	 * 根据名称获取当前表情图标R值
	 * @param EmotionType 表情类型标志符
	 * @param imgName 名称
	 * @return
	 */
	public static int getImgByName(int EmotionType,String imgName) {
		Integer integer=null;
		switch (EmotionType){
			case EMOTION_FACE_TYPE:
				integer = EMOTION_CLASSIC_MAP.get(imgName);
				break;
			default:
				Debug.w("the emojiMap is null!! Handle Yourself ");
				break;
		}
		return integer == null ? -1 : integer;
	}

	/**
	 * 根据类型获取表情数据
	 * @param EmotionType
	 * @return
	 */
	public static ArrayMap<String, Integer> getEmojiMap(int EmotionType){
		ArrayMap EmojiMap=null;
		switch (EmotionType){
			case EMOTION_FACE_TYPE:
				EmojiMap=EMOTION_CLASSIC_MAP;
				break;
			default:
				EmojiMap=EMPTY_MAP;
				break;
		}
		return EmojiMap;
	}
}
