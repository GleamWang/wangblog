package com.example.springboot.conf;

public class EditorResult<T> {
    private String errno;
    private T data;

    public EditorResult() {
    }

    public EditorResult(T data) {
        this.data = data;
    }

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> EditorResult<T> success(T data) {
        EditorResult<T> result = new EditorResult<>(data);
        result.setErrno("0");
        return result;
    }
}
