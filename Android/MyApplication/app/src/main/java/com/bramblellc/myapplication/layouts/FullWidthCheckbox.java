package com.bramblellc.myapplication.layouts;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bramblellc.myapplication.R;


public class FullWidthCheckbox extends RelativeLayout {

    private TextView primaryTextView;
    private TextView secondaryTextView;
    private CheckBox fullWidthCheckbox;
    private RelativeLayout textViewContainer;


    private String text;
    private boolean lowerText;
    private boolean lowerLine;

    public FullWidthCheckbox(Context context) {
        super(context);
    }

    public FullWidthCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FullWidthCheckbox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FullWidthButton);
        //Use a
        this.text = a.getString(R.styleable.FullWidthButton_android_text);
        this.lowerText = a.getBoolean(R.styleable.FullWidthButton_lowerText, false);
        this.lowerLine = a.getBoolean(R.styleable.FullWidthButton_lowerLine, false);
        //Don't forget this
        a.recycle();

        this.fullWidthCheckbox = new CheckBox(context);
        this.fullWidthCheckbox.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.full_width_button_nobottom));

        if(lowerLine)
            fullWidthCheckbox.setBackground(ContextCompat.getDrawable(context, R.drawable.full_width_checkbox_selector));
        else
            fullWidthCheckbox.setBackground(ContextCompat.getDrawable(context, R.drawable.full_width_checkbox_selector_nobottom));

        LayoutParams buttonParams = new LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        this.addView(fullWidthCheckbox, buttonParams);

        // retrieves the density of current phone for scaling px to dp in setPadding(w,x,y,z);
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;

        this.primaryTextView = new TextView(context);
        this.primaryTextView.setText(text);
        this.primaryTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.list_item_primary_text_size));
        // left, top, right, bottom v v v v
        this.primaryTextView.setPadding(12*(int)logicalDensity, 0, 0, 0);

        this.secondaryTextView = new TextView(context);

        LayoutParams params = new LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        if(lowerText) {
            // configures the secondary textview
            this.secondaryTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.list_item_secondary_text_size));
            this.secondaryTextView.setTextColor(context.getResources().getColor(R.color.secondary_body_text_color));
            this.secondaryTextView.setPadding(12*(int)logicalDensity, 0, 0, 0);

            LayoutParams paramsPrimary = new LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            this.primaryTextView.setId(1);

            LayoutParams paramsSecondary = new LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsSecondary.addRule(RelativeLayout.BELOW, primaryTextView.getId());

            // adds primary and secondary textviews to the relative layout container
            this.textViewContainer = new RelativeLayout(context);
            this.textViewContainer.addView(primaryTextView, paramsPrimary);
            this.textViewContainer.addView(secondaryTextView, paramsSecondary);

            // adds the container to this custom relative layout with appropriate "rules"
            this.addView(textViewContainer, params);
        }
        else {
            // adds the primary textview to this custom relative layout with appropriate "rules"
            this.addView(primaryTextView, params);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        fullWidthCheckbox.setEnabled(enabled);
        if(enabled) {
            this.primaryTextView.setTextColor(getResources().getColor(R.color.primary_body_text_color));
            this.secondaryTextView.setTextColor(getResources().getColor(R.color.secondary_body_text_color));
            if(lowerLine)
                this.fullWidthCheckbox.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.full_width_checkbox_selector));
            else
                this.fullWidthCheckbox.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.full_width_checkbox_selector_nobottom));
        }
        else {
            if(lowerLine)
                this.fullWidthCheckbox.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.full_width_button));
            else
                this.fullWidthCheckbox.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.full_width_button_nobottom));
            this.primaryTextView.setTextColor(getResources().getColor(R.color.darkGray));
            this.secondaryTextView.setTextColor(getResources().getColor(R.color.mediumGray));
        }
    }

    public boolean isChecked() {
        return fullWidthCheckbox.isChecked();
    }

    public void setChecked(boolean checked) {
        fullWidthCheckbox.setChecked(checked);
    }

    public Button getFullWidthCheckbox() {
        return this.fullWidthCheckbox;
    }

    public String getSecondaryTextViewText() {
        return secondaryTextView.getText().toString();
    }

    public void setSecondaryTextViewText(String newText) {
        secondaryTextView.setText(newText);
    }
}

