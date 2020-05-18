package com.mee.manage.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mee.manage.enums.FeeTypeEnum;
import com.mee.manage.enums.FreeTypeEnum;
import com.mee.manage.enums.FreightTypeEnum;
import com.mee.manage.exception.MeeException;
import com.mee.manage.mapper.IOrderAddressMapper;
import com.mee.manage.po.Freight;
import com.mee.manage.po.FreightDetail;
import com.mee.manage.po.OrderAddress;
import com.mee.manage.po.OrderDetail;
import com.mee.manage.po.ProductFreight;
import com.mee.manage.service.IFreightDefaultService;
import com.mee.manage.service.IFreightService;
import com.mee.manage.service.IOrderAddressService;
import com.mee.manage.service.IOrderDetailService;
import com.mee.manage.service.IProductFreightService;
import com.mee.manage.util.StatusCode;
import com.mee.manage.vo.FeeDetailVo;
import com.mee.manage.vo.FreightOrderVo;
import com.mee.manage.vo.FreightVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderAddressServiceImpl extends ServiceImpl<IOrderAddressMapper, OrderAddress>
        implements IOrderAddressService {

    @Autowired
    IOrderDetailService orderDetailService;

    @Autowired
    IProductFreightService productService;

    @Autowired
    IFreightDefaultService freightDefatultService;

    @Autowired
    IFreightService freightService;

    @Override
    public OrderAddress getOrderAddress(Long orderId) {
        QueryWrapper<OrderAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        return getOne(queryWrapper);
    }

    @Override
    public FeeDetailVo getAddressFee(Long orderId,Long bizId) throws MeeException{
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetails(orderId);
        if(orderDetails == null || orderDetails.isEmpty()) {
            throw new MeeException(StatusCode.ORDER_NOT_EXIST);
        }

        //1、获取商品
        List<Long> productIds = orderDetails.stream().map(item -> item.getProductId()).collect(Collectors.toList());

        //2、根据商品获取运费模板
        List<ProductFreight> productFreights = productService.freights(productIds);
        List<Long> freightIds = null;
        Long defaultId = null;
        if (productFreights != null && productFreights.size() > 0) {
            freightIds = productFreights.stream().map(item -> item.getProductId()).collect(Collectors.toList());
        } else if(productFreights == null || productFreights.size() <= 0 || productFreights.size() < productFreights.size()) {
            //查询模本模板
            defaultId = freightDefatultService.getDefaultFreightId(bizId);
            if(freightIds == null)
                freightIds = Lists.newArrayList();
            
            freightIds.add(defaultId);
        }
        
        List<FreightVo> freightVos = freightService.getFreights(freightIds);
        if(freightVos == null || freightVos.size() <= 0)
            throw new MeeException(StatusCode.FREIGHT_NOT_EXIST);

        List<FreightOrderVo> freightOrderVo = Lists.newArrayList();
        for(FreightVo freight : freightVos) {
            Long freightId = freight.getFreight().getId();
            if(productFreights != null) {
                List<Long> pIds = productFreights.stream().
                        filter(item -> item.getFreightId().compareTo(freightId) == 0).
                        map(item -> item.getProductId()).
                        collect(Collectors.toList());
                if(pIds != null && pIds.size() > 0) {
                    List<OrderDetail> oList = orderDetails.stream().
                                                filter(item -> pIds.indexOf(item.getProductId()) >= 0).
                                                collect(Collectors.toList());
                    FreightOrderVo fOrderVo = new FreightOrderVo();
                    fOrderVo.setFreightVo(freight);
                    fOrderVo.setOList(oList);
                    freightOrderVo.add(fOrderVo);
                }
            }
        }

        if(defaultId != null && defaultId > 0) {
            List<OrderDetail> defaultDetail = Lists.newArrayList();
            for(OrderDetail od : orderDetails) {
                boolean flag = false;
                for(FreightOrderVo fOrderVo : freightOrderVo) {
                    List<OrderDetail> odDetails = fOrderVo.getOList();
                    if(odDetails != null) {
                        if(odDetails.stream().filter(item -> item.getProductId().compareTo(od.getProductId()) == 0).count() > 0) {
                            flag = true;
                            break;
                        }
                    }
                }
                if(!flag) {
                    defaultDetail.add(od);
                }
            }

            if(defaultDetail != null && defaultDetail.size() > 0) {
                long dId = defaultId == null ? 0 : defaultId.longValue();
                long num = freightOrderVo.stream().filter(item -> item.getFreightVo().getFreight().getId() == dId).count();
                if( num > 0) {
                    freightOrderVo.stream().filter(item -> item.getFreightVo().getFreight().getId().compareTo(dId) == 0).
                    forEach(item -> item.getOList().addAll(defaultDetail));
                } else {
                    List<FreightVo> dFreightList = freightVos.stream().filter(item -> item.getFreight().getId().compareTo(dId) == 0).collect(Collectors.toList());
                    if(dFreightList != null && dFreightList.size() > 0) {
                        FreightOrderVo fOrderVo = new FreightOrderVo();
                        fOrderVo.setFreightVo(dFreightList.get(0));
                        fOrderVo.setOList(defaultDetail);
                        freightOrderVo.add(fOrderVo);
                    }
                }
                
            }
        }

        //3、查询地址+模板 计算运费
        OrderAddress orderAddress = getOrderAddress(orderId);
        if(orderAddress == null) {
            throw new MeeException(StatusCode.ORDER_NOT_EXIST);
        }

        FeeDetailVo feeDetailVo = new FeeDetailVo();

        BigDecimal expressFee = countExpressFee(freightOrderVo, orderAddress);
        if(expressFee != null ) {
            feeDetailVo.setFee(expressFee);
            feeDetailVo.setFeeType(FeeTypeEnum.EXPRESS.getCode());
        }

        return feeDetailVo;
    }

    private BigDecimal countExpressFee(List<FreightOrderVo> freightOrderVo, OrderAddress orderAddress) throws MeeException{
        BigDecimal fee = BigDecimal.ZERO;
        for(FreightOrderVo freightOrder : freightOrderVo) {
            FreightVo freightVo = freightOrder.getFreightVo();
            List<OrderDetail> orderDetails = freightOrder.getOList();

            Freight freight = freightVo.getFreight();
            List<FreightDetail> freightDetails = freightVo.getFreightDetail();
            
            FreightDetail selectFrightDetail = selectFreightDetail(freightDetails,orderAddress.getCity(),orderAddress.getSuburb());
            if(selectFrightDetail != null) {
                fee = getFee(freight, selectFrightDetail, orderDetails);
            } else {
                throw new MeeException(StatusCode.ADDRESS_ERROR);
            }
            
        }

        return fee;
    }

    private BigDecimal getFee(Freight freight, FreightDetail detail, List<OrderDetail> orderDetails) {
        boolean isFree = isFree(detail.getFreeType(), detail.getFreeShipping(), orderDetails);
        if(isFree) {
            return BigDecimal.ZERO;
        }
        BigDecimal fee = BigDecimal.ZERO;
        if(freight.getType() == FreightTypeEnum.NUMBER.getCode()) {     //按数量计费
            fee = getFeeNumber(detail,orderDetails);

        } else if(freight.getType() == FreightTypeEnum.WEIGHT.getCode()) {  //按重量计费
            fee = getFeeWeight(detail,orderDetails);

        }
        return fee;
    }

    private BigDecimal getFeeNumber(FreightDetail detail, List<OrderDetail> orderDetails) {
        int number = 0;
        for(OrderDetail orderDetail : orderDetails) {
            number += orderDetail.getNumber();
        }
        BigDecimal fee = detail.getFirstPrice();
        if(number > detail.getFirst()) {
            int moreNum = detail.getFirst() - number;
            BigDecimal moreTime = new BigDecimal(Math.ceil(moreNum/detail.getMore()));
            BigDecimal morePrice = detail.getMorePrice().multiply(moreTime);
            fee = fee.add(morePrice);
        }
        return fee;
    }

    private BigDecimal getFeeWeight(FreightDetail detail, List<OrderDetail> orderDetails) {
        int weight = 0;
        for(OrderDetail orderDetail : orderDetails) {
            weight += orderDetail.getWeight();
        }
        BigDecimal fee = detail.getFirstPrice();
        if(weight > detail.getFirst()) {
            long moreNum = detail.getFirst() - weight;
            BigDecimal moreTime = new BigDecimal(Math.ceil(moreNum/detail.getMore()));
            BigDecimal morePrice = detail.getMorePrice().multiply(moreTime);
            fee = fee.add(morePrice);
        }
        return fee;
    }
    /**
     * 是否包邮
     * @return
     */
    private boolean isFree(Integer freeType, Double freeShipping,List<OrderDetail> orderDetails) {
        boolean flag = false;
        if(freeType == FreeTypeEnum.NO.getCode()) {
            flag = false;
        } else if(freeType == FreeTypeEnum.NUMBER.getCode()) {   //按数量包邮
            int number = 0;
            for(OrderDetail orderDetail : orderDetails) {
                number += orderDetail.getNumber();
            }
            if(freeShipping >= number) {
                flag = true;
            }
        } else if(freeType == FreeTypeEnum.PRICE.getCode()) {
            BigDecimal price = BigDecimal.ZERO;
            for(OrderDetail orderDetail : orderDetails) {
                price.add(orderDetail.getPrice());
            }
            BigDecimal freePrice = new BigDecimal(freeShipping);
            if(freePrice.compareTo(price) >= 0) {
                flag = true;
            }
        }

        return flag;
    }

    private FreightDetail selectFreightDetail(List<FreightDetail> freightDetails, String city, String suburb) {
        if(freightDetails == null || freightDetails.isEmpty())
            return null;
        
        List<FreightDetail> selectFrightDetail = freightDetails.stream().
            filter(item -> item.getCitys().indexOf(city) >= 0).
            collect(Collectors.toList());

        FreightDetail detail = null;
        if(selectFrightDetail != null && selectFrightDetail.size() > 0) {
            detail = filterCity(selectFrightDetail,city,suburb);
            if(detail == null) {
                detail = filterCity(selectFrightDetail,city,"ALL");
            }

        } else {
            List<FreightDetail> allCityDetail = freightDetails.stream().
                                filter(item -> item.getCitys().indexOf("All City") >= 0).
                                collect(Collectors.toList());
            if(allCityDetail != null && allCityDetail.size() > 0) {
                detail = allCityDetail.get(0);
            }
        }
        
        return detail;
    }

    private FreightDetail filterCity(List<FreightDetail> selectFrightDetail, String city, String suburb) {
        FreightDetail detail = null;

        for(FreightDetail fDetail : selectFrightDetail) {
            String[] citys = fDetail.getCitys().split(";");
            List<String> cityList = Lists.newArrayList(citys);
            if(cityList.stream().filter(item -> (item.indexOf(city) >= 0 && item.indexOf(suburb) >= 0)).count() > 0)
                detail = fDetail;
                break;
        }
        return detail;
    }



}