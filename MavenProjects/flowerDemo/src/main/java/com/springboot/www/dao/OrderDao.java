package com.springboot.www.dao;

import com.springboot.www.entity.Good;
import com.springboot.www.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderDao {
    @Autowired(required = false)
    JdbcTemplate jdbcTemplate;

    @Autowired
    GoodDao goodDao=new GoodDao();
    public List<Order> findReceiveByUid(int uid){
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from orders where uid=? and isReceive=true and isShown=true",uid);
        List<Order> orderList=new ArrayList<>();
        for(Map<String,Object> map:list){
            Order order=new Order();
            order.setPid((int) map.get("pid"));
            order.setFid((String)map.get("fid"));
            order.setNum((int)map.get("num"));
            order.setId((String)map.get("id"));
            order.setUid((int) map.get("uid"));
            order.setDetail((String)map.get("detail"));
            order.setSend((boolean) map.get("isSend"));
            order.setReceive((boolean)map.get("isReceive"));
            order.setEnough((boolean)map.get("isEnough"));
            order.setPrice((double)map.get("price"));
            order.setTime((Date)map.get("time"));
            order.setShown((boolean)map.get("isShown"));
            orderList.add(order);
        }
        return orderList;
    }
    public List<Order> findByUid(int uid){
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from orders where uid=? and isShown=true",uid);
        List<Order> orderList=new ArrayList<>();
        for(Map<String,Object> map:list){
            Order order=new Order();
            order.setPid((int) map.get("pid"));
            order.setFid((String)map.get("fid"));
            order.setNum((int)map.get("num"));
            order.setId((String)map.get("id"));
            order.setUid((int) map.get("uid"));
            order.setDetail((String)map.get("detail"));
            order.setSend((boolean) map.get("isSend"));
            order.setReceive((boolean)map.get("isReceive"));
            order.setEnough((boolean)map.get("isEnough"));
            order.setPrice((double)map.get("price"));
            order.setTime((Date)map.get("time"));
            order.setShown((boolean)map.get("isShown"));
            orderList.add(order);
        }
        return orderList;
    }
    public List<Order> findSendByUid(int uid){
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from orders where uid=? and isSend=true and isReceive=false and isShown=true",uid);
        List<Order> orderList=new ArrayList<>();
        for(Map<String,Object> map:list){
            Order order=new Order();
            order.setPid((int) map.get("pid"));
            order.setFid((String)map.get("fid"));
            order.setNum((int)map.get("num"));
            order.setId((String)map.get("id"));
            order.setUid((int) map.get("uid"));
            order.setDetail((String)map.get("detail"));
            order.setSend((boolean) map.get("isSend"));
            order.setReceive((boolean)map.get("isReceive"));
            order.setEnough((boolean)map.get("isEnough"));
            order.setPrice((double)map.get("price"));
            order.setTime((Date)map.get("time"));
            order.setShown((boolean)map.get("isShown"));
            orderList.add(order);
        }
        return orderList;
    }

    public List<Order> findNotSendByUid(int uid){
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from orders where uid=? and isSend=false and isShown=true",uid);
        List<Order> orderList=new ArrayList<>();
        for(Map<String,Object> map:list){
            Order order=new Order();
            order.setPid((int) map.get("pid"));
            order.setFid((String)map.get("fid"));
            order.setNum((int)map.get("num"));
            order.setId((String)map.get("id"));
            order.setUid((int) map.get("uid"));
            order.setDetail((String)map.get("detail"));
            order.setSend((boolean) map.get("isSend"));
            order.setReceive((boolean)map.get("isReceive"));
            order.setEnough((boolean)map.get("isEnough"));
            order.setPrice((double)map.get("price"));
            order.setTime((Date)map.get("time"));
            order.setShown((boolean)map.get("isShown"));
            orderList.add(order);
        }
        return orderList;
    }
    public List<Order> findDeletedByUid(int uid){
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from orders where uid=? and isShown=false",uid);
        List<Order> orderList=new ArrayList<>();
        for(Map<String,Object> map:list){
            Order order=new Order();
            order.setPid((int) map.get("pid"));
            order.setFid((String)map.get("fid"));
            order.setNum((int)map.get("num"));
            order.setId((String)map.get("id"));
            order.setUid((int) map.get("uid"));
            order.setDetail((String)map.get("detail"));
            order.setSend((boolean) map.get("isSend"));
            order.setReceive((boolean)map.get("isReceive"));
            order.setEnough((boolean)map.get("isEnough"));
            order.setPrice((double)map.get("price"));
            order.setTime((Date)map.get("time"));
            order.setShown((boolean)map.get("isShown"));
            orderList.add(order);
        }
        return orderList;
    }
    public int addNewOrder(Order order){
        //获取id
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from orders order by id desc limit 1");
        Date date=new Date(System.currentTimeMillis());
        String id="";
        for(Map<String,Object> map:list) {
            id = (String) map.get("id");
        }
        String nid=new SimpleDateFormat("yyyyMMdd").format(date);
        if(id.substring(0,8).equals(nid)){
            int number=Integer.parseInt(id.substring(8))+1;
            if(number>=1000){
                nid=nid+number;
            }
            else if(number>=100){
                nid=nid+"0"+number;
            }
            else if(number>=10){
                nid=nid+"00"+number;
            }
            else{
                nid=nid+"000"+number;
            }
        }
        else{
            nid=nid+"0001";
        }
        //System.out.println(nid);
        //更新库存
        Good good=goodDao.findById(order.getFid());
        //System.out.println(good.getId()+good.getNum()+good.getName());
        if(good.getNum()-order.getNum()<0){
            return 0;
        }
        good.setNum(good.getNum()-order.getNum());
        System.out.println(good.getNum());
        goodDao.updateById(good);
        //插入
        return jdbcTemplate.update("insert into orders(id,uid,fid,pid,isSend,isReceive,isEnough,isSHown,price,num,detail,time) values(?,?,?,?,?,?,?,?,?,?,?)",
                nid,order.getUid(),order.getFid(),order.getPid(),false,false,true,true,order.getPrice(),
                order.getNum(),order.getDetail(),date);
    }

    public int deleteById(String id){
        return jdbcTemplate.update("update orders set isShown=false where id=?",
                id);
    }

    public int updateReceiveByUid(String id){
        return jdbcTemplate.update("update orders set isReceive=true where id=?",
                id);
    }
}
