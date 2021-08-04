package mb.mungboih.RainbowTextView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import mb.mungboih.RainbowTextView.AnimationListener;
import mb.mungboih.RainbowTextView.DisplayUtils;
import mb.mungboih.RainbowTextView.HTextView;
import android.content.res.*;
import android.graphics.*;

public class RainbowTextView extends HTextView {

    private Matrix mMatrix;
    private float mTranslate;
    private float colorSpeed;
    private float colorSpace;
    private int[] colors = {0xFFFF2B22, 0xFFFF7F22, 0xFFEDFF22, 0xFF22FF22, 0xFF22F4FF, 0xFF2239FF, 0xFF5400F7};
    private LinearGradient mLinearGradient;

    public RainbowTextView(Context context) {
        this(context, null);
    }

    public RainbowTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RainbowTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    @Override
    public void setAnimationListener(AnimationListener listener) {
        throw new UnsupportedOperationException("Invalid operation for rainbow");
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        int[] tp = {0x7f010000, 0x7f010001};
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,tp);
        colorSpace = typedArray.getDimension(1, DisplayUtils.dp2px(150));
        colorSpeed = typedArray.getDimension(1, DisplayUtils.dp2px(5));
        typedArray.recycle();

        mMatrix = new Matrix();
        initPaint();
    }

    public float getColorSpace() {
        return colorSpace;
    }

    public void setColorSpace(float colorSpace) {
        this.colorSpace = colorSpace;
    }

    public float getColorSpeed() {
        return colorSpeed;
    }

    public void setColorSpeed(float colorSpeed) {
        this.colorSpeed = colorSpeed;
    }

    public void setColors(int... colors) {
        this.colors = colors;
        initPaint();
    }

    private void initPaint() {
        mLinearGradient = new LinearGradient(0, 0, colorSpace, 0, colors, null, Shader.TileMode.MIRROR);
        getPaint().setShader(mLinearGradient);
    }

    @Override
    public void setProgress(float progress) {
    }

    @Override
    public void animateText(CharSequence text) {
        setText(text);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mMatrix == null) {
            mMatrix = new Matrix();
        }
        mTranslate += colorSpeed;
        mMatrix.setTranslate(mTranslate, 0);
        mLinearGradient.setLocalMatrix(mMatrix);
        super.onDraw(canvas);
        postInvalidateDelayed(100);
    }
}
