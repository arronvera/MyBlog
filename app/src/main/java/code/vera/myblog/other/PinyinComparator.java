package code.vera.myblog.other;

import java.util.Comparator;

import code.vera.myblog.bean.home.UserInfoBean;

/**
 * 比较器
 */
public class PinyinComparator implements Comparator<UserInfoBean> {

	public int compare(UserInfoBean o1, UserInfoBean o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
