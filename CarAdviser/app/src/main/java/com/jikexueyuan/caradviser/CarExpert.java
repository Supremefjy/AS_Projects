package com.jikexueyuan.caradviser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyaoxianzhidao on 16/1/3.
 */
public class CarExpert {
    //activity
    List<String> getCars(String type){
        List<String> cars=new ArrayList<>();

        if(type.equals("电动车")){
            cars.add("特斯拉-MODEL S");
            cars.add("比亚迪 S6");
        }else if (type.equals("跑车")){
            cars.add("阿斯顿马丁");
            cars.add("兰博基尼");
            cars.add("法拉利");
        }else if(type.equals("SUV")){
            cars.add("路虎 揽胜");
            cars.add("雷克萨斯");
            cars.add("宝马 X8");
        }else if (type.equals("皮卡")) {
            cars.add("JEEP");
            cars.add("GMC SIERA");
        }
        return cars;  //返回
    }

}
