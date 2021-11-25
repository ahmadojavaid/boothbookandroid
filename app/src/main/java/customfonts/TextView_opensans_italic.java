package customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by wolfsoft1 on 31/1/18.
 */

public class TextView_opensans_italic extends TextView {
    public TextView_opensans_italic(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextView_opensans_italic(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextView_opensans_italic(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Italic.ttf");
            setTypeface(tf);
        }
    }
}
