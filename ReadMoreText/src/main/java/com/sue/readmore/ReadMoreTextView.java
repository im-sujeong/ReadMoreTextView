package com.sue.readmore;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

public class ReadMoreTextView extends AppCompatTextView {
    Context context;

    String readMoreText;
    int readMoreTextColor;

    public ReadMoreTextView(Context context) {
        super(context);

        init();
        setReadMore();
    }

    public ReadMoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        init();
        setTypeArray(attrs, 0);
        setReadMore();
    }

    public ReadMoreTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        init();
        setTypeArray(attrs, defStyleAttr);
        setReadMore();
    }

    private void init() {
        readMoreTextColor = ContextCompat.getColor(context, R.color.colorAccent);
        readMoreText = "...";
    }

    private void setReadMore() {
        post(new Runnable() {
            @Override
            public void run() {
                String originText = getText().toString();
                int maxLines = getMaxLines() > 100 ? -1 : getMaxLines();

                if( maxLines > -1 ) {
                    int origLineEndIndex = getLayout().getLineEnd(getLineCount()-1);
                    int lastLineEndIndex = getLayout().getLineVisibleEnd(maxLines - 1);

                    if( lastLineEndIndex < origLineEndIndex ) {
                        String changeText = originText.substring(0, lastLineEndIndex - readMoreText.length()) + readMoreText;

                        SpannableStringBuilder ssb = new SpannableStringBuilder(changeText);
                        ssb.setSpan(new ForegroundColorSpan(readMoreTextColor), lastLineEndIndex - readMoreText.length(), lastLineEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        setText(ssb);
                    }
                }
            }
        });
    }

    private void setTypeArray(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ReadMoreTextView, defStyle, 0);

        readMoreText = typedArray.getString(R.styleable.ReadMoreTextView_readMoreText) == null ? readMoreText : typedArray.getString(R.styleable.ReadMoreTextView_readMoreText);
        readMoreTextColor = typedArray.getColor(R.styleable.ReadMoreTextView_readMoreTextColor, readMoreTextColor);

        typedArray.recycle();
    }
}
