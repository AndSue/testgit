package com.springboot.www.service;

import com.springboot.www.entity.Position;
import javafx.geometry.Pos;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PositionService {
    List<Position> findByUid(int uid);
    int addNewPosition(Position position);
    int updateById(Position position);
    int deleteById(int id,int uid,boolean isDefault);
    Position findById(int id);
}
