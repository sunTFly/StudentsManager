package util;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;

import com.example.studentsmanager.R;

public class FABImageButton extends ImageButton {
	private Context content;
	public ObjectAnimator operatingAnim;
	private boolean isAnimPlay = false;
	private int bgColor;
	private int bgColorPressed;

	public FABImageButton(Context context) {
		super(context);
		this.content = context;
		init(null);
	}

	public FABImageButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.content = context;
		init(attrs);
	}

	public FABImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.content = context;
		init(attrs);
	}

	@SuppressLint("NewApi")
	public FABImageButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		this.content = context;
		init(attrs);
	}

	public void PlayAnim() {
		if (!isAnimPlay) {
			operatingAnim = ObjectAnimator.ofFloat(this, "rotation", 0f, 45.0f);
			operatingAnim.setDuration(300);
			operatingAnim.setInterpolator(new LinearInterpolator());// ��ͣ��
			operatingAnim.start();
			isAnimPlay = true;
		} else {
			operatingAnim = ObjectAnimator.ofFloat(this, "rotation", 45.0f, 0.0f);
			operatingAnim.setDuration(300);
			operatingAnim.setInterpolator(new LinearInterpolator());// ��ͣ��
			operatingAnim.start();
			isAnimPlay = false;
		}
	}

	private Drawable createButton(int color) {
		OvalShape oShape = new OvalShape();
		ShapeDrawable sd = new ShapeDrawable(oShape);
		setWillNotDraw(false);
		sd.getPaint().setColor(color);
		ShapeDrawable sd1 = new ShapeDrawable(oShape);

		sd1.setShaderFactory(new ShapeDrawable.ShaderFactory() {
			@Override
			public Shader resize(int width, int height) {
				LinearGradient lg = new LinearGradient(0, 0, 0, height,
						new int[] { Color.WHITE, Color.GRAY, Color.DKGRAY, Color.BLACK }, null, Shader.TileMode.REPEAT);
				return lg;
			}
		});

		LayerDrawable ld = new LayerDrawable(new Drawable[] { sd1, sd });
		ld.setLayerInset(0, 0, 0, 0, 0);

		return ld;
	}

	@SuppressLint("NewApi")
	private void init(AttributeSet attrSet) {

		Resources.Theme theme = content.getTheme();
		TypedArray arr = theme.obtainStyledAttributes(attrSet, R.styleable.FAB, 0, 0);
		try {
			setBgColor(arr.getColor(R.styleable.FAB_bg_color, Color.rgb(135, 206, 250)));
			setBgColorPressed(arr.getColor(R.styleable.FAB_bg_color_pressed, Color.GRAY));
			StateListDrawable sld = new StateListDrawable();

			sld.addState(new int[] { android.R.attr.state_pressed }, createButton(bgColorPressed));
			sld.addState(new int[] {}, createButton(bgColor));
			setBackground(sld);
		}

		catch (Throwable t) {
		} finally {
			arr.recycle();
		}

	}

	public void setBgColor(int color) {
		this.bgColor = color;
	}

	public void setBgColorPressed(int color) {
		this.bgColorPressed = color;
	}
}