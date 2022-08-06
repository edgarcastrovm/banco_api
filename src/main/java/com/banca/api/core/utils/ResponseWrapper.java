package com.banca.api.core.utils;

public class ResponseWrapper<E> {

    private String code;

    private E result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public E getResult() {
        return result;
    }

    public void setResult(E result) {
        this.result = result;
    }

    public static class ResponseWrapperBuilder<E> {
        private ResponseWrapper<E> obj = new ResponseWrapper<E>();

        public ResponseWrapperBuilder<E> setCode(String code) {
            obj.setCode(code);
            return this;
        }

        public ResponseWrapperBuilder<E> setResult(E result) {
            obj.setResult(result);
            return this;
        }

        public ResponseWrapper<E> build() {
            return obj;
        }
    }

}
