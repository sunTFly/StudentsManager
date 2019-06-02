package util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ListViewForScrollView extends ListView {
	public ListViewForScrollView(Context context) {
		super(context);
	}

	public ListViewForScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ListViewForScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// Integer.MAX_VALUE:��ʾint�����ܹ���ʾ�����ֵ��ֵΪ2��31�η�-1
		// >>2:����Nλ�൱�ڳ���2��N����
		// MeasureSpec.AT_MOST���Ӳ��ֿ��Ը����Լ��Ĵ�Сѡ�������С��ģʽ
		int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
	}
}
