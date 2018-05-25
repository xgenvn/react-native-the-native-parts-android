package com.example.rn.thenativeparts;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class CounterView extends FrameLayout implements View.OnClickListener {

  private int count;

  private TextView mCountTextView;

  public CounterView(Context context) {
    super(context);
    count = 0;
  }

  public CounterView(Context context, Activity activity) {
    super(context);
    count = 0;

    FrameLayout counterLayout = (FrameLayout) activity.getLayoutInflater().inflate(R.layout.counter, null);
    counterLayout.setOnClickListener(CounterView.this);
    this.addView(counterLayout);

    mCountTextView = counterLayout.findViewById(R.id.count_tv);
    mCountTextView.setText(String.valueOf(count));
  }

  public void setCountColor(int color) {
    mCountTextView.setTextColor(color);
  }

  @Override
  public void onClick(View view) {
    count++;
    mCountTextView.setText(String.valueOf(count));

    WritableMap event = Arguments.createMap();
    event.putInt("count", count);

    ReactContext reactContext = (ReactContext) getContext();
    reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
      getId(),
      "onCountChange",
      event);
  }

  @Override
  public void requestLayout() {
    super.requestLayout();
    post(measureAndLayout);
  }

  private final Runnable measureAndLayout = new Runnable() {
    @Override
    public void run() {
      measure(
        MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
        MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
      layout(getLeft(), getTop(), getRight(), getBottom());
    }
  };

}
