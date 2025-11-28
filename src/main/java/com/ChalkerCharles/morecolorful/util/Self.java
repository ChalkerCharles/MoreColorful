package com.ChalkerCharles.morecolorful.util;

public interface Self<T> {
    @SuppressWarnings("unchecked")
    default T moreColorful$self() {
        return (T) this;
    }
}
