package com.his.pharmacy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.pharmacy.entity.DrugStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DrugStockMapper extends BaseMapper<DrugStock> {

    @Select("SELECT s.*, d.drug_name, d.spec as drug_spec FROM drug_stock s " +
            "LEFT JOIN drug_info d ON s.drug_id = d.id " +
            "WHERE s.quantity <= s.safe_stock AND s.status = 1")
    List<DrugStock> selectWarnings();
}
