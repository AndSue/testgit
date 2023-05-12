package com.springboot.www.controller;

import com.springboot.www.entity.Good;
import com.springboot.www.entity.Order;
import com.springboot.www.entity.Position;
import com.springboot.www.entity.User;
import com.springboot.www.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping(value = "/andsue",method = RequestMethod.POST)
public class HelloController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private GoodServiceImpl goodService;
    @Autowired
    private PositionServiceImpl positionService;
    @Autowired
    private OrderServiceImpl orderService;

    //添加新用户
    @GetMapping("/addNewUser")
    @ResponseBody
    public boolean addNewUser(@RequestParam("id")int id,@RequestParam("name")String name,@RequestParam("password")String password,
                             @RequestParam("sex")String sex,@RequestParam("tel")String tel,@RequestParam("job")String job){
        User user=new User();
        user.setJob(job);
        user.setName(name);
        user.setPassword(password);
        user.setTel(tel);
        user.setSex(sex);
        user.setId(id);
        int addRows=userService.addNewUser(user);
        if(addRows>0){
            return true;
        }
        return false;
    }

    //根据id删除用户
    @GetMapping("/deleteUserById")
    @ResponseBody
    public boolean deleteById(@RequestParam("id")int id){
        int deletedRows=userService.deleteById(id);
        if(deletedRows>0){
            return true;
        }
        return false;
    }

    //修改用户数值
    @GetMapping("/updateUserById")
    @ResponseBody
    public boolean updateById(@RequestParam("id")int id,@RequestParam("name")String name,@RequestParam("password")String password,
                              @RequestParam("sex")String sex,@RequestParam("tel")String tel,@RequestParam("job")String job){
        User user=new User();
        user.setJob(job);
        user.setName(name);
        user.setPassword(password);
        user.setTel(tel);
        user.setSex(sex);
        user.setId(id);
        int updatedRows= userService.updateById(user);
        if(updatedRows>0){
            return true;
        }
        return false;
    }

    //展示所有用户
    @GetMapping("/showAllUsers")
    @ResponseBody
    public List<User> showAllUsers(){
        return userService.showAllUsers();
    }

    //模糊查找用户
    @GetMapping("/findByLike")
    @ResponseBody
    public List<User> findByLike(@RequestParam("condition") String condition){
        return userService.findByLike(condition);
    }

    //展示所有商品
    @GetMapping("/findAllGoods")
    @ResponseBody
    public List<Good> findAllGoods(){
        return goodService.findAllGoods();
    }

    //模糊查找商品
    @GetMapping("/findGoodsByLike")
    @ResponseBody
    public List<Good> findGoodsByLike(@RequestParam("condition") String condition){
        return goodService.findByLike(condition);
    }

    //按类别查找商品
    @GetMapping("/findGoodsByType")
    @ResponseBody
    public List<Good> findGoodsByType(@RequestParam("type") String type){
        return goodService.findByType(type);
    }

    //根据编号查找商品
    @GetMapping("/findGoodById")
    @ResponseBody
    public Good findGoodById(@RequestParam("id") String id){
        return goodService.findById(id);
    }

    //用户登录
    @GetMapping("/userLogin")
    @ResponseBody
    public User userLogin(@RequestParam("name") String name,@RequestParam("password") String password){
        return userService.login(name, password);
    }

    //按照标签查找商品
    @GetMapping("/findGoodsByTags")
    @ResponseBody
    public List<Good> userLogin(@RequestParam("tags") String[] tags){
        return goodService.findByTags(tags);
    }

    //展示用户的地址
    @GetMapping("/findPositionsByUid")
    public List<Position> findPositionsByUid(@RequestParam("uid")int uid){
        return positionService.findByUid(uid);
    }

    @GetMapping("/findPositionById")
    public Position findPositionsById(@RequestParam("id")int id){
        return positionService.findById(id);
    }

    //更改地址
    @GetMapping("/updatePositionById")
    @ResponseBody
    public boolean updatePositionById(@RequestParam("id")int id,@RequestParam("name")String name,@RequestParam("address")String address,
                              @RequestParam("default")boolean isDefault,@RequestParam("tel")String tel,@RequestParam("uid")int uid){
        Position position=new Position();
        position.setAddress(address);
        position.setName(name);
        position.setIsDefault(isDefault);
        position.setTel(tel);
        position.setUid(uid);
        position.setId(id);
        int updatedRows= positionService.updateById(position);
        if(updatedRows>0){
            return true;
        }
        return false;
    }

    //添加地址
    @GetMapping("/addNewPosition")
    @ResponseBody
    public boolean addNewPosition(@RequestParam("name")String name,@RequestParam("address")String address,
                                      @RequestParam("isDefault")boolean isDefault,@RequestParam("tel")String tel,@RequestParam("uid")int uid){
        Position position=new Position();
        position.setAddress(address);
        position.setName(name);
        position.setIsDefault(isDefault);
        position.setTel(tel);
        position.setUid(uid);
        int updatedRows= positionService.addNewPosition(position);
        if(updatedRows>0){
            return true;
        }
        return false;
    }

    //删除地址
    @GetMapping("/deletePositionById")
    @ResponseBody
    public boolean deletePositionById(@RequestParam("id")int id,@RequestParam("uid")int uid,@RequestParam("default")boolean isDefault){
        int updatedRows= positionService.deleteById(id,uid,isDefault);
        if(updatedRows>0){
            return true;
        }
        return false;
    }

    //添加订单
    @GetMapping("/addNewOrder")
    @ResponseBody
    public boolean addNewOrder(@RequestParam("fid")String fid,@RequestParam("uid")int uid,@RequestParam("price")double price,
                                  @RequestParam("num")int num,@RequestParam("pid")int pid,@RequestParam("detail")String detail){
        Order order=new Order();
        order.setFid(fid);
        order.setPid(pid);
        order.setPrice(price);
        order.setNum(num);
        order.setDetail(detail);
        order.setUid(uid);
        int updatedRows= orderService.addNewOrder(order);
        if(updatedRows>0){
            return true;
        }
        return false;
    }

    //全部订单
    @GetMapping("/findOrdersByUid")
    public List<Order> findOrdersByUid(@RequestParam("uid")int uid){
        return orderService.findByUid(uid);
    }
    //未发货
    @GetMapping("/findNotSendOrdersByUid")
    public List<Order> findNotSendOrdersByUid(@RequestParam("uid")int uid){
        return orderService.findNotSendByUid(uid);
    }
    //已发货
    @GetMapping("/findSendOrdersByUid")
    public List<Order> findSendOrdersByUid(@RequestParam("uid")int uid){
        return orderService.findSendByUid(uid);
    }
    //待评价
    @GetMapping("/findReceiveOrdersByUid")
    public List<Order> findReceiveOrdersByUid(@RequestParam("uid")int uid){
        return orderService.findReceiveByUid(uid);
    }
    //退货
    @GetMapping("/deleteOrderById")
    public int deleteOrderById(@RequestParam("id")String id){
        return orderService.deleteById(id);
    }
    //已退货
    @GetMapping("/findDeletedOrdersByUid")
    public List<Order> findDeletedOrdersByUid(@RequestParam("uid")int uid){
        return orderService.findDeletedByUid(uid);
    }
    //上传头像
    @PostMapping(value = "/uploadingPhoto")
    @ResponseBody
    public String uploadFile(@RequestBody MultipartFile file,int uid) {
        System.out.println("接收到的文件数据为：" + file);
        if (file.isEmpty()) {
            return "上传文件为空";
        }
        // 获取文件全名a.py
        String fileName = file.getOriginalFilename();
        // 文件上传路径
        String templatePath = "E:/VueProjects/flower/src/assets/user/";
        System.out.println("文件路径:" + templatePath);
        // 获取文件的后缀名
        //String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //获取文件名
        String prefixName = fileName.substring(0, fileName.lastIndexOf("."));
        // 解决中文问题,liunx 下中文路径,图片显示问题
        //fileName = UUID.randomUUID() + suffixName;
        String name="user"+uid+".jpg";
        File dest0 = new File(templatePath);
        File dest = new File(dest0, name);
        //文件上传-覆盖
        try {
            // 检测是否存在目录
            if (!dest0.getParentFile().exists()) {
                dest0.getParentFile().mkdirs();
                //检测文件是否存在
            }
            if (!dest.exists()) {
                dest.mkdirs();
            }
            file.transferTo(dest);
            userService.updatePhotoById(uid,name);
            return "上传成功";
        } catch (Exception e) {
            System.out.println("文件上传错误");
            return "上传失败";
        }

    }
}
