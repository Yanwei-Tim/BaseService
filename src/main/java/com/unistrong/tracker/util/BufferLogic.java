package com.unistrong.tracker.util;

/**
 * Created by gongdezhi on 8/9/2014.
 */
public class BufferLogic {
    public static final String RET = "ret";

    public static final String DESC = "desc";

    public enum RES{
        RES_DEFAULT,
        RES_SUCCESS,
        RES_ERROR;
        public Integer getValue(){
            return ordinal();
        }
    }

    public enum Shape {
        shape_default ("0"),
        circle ("1"),
        rectangle ("2");

        private String code;

        private Shape(String code){
            this.code=code;
        }

        public String getCode() {
            return code;
        }

    }

    public enum Operate{
        add ("1"),
        update ("2"),
        delete ("3");

        private String code;

        private Operate(String code){
            this.code=code;
        }
        public String getCode() {
            return code;
        }


    }
}
