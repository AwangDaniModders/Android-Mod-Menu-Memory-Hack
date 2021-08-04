package mb.mungboih.RainbowTextView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public abstract class HTextView extends TextView {
    public HTextView(Context context) {
        this(context, null);
    }

    public HTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public abstract void animateText(CharSequence charSequence);

    public abstract void setAnimationListener(AnimationListener animationListener);

    public abstract void setProgress(float f);
}
