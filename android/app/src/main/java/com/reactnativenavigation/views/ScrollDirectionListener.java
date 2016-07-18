package com.reactnativenavigation.views;

import android.view.ViewGroup;
import android.view.ViewTreeObserver;

public class ScrollDirectionListener implements ViewTreeObserver.OnScrollChangedListener {
    public enum Direction {
        Up, Down
    }

    public interface OnChanged {
        void onScrollChanged(Direction direction);
    }

    private final ViewGroup view;
    private OnChanged onChanged;
    private int lastScrollY = -1;

    public ScrollDirectionListener(ViewGroup view, OnChanged onChanged) {
        this.view = view;
        this.onChanged = onChanged;
    }

    @Override
    public void onScrollChanged() {
        if (!view.getViewTreeObserver().isAlive()) {
            return;
        }

        final int scrollY = view.getScrollY();
        if (isScrollPositionChanged(scrollY) && !isTopOverscroll(scrollY) && !isBottomOverscroll(scrollY)) {
            lastScrollY = scrollY;
            onChanged.onScrollChanged(getDirection(scrollY));
        }
    }

    private Direction getDirection(int scrollY) {
        return scrollY > lastScrollY ? Direction.Down : Direction.Up;
    }

    private boolean isBottomOverscroll(int scrollY) {
        return scrollY >= (view.getChildAt(0).getHeight() - view.getHeight());
    }

    private boolean isTopOverscroll(int scrollY) {
        return scrollY <= 0;
    }

    private boolean isScrollPositionChanged(int scrollY) {
        return scrollY != lastScrollY;
    }

}
