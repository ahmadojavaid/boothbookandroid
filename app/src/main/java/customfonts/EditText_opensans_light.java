package customfonts;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class EditText_opensans_light extends EditText {

    public EditText_opensans_light(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditText_opensans_light(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditText_opensans_light(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Light.ttf");
            setTypeface(tf);
        }
    }

}