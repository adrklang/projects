package com.lhstack.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.lhstack.pojo.TbOrder;
import com.lhstack.pojo.TbOrderExample;

public interface TbOrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbggenerated Fri Aug 18 10:13:27 CST 2017
     */
    int countByExample(TbOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbggenerated Fri Aug 18 10:13:27 CST 2017
     */
    int deleteByExample(TbOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbggenerated Fri Aug 18 10:13:27 CST 2017
     */
    int deleteByPrimaryKey(String orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbggenerated Fri Aug 18 10:13:27 CST 2017
     */
    int insert(TbOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbggenerated Fri Aug 18 10:13:27 CST 2017
     */
    int insertSelective(TbOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbggenerated Fri Aug 18 10:13:27 CST 2017
     */
    List<TbOrder> selectByExample(TbOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbggenerated Fri Aug 18 10:13:27 CST 2017
     */
    TbOrder selectByPrimaryKey(String orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbggenerated Fri Aug 18 10:13:27 CST 2017
     */
    int updateByExampleSelective(@Param("record") TbOrder record, @Param("example") TbOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbggenerated Fri Aug 18 10:13:27 CST 2017
     */
    int updateByExample(@Param("record") TbOrder record, @Param("example") TbOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbggenerated Fri Aug 18 10:13:27 CST 2017
     */
    int updateByPrimaryKeySelective(TbOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbggenerated Fri Aug 18 10:13:27 CST 2017
     */
    int updateByPrimaryKey(TbOrder record);
}