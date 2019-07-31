package com.lhstack.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.lhstack.pojo.TbOrderItem;
import com.lhstack.pojo.TbOrderItemExample;

public interface TbOrderItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_item
     *
     * @mbggenerated Fri Aug 18 10:13:27 CST 2017
     */
    int countByExample(TbOrderItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_item
     *
     * @mbggenerated Fri Aug 18 10:13:27 CST 2017
     */
    int deleteByExample(TbOrderItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_item
     *
     * @mbggenerated Fri Aug 18 10:13:27 CST 2017
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_item
     *
     * @mbggenerated Fri Aug 18 10:13:27 CST 2017
     */
    int insert(TbOrderItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_item
     *
     * @mbggenerated Fri Aug 18 10:13:27 CST 2017
     */
    int insertSelective(TbOrderItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_item
     *
     * @mbggenerated Fri Aug 18 10:13:27 CST 2017
     */
    List<TbOrderItem> selectByExample(TbOrderItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_item
     *
     * @mbggenerated Fri Aug 18 10:13:27 CST 2017
     */
    TbOrderItem selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_item
     *
     * @mbggenerated Fri Aug 18 10:13:27 CST 2017
     */
    int updateByExampleSelective(@Param("record") TbOrderItem record, @Param("example") TbOrderItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_item
     *
     * @mbggenerated Fri Aug 18 10:13:27 CST 2017
     */
    int updateByExample(@Param("record") TbOrderItem record, @Param("example") TbOrderItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_item
     *
     * @mbggenerated Fri Aug 18 10:13:27 CST 2017
     */
    int updateByPrimaryKeySelective(TbOrderItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_item
     *
     * @mbggenerated Fri Aug 18 10:13:27 CST 2017
     */
    int updateByPrimaryKey(TbOrderItem record);
}